package base;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

//import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;



import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.cucumber.java.Scenario;
import test.TestRun;
import utilities.AppiumUtil;
import utilities.ExcelUtil;

public class Base {
	public static Scenario scenario;
	private static AppiumDriverLocalService service;
	private static String caseID;
	private static boolean original_flag, changed_flag;
	private static String error_msg = "Sorry! Test failed !";
	public static String platform;
	private static long start_time;
	private static String url;
	private static String run_mode = "local";
	private static String fail_reason = "";

	public static void setStartTime() {
		start_time = System.currentTimeMillis();
	}
	public static void setFailReason(String reason){
		fail_reason = reason;
	}
	public static String getFailReason(){
		return fail_reason ;
	}

	//no runtime
	public static String getRunTime() {
		long run_time = System.currentTimeMillis() - start_time;
		long currentMS = run_time % 1000;
		long totalSeconds = run_time / 1000;
		long currentSecond = totalSeconds % 60;
		long totalMinutes = totalSeconds / 60;
		long currentMinute = totalMinutes % 60;

		String use_time = String.valueOf(currentMinute) + "m" + String.valueOf(currentSecond) + "."
				+ String.valueOf(currentMS) + "s";
		return use_time;
	}

	public static String getRunMode(){
		return run_mode;
	}

	public static void setRunMode(String mode){
		run_mode = mode;
	}

	public static void setErrorMsg(String msg) {
		error_msg += " " + msg;
	}








	public static String getCaseID() {
		return caseID;
	}

	public static void setCaseID(String caseID) {
		Base.caseID = caseID;
	}

	public AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunnning(4723);

		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}

	public static boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static void setCapabilities() throws IOException {
		String reportDirectory = "reports/fonio";
		String reportFormat = "xml";
//		String fileSeparator = File.separator;
		String fileSeparator = "/";
		String projectBaseDirectory = System.getProperties().getProperty("user.dir");

		String appPackage = "hko.MyObservatory_v1_0";
		String appActivity = ".AgreementPage";

		ITestContext itc = TestRun.itc;
		// device setting
		String device = itc.getCurrentXmlTest().getParameter("device");
		platform = itc.getCurrentXmlTest().getParameter("platform");
		String useProxy = itc.getCurrentXmlTest().getParameter("useProxy");
		url = itc.getCurrentXmlTest().getParameter("url");
		String accessKey = itc.getCurrentXmlTest().getParameter("accessKey");

		if ("TRUE".equalsIgnoreCase(useProxy)) {
			System.setProperty("https.proxyHost", "10.7.192.136");
			System.setProperty("https.proxyPort", "10938");
		}

		if ("".equals(url)) {
			url = "https://aia.experitest.com/wd/hub";
		}
		if (url.contains("experitest")) {
			run_mode = "cloud";
		}

//Added by DF		if (DriverContext.getDriver() == null) {

		DesiredCapabilities dc = new DesiredCapabilities();
		// dc.setCapability("report.disable", true);
		dc.setCapability("instrumentApp", true);

		//debug 的时候打开
//			dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);

		// report setting
		dc.setCapability("reportDirectory", reportDirectory);
		dc.setCapability("reportFormat", reportFormat);
		dc.setCapability("testName", caseID);

//			if (url.contains("experitest")) {
//				dc.setCapability(MobileCapabilityType.APP,"cloud:com.aiahk.idirect.uat/com.aiaconnect.MainActivity:4.316");
//			}

		dc.setCapability(MobileCapabilityType.UDID, device);
		dc.setCapability("platformName", platform);
		dc.setCapability("accessKey", accessKey);
		//TODO:QuickDebug
		if (platform.equalsIgnoreCase("ANDROID")) {
			System.out.println("Connected to Android device");
			dc.setCapability("appPackage", appPackage);
			dc.setCapability("appActivity", appActivity);

			DriverContext.setDriver((AppiumDriver<MobileElement>) (new AndroidDriver<MobileElement>(new URL(url), dc)));
		} else {
			System.out.println("Connected to IOS device");
//				appPackage = "com.accentuerlab.connect.uat";
			dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, appPackage);

			// dc.setCapability("unicodeKeyboard" ,"True");
			// dc.setCapability("resetKeyboard", "True");
			// dc.setCapability("sendKeyStrategy", sendkeyStrategy);
//				dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
			//dc.setCapability("noReset", true);

			DriverContext.setDriver((AppiumDriver<MobileElement>) (new IOSDriver<MobileElement>(new URL(url), dc)));
		}

		System.out.println("=============create a new driver!=============");
		DriverContext.setDeviceModel();
		DriverContext.setDeviceOS();
		DriverContext.setDeviceName();


//DF		} else {
//			DriverContext.getDriver().closeApp();
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
		// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (DriverContext.getDeviceOS().contains("IOS")) {
//				DriverContext.getDriver().launchApp();
//			}else {
//				AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>)DriverContext.getDriver();
//				driver.startActivity(new Activity(appPackage, appActivity));
//				DriverContext.setDriver((AppiumDriver<MobileElement>)driver);
//			}
//DF		}

		// client.setProjectBaseDirectory(projectBaseDirectory);
//		setFlag(false);// 通过addCapture在报告增加截图
	}
	public void delay(long delaySec) {
		try {
			Thread.sleep(delaySec * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	}