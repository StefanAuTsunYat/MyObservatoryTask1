package base;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.sikuli.script.App;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import com.MyObservatory.constant.ConstantFile;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.Scenario;
import utilities.AppiumUtil;
import utilities.ExcelUtil;

public abstract class BasePage {
	protected Scenario scenario = Base.scenario;
	protected static String language = "English";
	protected static Logger logger = Logger.getLogger(BasePage.class);
	//protected static AppiumDriver<MobileElement> driver = DriverContext.getDriver();
	public String strPlatform = DriverContext.getDeviceOS();
	public String deviceName ;

	private static int screenHeight = DriverContext.driver.manage().window().getSize().height;
	protected static int screenWidth = DriverContext.driver.manage().window().getSize().width;

	private int display_x_min = (int)(screenWidth*0.1);
	private int display_x_max = (int)(screenWidth*0.9);
	private int display_y_min = (int)(screenHeight*0.06);
	private int display_y_max = (int)(screenHeight*0.94);
	private int swipe_x_small = (int)(screenWidth*0.2);
	private int swipe_x_big = (int)(screenWidth*0.8);
	private int swipe_little_x_small = (int)(screenWidth*0.4);
	private int swipe_little_x_big = (int)(screenWidth*0.6);
	private int swipe_y_small = (int)(screenHeight*0.2);
	private int swipe_y_big = (int)(screenHeight*0.8);
	private int swipe_little_y_small = (int)(screenHeight*0.4);
	private int swipe_little_y_big = (int)(screenHeight*0.6);
	private int swipe_time = 1500;
	private int swipe_x = (int)(screenWidth-50);

	protected static int va_y_max;//visible area
	protected static int va_y_min;
	protected static int va_y_max_home_page;

	protected static File img1;
	protected static File img2;
//stefan test
	public BasePage() {
		System.out.println("DriverContext.getDriver() = "+DriverContext.getDriver());
		PageFactory.initElements(new AppiumFieldDecorator(DriverContext.getDriver()), this);
		this.language = ExcelUtil.getValue("language").trim();

	}

	protected void resetVisibleAreaMaxY(){
		resetVisibleAreaMaxY(false);
	}

	protected void resetVisibleAreaMaxY(boolean onHomePage){
		if(!onHomePage){
			va_y_max = screenHeight;
		}
	}


	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	public boolean checkFullScreen(MobileElement element) {
		int position_y_top = element.getLocation().y;
		int size_y = element.getSize().width;
		int position_y_below = position_y_top + size_y;

		System.out.println("position_y_below : " + position_y_below + ", screenHeight : " + screenHeight);
		boolean flag = position_y_below > (int)(screenHeight*0.95);
		return flag;
	}

	private static void execShell(String scriptPath,String ... para) throws Exception {
		String[] cmd = new String[] {scriptPath};
		cmd = ArrayUtils.addAll(cmd, para);
		System.out.println(cmd);

		//handle error: Permission denied
		ProcessBuilder builder = new ProcessBuilder("/bin/chmod","755",scriptPath);
		Process process = builder.start();
		process.waitFor();

		Process ps = Runtime.getRuntime().exec(cmd);
		ps.waitFor();
	}

	public static void doPaste() throws Exception {
		Robot r = new Robot();
		r.setAutoDelay(500);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
	}

//    public void setValue(String value) throws Exception {
//    		String deviceName = client.getDeviceProperty("device.name");
//    		System.out.println(deviceName);

//    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//    		Transferable tf = new StringSelection(value);
//    		clipboard.setContents(tf, null);

//    		String osType = System.getProperty("os.name").toLowerCase();
//    		if(osType.contains("windows")){
//    			deviceName = DriverContext.getDeviceName();
//    			String prefix = "";
//    			if (Base.platform.equalsIgnoreCase("ANDROID")) {
//    				prefix="adb:";
//    			}else{
//    				prefix="ios_app:";
//    			}
//    			setAppWindowFocused(prefix+ deviceName);

//    		}else{
//    			execShell("resource/getWindowTitle.sh",deviceName);
//    		}

//		doPaste();
//	}

	private void setAppWindowFocused(String windowTitle) {

//		App app = new App(windowTitle);
//		app.focus();


	}

//    public void sendKeysOneByOne(MobileElement element , String text) {
//    		//解决iPhone11中sendkeys中重复输入
//    		String letter_xpath ="//*[@text='letters']";
//    		String number_xpath ="//*[@text='numbers']";
//    		String current_char = "letter";
//    		String previous_char = "letter";
//    		boolean first_input = true;
//    		char[] c = text.toCharArray();
//		for(char tmp : c) {
//			if (Character.isLetter(tmp)) {
//				current_char = "letter";
//				if (!current_char.equals(previous_char)) {
//					driver.findElementByXPath(letter_xpath).click();
//				}
//				System.out.println(Character.isUpperCase(tmp));
//				if (Character.isUpperCase(tmp) && (!first_input)) {
//					driver.findElementByXPath("//*[@text='shift']").click();
//				}
//			}else {
//				current_char = "number";
//				if (!current_char.equals(previous_char)) {
//					driver.findElementByXPath(number_xpath).click();
//				}
//			}
//			driver.findElementByAccessibilityId(String.valueOf(tmp)).click();
//			previous_char = current_char;
//			first_input = false;
//		}
//	}

	public void sendKeysInIOSAndAOS(MobileElement element , String text) {
		if (strPlatform.contains("IOS")) {
//    			sendKeysOneByOne(element , text);
			element.click();
			delay(1);
//			element.sendKeys("\b");
//			element.sendKeys("\b");
//			element.sendKeys("\b");
//			element.sendKeys("\b");
//			element.sendKeys("\b");
//			delay(1);
//			element.clear();
//			element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			if (!element.getAttribute("value").equals("")){
//				String aaa = element.getText();
				element.sendKeys("\b");
			}
			delay(1);

			delay(1);
			try {
//					new WebDriverWait(DriverContext.driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath_Locator_IOS_SCHI.b_Done_Keybord)));
//					DriverContext.driver.findElementByXPath(Xpath_Locator_IOS_SCHI.b_Done_Keybord).click();
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}else {
				element.clear();
				if (!text.isEmpty()){
					element.sendKeys(text);
				}
    		}
    		closeKeyBoard();
	}

	public void sendKeysInIOSAndAOSLeftsife(MobileElement element , String text) {
		if (strPlatform.contains("IOS")) {
			clickElementLeftSide(element);
			delay(1);
			element.clear();
			delay(1);
//			client.sendText(text);
			delay(1);
			try {
//				new WebDriverWait(DriverContext.driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath_Locator_IOS_SCHI.b_Done_Keybord)));
//				DriverContext.driver.findElementByXPath(Xpath_Locator_IOS_SCHI.b_Done_Keybord).click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			element.sendKeys(text);
		}
		closeKeyBoard();
	}

	public void isNoticeExist() {
		WebDriverWait wait = new WebDriverWait(DriverContext.driver,10);
		//System.out.println(System.currentTimeMillis());
		try {
			wait.until(ExpectedConditions.visibilityOf(DriverContext.driver.findElement(By.xpath("//*[contains(@text,'IMPORTANT NOTICE') or contains(@text,'通知')]"))));
			System.out.println("found notice!");
			//System.out.println(System.currentTimeMillis());
			//delay(3);
			AppiumUtil.tapElementEdge(DriverContext.driver.findElement(By.xpath("//*[contains(@text,'IMPORTANT NOTICE') or contains(@text,'通知')]")),"MIDDLE-LEFT",30,0,1);
			//DriverContext.driver.findElement(By.xpath("//*[@class='UIAButton']")).click();
			System.out.println("close notice!");
			//System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			System.out.println("no notice!");
			//System.out.println(System.currentTimeMillis());
		}
	}

//	public void addCapture(String msg){
//		Base.addCapture(msg);
//	}
//
//	public void addCapture(String msg,int time){
//		Base.addCapture(msg,time);
//	}

	public static String getPageLang() {
		return language;
	}

	public void delay(long delaySec) {
		try {
			Thread.sleep(delaySec * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void waitElementApper()
	{
		try {

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}



	protected MobileElement waitElementPresent(By by){
		return  waitElementPresent(by,ConstantFile.TIMEOUT60);
	}

	protected MobileElement waitElementPresent(By by,int timeout){
    	return  (MobileElement) new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
	}

	protected MobileElement waitElementClickable(By by){
		return waitElementClickable(by,ConstantFile.TIMEOUT60);
	}

	protected MobileElement waitElementClickable(By by,int timeout){
		return AppiumUtil.waitElementClickable(by, timeout);
	}

	protected MobileElement waitElementClickable(MobileElement element){
		return waitElementClickable(element,ConstantFile.TIMEOUT60);
	}

	protected MobileElement waitElementClickable(MobileElement element,int timeout){
		return AppiumUtil.waitElementClickable(element, timeout);
	}
	public void swipeElementVisible(MobileElement element) {
		swipeElementVisible(element, element.getCenter().x);
    }
	public void swipeElementVisible(MobileElement element, int start_x) {
		int i = 1;
		while(!element.isDisplayed()) {
			System.out.println("Current platform: "+ DriverContext.getDeviceOS());
			if (!DriverContext.getDeviceOS().contains("IOS")) {
//    			swipeCoordination(200, 1400, 200, 650, swipe_time);
//				addCapture("swipe times : " + i);
				swipeCoordination(start_x, swipe_y_big, start_x, swipe_y_small, swipe_time);
			} else {
//				addCapture("swipe times : " + i);
				ScrollownLittleScreen();
			}
			delay(1);
			i++;
		}
	}

	public boolean isElementPresent(By locator) {

		return isElementPresent(locator,ConstantFile.TIMEOUT60);
	}

	public boolean isElementPresent(By locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), timeout);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void swipeLeftOnElement(MobileElement element)
	{
    	JavascriptExecutor js = (JavascriptExecutor) DriverContext.driver;
		Map<String, Object> params = new HashMap<>();
		params.put("direction", "left");
		params.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: swipe", params);

	}
	public void swipeHalfScreen()
	{
		JavascriptExecutor js = (JavascriptExecutor) DriverContext.driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		org.openqa.selenium.Dimension dimension = DriverContext.driver.manage().window().getSize();
		Double startHeight = dimension.getHeight()*0.5;
		Double startWidth = dimension.getWidth()*0.5;
		int startY = startHeight.intValue();
		int startX = startWidth.intValue();
		if(!strPlatform.contains("IOS")) {
//			new AndroidTouchAction(driver).press(PointOption.point(startX,startY)).moveTo(PointOption.point(startX,485)).release().perform();
			new AndroidTouchAction(DriverContext.driver).press(PointOption.point(startX,swipe_y_big)).moveTo(PointOption.point(startX,swipe_y_small)).release().perform();
		}else {
//			new IOSTouchAction(driver).press(PointOption.point(startX,startY)).moveTo(PointOption.point(startX,485)).release().perform();
			new IOSTouchAction(DriverContext.driver).press(PointOption.point(startX,swipe_y_big)).moveTo(PointOption.point(startX,swipe_y_small)).release().perform();
		}
	}
	public void scrollToDown() {
		if(strPlatform.contains("IOS")) {
			scrollToDownIOS();
		}
		else {
//add scroll to down AOS
		}
	}
	public void scrollToDownIOS()
	{
		JavascriptExecutor js = (JavascriptExecutor) DriverContext.driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);

    }
    public void pressOnScreen(int x, int y) {
    	System.out.println(x + " , " + y);
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(DriverContext.driver).press(PointOption.point(x, y)).perform();
		} else {
			new AndroidTouchAction(DriverContext.driver).press(PointOption.point(x, y)).perform();
		}
	}

	public void tapOnScreen(int x, int y) {
		System.out.println(x + " , " + y);
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(DriverContext.driver).tap(PointOption.point(x, y)).perform();
		} else {
			new AndroidTouchAction(DriverContext.driver).tap(PointOption.point(x, y)).perform();
		}
	}

    public void pressOnScreenUntilEleShow(int x, int y, MobileElement ele){
        int tap_count = 0;
        while ((!AppiumUtil.isElementPresent(ele,2)) && tap_count++<10){
            if ( strPlatform.contains("IOS")) {
                new IOSTouchAction(DriverContext.driver).press(PointOption.point(x, y)).perform();
            } else {
                new AndroidTouchAction(DriverContext.driver).press(PointOption.point(x, y)).perform();
            }
            delay(1);
        }
    }
	public void tapOnElement(MobileElement element) {
		System.out.println(element.getCenter());
		System.out.println(element.getLocation());
		System.out.println(element.getSize());

		int x = Integer.valueOf(element.getAttribute("x")) + 10;
		int y = Integer.valueOf(element.getAttribute("y")) + 10;
		System.out.println("Click on X: " + x + " ,Y: " + y);
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(DriverContext.driver).press(PointOption.point(x, y)).release().perform();
		} else {
			new AndroidTouchAction(DriverContext.driver).press(PointOption.point(x, y)).release().perform();
		}
	}


	public static void scrollToListItem(String targetItemXpath, String value) {
		System.out.println("Client - set picker value :" + value);
//		client.setPickerValues("NATIVE", "xpath=" + targetItemXpath, 0, 0, value);
		if (AppiumUtil.isElementPresent(By.xpath("//*[@text='Done']"),1)){
				DriverContext.driver.findElement(By.xpath("//*[@text='Done']")).click();
		}else if (AppiumUtil.isElementPresent(By.xpath("//*[@text='確定']"),1)) {
				DriverContext.driver.findElement(By.xpath("//*[@text='確定']")).click();
		}
		else if (AppiumUtil.isElementPresent(By.xpath("//*[@text='确定']"),1)) {
				DriverContext.driver.findElement(By.xpath("//*[@text='确定']")).click();
		}else if (AppiumUtil.isElementPresent(By.xpath("//*[@text='Confirm']"),1)) {
				DriverContext.driver.findElement(By.xpath("//*[@text='Confirm']")).click();
		}
	}

	public void scrollToListItem(String targetItemXpath, int WheelIndex, String value) {
		//多个wheel
		System.out.println("Client - set picker value");
//		client.setPickerValues("NATIVE", "xpath=" + targetItemXpath, 0, WheelIndex, value);
    }




	public void backToAppIOS13() {
    	int height = DriverContext.driver.manage().window().getSize().height;
    	int width = DriverContext.driver.manage().window().getSize().width;
		pressOnScreen((int)(width*0.036), (int)(height*0.035));
	}

	public void ScrollownLittleScreen() {
		int x= (int) (DriverContext.getDriver().manage().window().getSize().getWidth()*0.5);
		int y=(int) (DriverContext.getDriver().manage().window().getSize().getHeight()*0.5);
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(DriverContext.driver).press(PointOption.point(swipe_little_x_small, swipe_little_y_big)).moveTo(PointOption.point(swipe_little_x_small, swipe_little_y_small)).perform();
		} else {
//			new AndroidTouchAction(driver).press(PointOption.point(x, y)).moveTo(PointOption.point(x, y-100)).perform();
			new AndroidTouchAction(DriverContext.driver).press(PointOption.point(swipe_little_x_small, swipe_little_y_big)).moveTo(PointOption.point(swipe_little_x_small, swipe_little_y_small)).perform();

		}
	}

	public void closeKeyBoard() {
		if (strPlatform.contains("IOS")) {
			DriverContext.driver.hideKeyboard();
		}else {
			DriverContext.driver.hideKeyboard();
//			int pointX = DriverContext.driver.findElement(By.xpath("//*[@id='back']")).getCenter().getX();
//			int pointY = DriverContext.driver.findElement(By.xpath("//*[@id='back']")).getCenter().getY();
//			client.clickCoordinate(pointX, pointY,1);
		}
	}

	protected void hideKeyboardByDone(){
		By by = By.xpath("//*[@text='Done']");

		if(isElementPresent(by,3)){
    		DriverContext.driver.findElement(by).click();
		}
	}

	public void WaitLoading(MobileElement element) {
		new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT60).until(ExpectedConditions.visibilityOf(element));
		//delay(ConstantFile.TIMEOUT3);
	}

	public MobileElement swipeRightUntilVisibleInChatBox(MobileElement element) {
		if (isElementPresent(element, 10)) {
			logger.info("page is loaded");
		}

		try {
			int xPosition = element.getCenter().getX();
			int yPosition = element.getCenter().getY();
			System.out.println("Start xPosition: " + xPosition);
			System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());

			int i = 1;
			while ((!AppiumUtil.isElementPresent(element,1)) && i < 20) {
//				addCapture("swipe right times : " + i);
				swipeRight(yPosition);
				System.out.println("Swiping Right...");
				xPosition = element.getCenter().getX();
				System.out.println("xPosition: " + xPosition);
				System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());
				i++;
			}
			System.out.println("Final xPosition: " + xPosition);

		}catch (Exception e) {
			for(int i = 1; i < 10; i++){
				swipeRight(270);
				try {
					if(element.getCenter().getX() <= DriverContext.driver.manage().window().getSize().getWidth() - 50 ) {
						break;
					}
				}catch (Exception e2) {
					e.printStackTrace();
				}
			}
		}

		delay(2);
		return element;
	}


	public MobileElement swipeRightUntilVisible(MobileElement element) {
		if (isElementPresent(element, 5)) {
			logger.info("page is loaded");
		}

		try {
			int xPosition = element.getCenter().getX();
			int yPosition = element.getCenter().getY();
			System.out.println("Start xPosition: " + xPosition);
			System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());

			int i = 1;
			while (xPosition > DriverContext.driver.manage().window().getSize().getWidth() - 50 && i < 20) {
//				addCapture("swipe right times : " + i);
				swipeRight(yPosition);
				System.out.println("Swiping Right...");
				xPosition = element.getCenter().getX();
				System.out.println("xPosition: " + xPosition);
				System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());
				i++;
			}
			System.out.println("Final xPosition: " + xPosition);

		}catch (Exception e) {
			for(int i = 1; i < 10; i++){
				swipeRight(270);
				try {
					if(element.getCenter().getX() <= DriverContext.driver.manage().window().getSize().getWidth() - 50 ) {
						break;
					}
				}catch (Exception e2) {
					e.printStackTrace();
				}
			}
		}

		delay(2);
		return element;
	}

	public int[] swipeRightVisibleInChatBox(int x_left, int x_right, MobileElement element) {
		if (isElementPresent(element, 10)) {
			logger.info("page is loaded");
		}
		turnOnWebViewAndroid();
		if (!DriverContext.getDeviceOS().contains("IOS")){
			findEleInDocument(element);
		}
		int[] result = new int[2];
		try {
			int xPosition = element.getCenter().getX();
			int yPosition = element.getCenter().getY();
			System.out.println("Start xPosition: " + xPosition);
			System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());
			int i = 1;
			int swipeSize = swipe_x_big - swipe_x_small;
			while ( (xPosition > x_right-50 || xPosition < x_left-50) && i < 20) {
//				addCapture("swipe right times : " + i);
				swipeRight(yPosition);
				System.out.println("Swiping Right...");
				x_left += swipeSize;
				x_right += swipeSize;
				System.out.println("xPosition: " + xPosition);
				System.out.println("x_left: " + x_left + " , x_right: " + x_right);
				i++;
				result = new int[]{x_left, x_right};
			}
			System.out.println("Final xPosition: " + xPosition);
		}catch (Exception e) {
			for(int i = 1; i < 10; i++){
				swipeRight(270);
				try {
					if(element.getCenter().getX() <= DriverContext.driver.manage().window().getSize().getWidth() - 50 ) {
						break;
					}
				}catch (Exception e2) {
					e.printStackTrace();
				}
			}
		}
		delay(2);
		turnBackNative();
		return result;
	}
	public int[] swipeLeftVisibleInChatBox(int x_left, int x_right, MobileElement element) {
		if (isElementPresent(element, 10)) {
			logger.info("page is loaded");
		}
		try {
			int xPosition = element.getCenter().getX();
			int yPosition = element.getCenter().getY();
			System.out.println("Start xPosition: " + xPosition);
			System.out.println("window width size: " + DriverContext.driver.manage().window().getSize().getWidth());
			int i = 1;
			int swipeSize = swipe_x_big - swipe_x_small;
			while ( (xPosition > x_right-50 || x_right < x_left-50) && i < 20) {
//				addCapture("swipe right times : " + i);
				swipeLeft(yPosition);
				System.out.println("Swiping Right...");
				x_left -= swipeSize;
				x_right -= swipeSize;
				System.out.println("xPosition: " + xPosition);
				System.out.println("x_left: " + x_left + " , x_right: " + x_right);
				i++;
			}
			System.out.println("Final xPosition: " + xPosition);
		}catch (Exception e) {
			for(int i = 1; i < 10; i++){
				swipeRight(270);
				try {
					if(element.getCenter().getX() <= DriverContext.driver.manage().window().getSize().getWidth() - 50 ) {
						break;
					}
				}catch (Exception e2) {
					e.printStackTrace();
				}
			}
		}
		delay(2);
		int[] result = {x_left, x_right};
		return result;
	}
	public MobileElement swipeRightUntilVisibleIPAD(MobileElement element) {
		System.out.println("Func : swipeRightUntilVisibleIPAD");
		System.out.println(DriverContext.driver.getContextHandles());
//		if (isElementPresent(element, 60)) {
//			logger.info("page is loaded");
//		}
		int xPosition = element.getCenter().getX();
		int yPosition = element.getCenter().getY();
		System.out.println("Start xPosition: "+ xPosition);
		System.out.println("window width size: "+ DriverContext.driver.manage().window().getSize().getWidth());

		int i = 1;
		while (xPosition > DriverContext.driver.manage().window().getSize().getWidth()) {
//			addCapture("swipe right times : " + i);
//			swipeRight();
			swipeRightOnVitalityTopSubmenu(yPosition);
			System.out.println("Swiping Right...");
			xPosition = element.getCenter().getX();
			System.out.println("updated xPosition: "+ xPosition);
			System.out.println("window width size: "+ DriverContext.driver.manage().window().getSize().getWidth());
			i ++ ;
		}
		System.out.println("Final xPosition: "+ xPosition);
		return element;
	}

//	public static void dragCoordinates(AppiumDriver<MobileElement> driver, int x1, int y1, int x2, int y2, int time) {
//		System.out.println(
//				"drag from point (" + x1 + ", " + y1 + ") to point (" + x2 + ", " + y2 + ") in " + time + " ms..");
//		driver.executeScript(
//				"seetest:client.dragCoordinates(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + time + ")");
//
//	}

	public static void dragCoordinates(AppiumDriver<MobileElement> driver, int x1, int y1, int x2, int y2, int time)  {
		dragCoordinatesMethod(driver, x1, y1, x2, y2, time);
	}

	public static void dragCoordinates(AppiumDriver<MobileElement> driver, double x1, double y1, double x2, double y2, int time) {
		int x1Int = (int) x1;
		int x2Int = (int) x2;
		int y1Int = (int) y1;
		int y2Int = (int) y2;
		dragCoordinatesMethod(driver, x1Int, y1Int, x2Int, y2Int, time);
	}

	private static void dragCoordinatesMethod(AppiumDriver<MobileElement> driver, Object x1, Object y1, Object x2, Object y2, int time) {
		System.out.println(
				"drag from point (" + x1 + ", " + y1 + ") to point (" + x2 + ", " + y2 + ") in " + time + " ms..");
		driver.executeScript(
				"seetest:client.dragCoordinates(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + time + ")");

	}



	public boolean isElementPresent(MobileElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElePresent(MobileElement element) {
		return isElePresent(element,ConstantFile.TIMEOUT10);
	}


	public boolean isElePresent(MobileElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(DriverContext.driver, timeout);
		try {
			wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));
			return true;
		} catch (Exception e) {
			return false;
		}

		//return element == null ? false : true;
	}

	protected void waitElementPresent(MobileElement element){
		waitElementPresent(element,ConstantFile.TIMEOUT20);
	}

	protected void waitElementPresent(MobileElement element, int timeout){
		 new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));

	}

	public boolean checkElementPresent(MobileElement element, int timeout) {
		try {
			new WebDriverWait(DriverContext.driver, timeout).until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void findElementAndClick(MobileElement element, String elementIOSXpath, String elementAndroidXpath) throws Exception {

		try {
			if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS()) && (!elementIOSXpath.isEmpty())) {
				AppiumUtil.waitUntil(By.xpath(elementIOSXpath), ConstantFile.TIMEOUT60);
			} else if (!elementAndroidXpath.isEmpty()){
				AppiumUtil.waitUntil(By.xpath(elementAndroidXpath), ConstantFile.TIMEOUT60);
			}
		} catch (Exception e) {
			System.out.println("Failed in func: AppiumUtil.waitUntil...");
		}

		int xPosition = element.getCenter().getX();
		int yPosition = element.getCenter().getY();

//		AppiumUtil.swipeElementVisible(element, ConstantFile.TIMEOUT120);
//		swipeElementVisibleWithWaitAndCustomizedCoordinate(element, 100, 1400, 100, 600, swipe_time);
		swipeElementVisibleWithWaitAndCustomizedCoordinate(element, xPosition, swipe_y_big, xPosition, swipe_y_small, swipe_time);
		System.out.println("Target element displayed....");
		element.click();
		System.out.println("Target element clicked....");
	}

	public void findElementAndSendkeys(String keys, MobileElement element, String elementIOSXpath, String elementAndroidXpath) throws Exception {
		try {
			if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS()) && (!elementIOSXpath.isEmpty())) {
				AppiumUtil.waitUntil(By.xpath(elementIOSXpath), ConstantFile.TIMEOUT60);
			} else if (!elementAndroidXpath.isEmpty()){
				AppiumUtil.waitUntil(By.xpath(elementAndroidXpath), ConstantFile.TIMEOUT60);
			}
		} catch (Exception e) {
			System.out.println("Failed in func: AppiumUtil.waitUntil...");
		}
		System.out.println("Target element displayed....");
//		AppiumUtil.swipeElementVisible(element, ConstantFile.TIMEOUT120);
		swipeElementVisibleWithWait(element);
//		element.click();
//		element.sendKeys(keys);
		sendKeysInIOSAndAOS(element, keys);
		System.out.println("Target element clicked....");
	}

	public void clickElementRightSide(MobileElement targetElement) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);

		int sizeX = targetElement.getSize().getWidth();
		int sizeY = targetElement.getSize().getHeight();
		System.out.println("sizeX = "+targetX+", sizeY= "+ targetY);

		int finalX = targetX + (int)(sizeX*0.95);
		int finalY = targetY + (int)(sizeY*0.5);
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);
		int clickCount = 1;
		System.out.println("about to click Target element....");
		DriverContext.driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}

	public void clickElementLeftSide(MobileElement targetElement) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);
		int sizeX = targetElement.getSize().getWidth();
		int sizeY = targetElement.getSize().getHeight();
		System.out.println("sizeX = "+targetX+", sizeY= "+ targetY);
		int finalX = targetX + (int)(sizeX*0.1);
		int finalY = targetY + (int)(sizeY*0.8);
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);
		int clickCount = 1;
		System.out.println("about to click Target element....");
		DriverContext.driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}

	public void clickElementBottom(MobileElement targetElement) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);
		int sizeX = targetElement.getSize().getWidth();
		int sizeY = targetElement.getSize().getHeight();
		System.out.println("sizeX = "+targetX+", sizeY= "+ targetY);
		int finalX = targetX + (int)(sizeX*0.5);
		int finalY = targetY + (int)(sizeY*0.9);
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);
		int clickCount = 1;
		System.out.println("about to click Target element....");
		DriverContext.driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}
	protected void clickCoordinate(int x, int y){
		System.out.println("click point：("+x+"," +y+")");
//		client.clickCoordinate(x, y, 1);
	}

	public void clickElementByPercentageCoordination(MobileElement targetElement, double adjustX_percent, double adjustY_percent){
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);

		int finalX = targetX + (int)((targetElement.getSize().width) * adjustX_percent);
		int finalY = targetY + (int)((targetElement.getSize().height) * adjustY_percent);
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);

		System.out.println("about to click Target element....");
		DriverContext.driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", 1)");
		System.out.println("Target coordinate clicked....");
	}

	public void clickElementByCoordination(MobileElement targetElement, int adjustX, int adjustY, int clickCount) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);

		int finalX = targetX + adjustX;
		int finalY = targetY + adjustY;
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);

		System.out.println("about to click Target element....");
		DriverContext.driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}

	public void swipe2ElementPresent(By by){
		for(int i=0;i<10;i++){
			boolean present = isElementPresent(by,2);
			if(present){
				break;
			}else{
				swipeDown();
			}
		}
	}

	public void swipe2ElementPresent(MobileElement element){
		int y = 0;
		int minY = (int) (screenHeight*0.9);

		for(int i=0;i<13;i++){
			boolean present = isElePresent(element,1);
			if(present){
				y = element.getLocation().getY();
				if (y <= minY) {
					break;
				} else {
					swipeDownLittle();
					minY = screenHeight;
				}
			}else{
				swipeDown();
			}
		}
	}
	
	public void swipe2ElementPresentFast(MobileElement element){
		int y = 0;
		int minY = (int) (screenHeight*0.9);
		for(int i=0;i<10;i++){
			boolean present = isElePresent(element,2);
			if(present){
				y = element.getLocation().getY();
				if (y <= minY) {
					break;
				} else {
					swipeDownLittle();
					minY = screenHeight;
				}
			}else{
				swipeDownFast();
			}
		}
	}
	
	public void swipeUp2ElementPresent(MobileElement element){
		for(int i=0;i<10;i++){
			boolean present = isElePresent(element,2);
			if(present){
				break;
			}else{
				swipeUp();
			}
		}
	}

	public MobileElement swipe2ElementVisible(By by){
		MobileElement element = DriverContext.driver.findElement(by);
		return swipe2ElementVisible(element);
	}

	public MobileElement swipe2ElementVisible(By by, int duration){
		MobileElement element = DriverContext.driver.findElement(by);
		if(element == null){
			System.out.println("Strange, element not found");
			swipe2ElementPresent(by);

			element = DriverContext.driver.findElement(by);
		}
		return swipe2ElementVisible(element, duration);
	}

	public MobileElement swipe2ElementVisible(MobileElement element){
		return swipe2ElementVisible(element,1500);
	}

	public MobileElement swipe2ElementVisible(MobileElement element,int duration){
		return swipe2ElementVisible(element,20,duration);
	}

	public MobileElement swipe2ElementVisible(MobileElement element, int maxSwipeTime,int duration){
		System.out.println("va_y_max:"+va_y_max+", screenHeight:"+ screenHeight);
		if (va_y_max == 0){
			va_y_max = screenHeight;
		}
		if(element == null){
			System.out.println("error, element is Null");
		}
		Point center = element.getCenter();
		int ct_y = center.y;

		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;

		//first swipe down to element vertial visible
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);

		if (ele_y_max < va_y_max) {
			return element;
		} else {
			for (int i = 1; i < maxSwipeTime; i++) {
				try {
					Thread.sleep(2000);
					if (ele_y_max <= va_y_max) {
						System.out.println("element visible in V ");
						System.out.println("Element Y coordination: " + ele_y);
						System.out.println("Visible Area bottom: " + va_y_max);

						break;
					}
					logger.info("element.getLocation().getY(): " + ele_y);
				} catch (Exception e) {
				}
				// swipeDown();
				// addCapture("swipe to find element : " + i);
				swipeCoordination(from_x, from_y, to_x, to_y, duration);
				System.out.println("Swiping down...");
				System.out.println("Swiped times:" + i);

				ele_y = element.getLocation().y;
				ele_y_max = ele_y + ele_height;
			}

			if (ele_y_max < (int) (va_y_max * 0.8)) {
				swipeDownLittle();
			}
//		ct_y = element.getCenter().y;

			delay(1);
			return element;
		}
	}

	public MobileElement swipeRight2ElementVisible(MobileElement element){
		return swipeRight2ElementVisible(element,1500);
	}

	public MobileElement swipeRight2ElementVisible(MobileElement element,int duration){
		return swipeRight2ElementVisible(element,20,duration);
	}

	public MobileElement swipeRight2ElementVisible(MobileElement element,
												   int maxSwipeTime, int duration) {
		// swipe right to element visible
		int ele_x = element.getLocation().x;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;

		int mid_screen_width = screenWidth / 2;
		Point center = element.getCenter();
		int ct_y = center.y;

		int from_x = mid_screen_width;
		int from_y = ct_y;
		int to_x = (int) (mid_screen_width * 0.25);
		int to_y = ct_y;

		for (int i = 1; i < maxSwipeTime; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth || (ele_x+ele_width/2<screenWidth)) {
					System.out.println("element visible in H ");
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);

			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, duration);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);

			ele_x = element.getLocation().x;
			ele_x_max = ele_x + ele_width;
		}

		delay(1);
		return element;
	}

	public MobileElement swipeScreenUp2ElementVisible(MobileElement element){
		Point center = element.getCenter();
		int ct_y = center.y;

		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;

		//first swipe down to element vertial visible
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.25);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);

		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_y > va_y_min) {
					System.out.println("element visible in V ");
					System.out.println("Element Y coordination: " + ele_y);
					System.out.println("Visible Area top: " + va_y_min);

					break;
				}
				logger.info("element.getLocation().getY(): " + ele_y);
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping up...");
			System.out.println("Swiped times:" + i);

			ele_y = element.getLocation().y;
			//ele_y_max = ele_y + ele_height;
		}

		ct_y = element.getCenter().y;

		//swipe right to element visible
		int ele_x = element.getLocation().x;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;

		from_x = mid_screen_width;
		from_y = ct_y;
		to_x = (int)(mid_screen_width * 0.25);
		to_y = ct_y;

		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth) {
					System.out.println("element visible in H ");
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);

			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);

			ele_x = element.getLocation().x;
			ele_x_max = ele_x + ele_width;
		}


		return element;
	}

	public MobileElement swipeDown2ElementDisplayed(MobileElement element){
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);

		for(int i = 0;i<10;i++){
			boolean visible = element.isDisplayed();
			if(visible){
				dragDown2VisibleArea(element);
				break;
			}else{
				swipeCoordination(from_x, from_y, to_x, to_y, 1500);

			}
		}

		return element;
	}

	private void dragDown2VisibleArea(MobileElement element){
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;

		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);

		if (ele_y_max <= va_y_max) {
			System.out.println("Element Y coordination: " + ele_y);
			System.out.println("Visible Area bottom: " + va_y_max);
		}else{
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			logger.info("element.getLocation().getY(): " + ele_y);
		}
		// swipeDown();
		// addCapture("swipe to find element : " + i);

	}
	public MobileElement swipeDown2ElementVisible(MobileElement element, boolean instrument) {
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;

		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);

		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_y_max <= va_y_max) {
					System.out.println("Element Y coordination: " + ele_y);
					System.out.println("Visible Area bottom: " + va_y_max);
					break;
				}
				logger.info("element.getLocation().getY(): " + ele_y);
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
		}

		System.out.println("element found...");

		return element;
	}

	public MobileElement swipeElementVisibleWithWait(MobileElement element) {
		return swipeElementVisibleWithWaitAndCustomizedCoordinate(element, 10, swipe_y_big, 10, swipe_y_small, swipe_time);
	}


	public MobileElement swipeElementVisibleWithWaitAndCustomizedCoordinate(MobileElement element, int fromX, int fromY, int toX, int toY, int duration) {
		logger.info("Start of function swipeElementVisibleWithWait");
		try {
//			AppiumUtil.waitElementDisappear();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i = 1; i < 15; i++) {
//		for(int i = 1; i < 8; i++) {
			try {
				Thread.sleep(2000);
				System.out.println("Element Y coordination: "+ element.getLocation().getY());
				System.out.println("Screen bottom: "+(DriverContext.driver.manage().window().getSize().getHeight()));
				if(element.getLocation().getY() <= DriverContext.driver.manage().window().getSize().getHeight() - 257) {

					break;
				}
				logger.info("element.getLocation().getY(): "+element.getLocation().getY());
			} catch (Exception e) {
				e.printStackTrace();
			}
//			swipeDown();
//			addCapture("swipe to find element : " + i);
			swipeCoordination(fromX, fromY, toX, toY, duration);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:"+ i);
		}

		System.out.println("Swipe looping done...");
		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}

		System.out.println("element found...");
		int y = element.getLocation().getY();
		logger.info("element.getLocation().getY(): "+y);
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
			if (y < display_y_min) {
				swipeUpLittle();
				logger.info("swipe Up Little...");
			} else if (y > display_y_max) {
				swipeDownLittle();
				logger.info("swipe Down Little...");
			}
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		logger.info("exiting function");
		delay(2);
		return element;
	}



	public MobileElement swipeUpUntilElementVisible(MobileElement element) {
		logger.info("func: swipe UP Until Element Visible...");
		for(int i = 1; i < 15; i++) {
			try {
//				if(element.getLocation().getY() >= 400) {
				if(element.getCenter().getY() >= display_y_min && element.isDisplayed() && element.getSize().height>5) {
					break;
				}
			} catch (Exception e) {
			}
//			dragCoordinates(driver, 450, 600, 450, 1400, swipe_time);
			dragCoordinates(DriverContext.driver, swipe_little_x_small, swipe_y_small, swipe_little_x_small, swipe_y_big, swipe_time);
		}

		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		int y = element.getLocation().getY();
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
//			dragCoordinates(driver, 450, 700, 450, 900, swipe_time);
			dragCoordinates(DriverContext.driver, swipe_little_x_small, swipe_little_y_small, swipe_little_x_small, swipe_little_y_big, swipe_time);
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		delay(2);
		return element;
	}

	public MobileElement swipeDownUntilElementVisible(MobileElement element) {
		logger.info("func: swipe DOWN Until Element Visible...");
		for(int i = 1; i < 11; i++) {
			try {
//				if(element.getLocation().getY() >= 400) {
				if(element.getLocation().getY() >= display_y_min) {
					break;
				}
			} catch (Exception e) {
			}
//			dragCoordinates(driver, 450, 1400, 450, 600, swipe_time);
//			dragCoordinates(DriverContext.driver, swipe_little_x_small, swipe_y_big, swipe_little_x_small, swipe_y_small, swipe_time);
			dragCoordinates(DriverContext.driver, swipe_x, swipe_y_big, swipe_x, swipe_y_small, swipe_time);
		}

		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		int y = element.getLocation().getY();
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
//			dragCoordinates(driver, 450, 900, 450, 700, swipe_time);
			dragCoordinates(DriverContext.driver, swipe_little_x_small, swipe_little_y_big, swipe_little_x_small, swipe_little_y_small, swipe_time);
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		return element;
	}

	public void swipeCoordination(int fromX, int fromY, int toX, int toY, int duration) {
		dragCoordinates(DriverContext.driver, fromX, fromY, toX, toY, duration);
	}

	public void swipeDownOnLeftSide() {
//		dragCoordinates(driver, 230, 1400, 230, 600, swipe_time);
		dragCoordinates(DriverContext.driver, display_x_min, swipe_y_big, display_x_min, swipe_little_y_small, swipe_time);
	}

	public void swipeDown() {
		double fromHeight = DriverContext.driver.manage().window().getSize().height * 0.7;
		double toHeight = DriverContext.driver.manage().window().getSize().height * 0.2;
		double width = DriverContext.driver.manage().window().getSize().width * 0.2;

		if (strPlatform.contains("IOS")) {
			System.out.println("dragCoordinates: from " + width + "," + fromHeight + ", to " + width + "," + toHeight);
			dragCoordinates(DriverContext.driver, width, fromHeight, width, toHeight, swipe_time);
		}else {
//			dragCoordinates(driver, 450, 1400, 450, 600, swipe_time);
			dragCoordinates(DriverContext.driver, display_x_min, swipe_y_big, display_x_min, swipe_little_y_small, swipe_time);
		}
	}

	public void swipeDownFast() {
		double fromHeight = DriverContext.driver.manage().window().getSize().height * 0.7;
		double toHeight = DriverContext.driver.manage().window().getSize().height * 0.1;
		double width = DriverContext.driver.manage().window().getSize().width * 0.2;
		if (strPlatform.contains("IOS")) {
			System.out.println("dragCoordinates: from " + width + "," + fromHeight + ", to " + width + "," + toHeight);
			dragCoordinates(DriverContext.driver, width, fromHeight, width, toHeight, 500);
		}else {
			dragCoordinates(DriverContext.driver, display_x_min, fromHeight, display_x_min, toHeight, 1000);
		}
		delay(1);
	}
	public void swipeUp() {
		dragCoordinates(DriverContext.driver, display_x_min, swipe_little_y_small, display_x_min, swipe_y_big, swipe_time);
	}

	public void swipeUpLittle() {
//		dragCoordinates(driver, 50, 700, 50, 1200, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_small, swipe_little_y_small, swipe_x_small, swipe_little_y_big, swipe_time);
	}

	public void swipeDownLittle() {
//		dragCoordinates(driver, 450, 1200, 450, 700, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_small, swipe_little_y_big, swipe_x_small, swipe_little_y_small, swipe_time);
	}

	public void swipeDownLot() {
//		dragCoordinates(driver, 450, 1200, 450, 700, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_small, swipe_y_big, swipe_x_small, swipe_y_small, swipe_time);
	}

	public MobileElement swipeLeft2ElementVisible(MobileElement element){
		Point center = element.getCenter();
		int ct_y = center.y;

		int ele_x = element.getLocation().x;
		//int ele_y = element.getLocation().y;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;

		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = ct_y;
		int to_x = (int)(mid_screen_width * 0.25);
		int to_y = ct_y;

		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth) {
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);
			} catch (Exception e) {
			}

			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
		}

		System.out.println("element found...");

		return element;

	}

	public void swipeRightFromElement(MobileElement element){
		Point point = element.getCenter();
		dragCoordinates(DriverContext.driver,point.x,point.y,0,point.y,swipe_time);
	}

	public void swipeRight() {
//		dragCoordinates(driver, 840, 270, 270, 270, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_big, swipe_y_small, swipe_x_small, swipe_y_small, swipe_time);
	}

	public void swipeRight(int y) {
//		dragCoordinates(driver, 840, 270, 270, 270, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_big, y, swipe_x_small, y, swipe_time);
	}

	public void swipeRight(MobileElement ele, int swipe_times) {
		int yPosition = ele.getCenter().getY();
		for (int i = 0; i<swipe_times;i++){
			swipeRight(yPosition);
		}
	}

	public void swipeLeft(int y) {
		dragCoordinates(DriverContext.driver, swipe_x_small, y, swipe_x_big, y, swipe_time);
	}

	public void swipeLeft(MobileElement ele, int swipe_times) {
		int yPosition = ele.getCenter().getY();
		for (int i = 0; i<swipe_times;i++){
			swipeLeft(yPosition);
		}
	}

	public void swipeRightOnVitalityTopSubmenu() {
		DriverContext.driver.context("NATIVE");
		System.out.println(DriverContext.driver.getContext() + ", list : " + DriverContext.driver.getContextHandles());
		int height = DriverContext.getDriver().manage().window().getSize().height;
		int width = DriverContext.getDriver().manage().window().getSize().width;
		System.out.println("ready to Swipe Submenu");
		dragCoordinates(DriverContext.driver, (int)(width*0.782), (int)(height*0.125), (int)(width*0.106),  (int)(height*0.125), swipe_time);
//		new TouchAction<>(DriverContext.getDriver()).longPress(PointOption.point((int)(width*0.782), (int)(height*0.125))).moveTo(PointOption.point((int)(width*0.106),  (int)(height*0.125))).release()
//		.perform();
		delay(2);
	}

	public void swipeRightOnVitalityTopSubmenu(int y) {
		DriverContext.driver.context("NATIVE");
		System.out.println(DriverContext.driver.getContext() + ", list : " + DriverContext.driver.getContextHandles());
		int height = DriverContext.getDriver().manage().window().getSize().height;
		int width = DriverContext.getDriver().manage().window().getSize().width;
		System.out.println("ready to Swipe Submenu");
		dragCoordinates(DriverContext.driver, swipe_x_big, y, swipe_little_x_small,  y, swipe_time);
//		new TouchAction<>(DriverContext.getDriver()).longPress(PointOption.point((int)(width*0.782), (int)(height*0.125))).moveTo(PointOption.point((int)(width*0.106),  (int)(height*0.125))).release()
//		.perform();
		delay(2);
	}

	public void swipeLeftMiddle() {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_big, swipe_little_y_small, swipe_x_small, swipe_little_y_small, swipe_time);
	}

	public void swipeLeftMiddle(int y) {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_big, y, swipe_x_small, y, swipe_time);
	}

	public void swipeMiddleLeft() {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(DriverContext.driver, swipe_x_big, swipe_little_y_small, swipe_x_small, swipe_little_y_small, swipe_time);
	}

	public static void pressEnter() {
		DriverContext.driver.executeScript("seetest:client.deviceAction('Enter')");
	}

	public MobileElement swipeToBottom(MobileElement element) {

		for(int i = 1; i < 51; i++) {
			swipeDownLittle();
			try {
				System.out.println(element.getLocation().getY());
				System.out.println(display_y_max);
				if(element.getLocation().getY() <= display_y_max) {
					break;
				}
			} catch (Exception e) {
			}
			catch (Error e) {
			}
//			addCapture("swipe down to find buttom: " + i);
			new TouchAction(DriverContext.driver).press(PointOption.point(50, screenHeight-300)).moveTo(PointOption.point(50, 400)).release().perform();
		}

		if (element == null) {
			logger.info("element not found after swipe 50 times");
			throw new RuntimeException("element not found");
		}

		delay(1);
		return element;
	}

	public void moveSeekbar(MobileElement element, double percent) {
		// ???????
		int width = element.getSize().getWidth();
		// ????
		int x = element.getLocation().getX() + 40;
		int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
		dragCoordinates(DriverContext.driver, x, y, (int) (width * percent) + x, y, swipe_time);
	}

	public void swipeDownLeftPanel() {

		if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			dragCoordinates(DriverContext.driver, 220, 1185, 220, 600, 1000);
		} else {
			dragCoordinates(DriverContext.driver, 170, 1185, 170, 600, 1000);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void checkSize(MobileElement element, int width, int height) throws Exception {
		int actualWidth = element.getSize().getWidth();
		int actualHeight = element.getSize().getHeight();
		System.out.println("actualWidth: "+ actualWidth);
		System.out.println("actualHeight: "+ actualHeight);

		Assert.assertTrue(width == actualWidth, "check width");
		Assert.assertTrue(height == actualHeight, "check height");
	}

	public void clearAndSendKeys(MobileElement element, String message){
		element.clear();
		element.click();
//		client.sendText(message);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			DriverContext.driver.hideKeyboard();
		}

		hideKeyboardByDone();
	}

	public void clickAndSendKeys(MobileElement element, String message){
		clickAndSendKeys(element,message,false);
	}

	public void clickAndSendKeys(MobileElement element, String message,boolean clear){
		By doneKeybordBy = By.xpath("//*[@text='Done' or @text='完成']");
		element.click();
		delay(1);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			DriverContext.driver.hideKeyboard();
		}

		if(clear){
			delay(1);
			element.clear();
			delay(1);
		}

//		client.sendText(message);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			DriverContext.driver.hideKeyboard();
		}else {
			if (AppiumUtil.isElementPresent(doneKeybordBy, 2)) {
				DriverContext.driver.findElement(doneKeybordBy).click();
			}
		}
	}


	protected void inputTextField(MobileElement textField,String value){
//		AppiumUtil.isElementPresentWithCheckLoading(textField,10);
		sendKeysInIOSAndAOS(textField,value);

		//hideKeyboardByDone();
	}

	protected void inputTextFieldAfterClear(MobileElement textField,String value){
		delay(2);

		clickAndSendKeys(textField,value,true);

		//hideKeyboardByDone();
	}

	public static void swipeScreenUp(AppiumDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile:swipe", scrollObject);

	}

	public void turnOnInstrumented() {
    	DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
		System.out.println(DriverContext.getDriver().getContext());
	}

	public void turnOnWebView() {
//    	DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
    	DriverContext.driver.context("WEBVIEW_1");
		System.out.println(DriverContext.getDriver().getContext());
	}

	public void turnBackNative() {
    	DriverContext.driver.context("NATIVE_APP");
		System.out.println(DriverContext.getDriver().getContext());
	}

	public void turnOnInstrumentedAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
		}
		System.out.println(DriverContext.getDriver().getContext());
	}

	public void turnOnWebViewAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
			DriverContext.driver.context("WEBVIEW_1");
		}
		System.out.println(DriverContext.getDriver().getContext());
	}

	public void turnBackNativeAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			DriverContext.driver.context("NATIVE_APP");
		}
		System.out.println(DriverContext.getDriver().getContext());
	}

	protected void selectPickerValue(String value){
		System.out.println("expected picker value:"+value);
		DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");

		By by = By.xpath("//*[@class='WKOptionPickerCell' and ./*[@class='UIImageView']]");
		MobileElement defaultOption = waitElementPresent(by);
		Point center = defaultOption.getCenter();
		int height = Integer.parseInt(defaultOption.getAttribute("height"));
		System.out.println(center.x+","+center.y+", height:"+ height);
		DriverContext.driver.context("NATIVE_APP");

		int fromX = screenWidth/2;
		int fromY = center.y+ height;
		int toY = center.y;

		for(int i =0;i<20;i++){
			dragCoordinates(DriverContext.driver,fromX,fromY,fromX,toY,1000);

			clickCoordinate(center.x,center.y);

			by = By.xpath("//*[@class='UIAPicker']/*[1]");
			MobileElement selectedOption = waitElementPresent(by);
			String optionValue = selectedOption.getAttribute("value");
			System.out.println("optionValue:"+optionValue);
			if(optionValue.equals(value)){
				break;
			}
		}

		by = By.xpath("//*[@text='Done']");
		if(isElementPresent(by,3)){
			DriverContext.driver.findElement(by).click();
		}
	}

	protected void SwipeUpToSelectPickerValue(String value){
		System.out.println(value);

		By by = By.xpath("//*[@class='UIAPicker']/*[1]");
		MobileElement defaultOption = waitElementPresent(by);
		String defaultValue = defaultOption.getAttribute("value");
		System.out.println("Default option Value:"+defaultValue);

		if(value.equals(defaultValue)){
			hideKeyboardByDone();
			return;
		}

		DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");

		String xpath = "//*[@class='UIPickerColumnView']//*[@alpha='1' and @text='"+defaultValue+"']";
		by = By.xpath(xpath);

		if(!isElementPresent(by,3)){
			xpath = "//*[@accessibilityLabel='"+defaultValue+"' and @class='WKOptionPickerCell']";//请选择
			by = By.xpath(xpath);
		}

		 MobileElement option = DriverContext.driver.findElement(by);

		Point center = option.getCenter();
		int height = Integer.parseInt(option.getAttribute("height"));
		System.out.println(center.x+","+center.y+", height:"+ height);
		DriverContext.driver.context("NATIVE_APP");

		int fromX = screenWidth/2;
		int fromY = center.y- height;
		int toY = center.y;

		for(int i =0;i<20;i++){
			dragCoordinates(DriverContext.driver,fromX,fromY,fromX,toY,1000);

			clickCoordinate(center.x,center.y);

			by = By.xpath("//*[@class='UIAPicker']/*[1]");
			MobileElement selectedOption = waitElementPresent(by);
			String optionValue = selectedOption.getAttribute("value");
			System.out.println("optionValue:"+optionValue);
			if(optionValue.equals(value)){
				break;
			}
		}

		hideKeyboardByDone();
	}

	public void tapUntilElementShow(MobileElement ele_click, MobileElement ele_shown){
		int click_count = 0;
		System.out.println("tap Until show");
		while ((!AppiumUtil.isElementPresent(ele_shown,2)) && click_count < 10){
			tapOnElement(ele_click);
			delay(1);
			click_count ++;
			System.out.println("click count: " + click_count);
		}
	}
	public void clickUntilElementShow(MobileElement ele_click, MobileElement ele_shown){
		int click_count = 0;
		System.out.println("click Until show");
    	while ((!AppiumUtil.isElementPresent(ele_shown,2)) && click_count < 10){
			ele_click.click();
			delay(1);
			click_count ++;
			System.out.println("click count: " + click_count);
		}

	}

	public void clickUntilDone(MobileElement ele) {
		try {
			System.out.println("click Until Done");
			int click_count = 0;
			while (ele.isEnabled() && click_count < 10) {
				ele.click();
				delay(1);
				click_count ++;
				System.out.println("click count: " + click_count);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

//	/**
//	 *
//	 * @param DD_MM_YYYY eg:01 Mar 1965
//	 */
	public static void sendDateForIOS(String date) {
		Date today = Calendar.getInstance().getTime();
		String[] today_list = String.valueOf(today).split(" ");
		String[] date_list = date.split(" ");
		String yearXpath = "//*[@text='" + today_list[5] + "']";
		String picker = "//*[@class='UIAPicker']";
		String monthXpath = "//*[@text='" + today_list[1] + "']";
		MobileElement b_Done_Keybord = DriverContext.driver.findElement(By.xpath("//*[@text='Done' or @text='完成']"));
		String day = date_list[0];
		String month = date_list[1];
		String year = date_list[2];

		System.out.println("Select Year...");
		DriverContext.driver.findElement(By.xpath(yearXpath)).click();
		scrollToListItem(picker, year);
//		b_Done_Keybord.click();

		System.out.println("Select Month...");
		DriverContext.driver.findElement(By.xpath(monthXpath)).click();
		scrollToListItem(picker, month);
//		b_Done_Keybord.click();

		System.out.println("Select Day...");
		String xpath;
		if(day.startsWith("0")){
			xpath = "(//*[@text='"+day.substring(1)+"' and @y>0])[1]";
		}else{
			xpath = "(//*[@text='" + day + "'])[last()]";
		}
		System.out.println(xpath);
		DriverContext.driver.findElement(By.xpath(xpath)).click();
//		b_Done_Keybord.click();
	}

	public void switchInstrumentedAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			DriverContext.driver.context("NATIVE_APP_INSTRUMENTED");
			DriverContext.driver.context("NATIVE_APP");
		}
	}

	public void switchInstrumentedAndroidWEBVIEW() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			DriverContext.driver.context("WEBVIEW_1");
			DriverContext.driver.context("NATIVE_APP");
		}
	}

	public void swipeDownValuePicker(MobileElement element) {
		int x = element.getCenter().getX();
		int yStart = element.getCenter().getY() + element.getSize().getHeight()/5;
		int yEnd = element.getCenter().getY();
		dragCoordinates(DriverContext.driver, x, yStart, x, yEnd , swipe_time);
	}

	public int getDocumentSize(){
		String xpath = "//*[@nodeName='document']";
		int size = 0;
		try{
			size = DriverContext.getDriver().findElementsByXPath(xpath).size();
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println(size);
		return size;
	}

	public MobileElement findEleInDocument(MobileElement element){
		MobileElement ele=null;
		int document_size = getDocumentSize();
		System.out.println("size : " + document_size);

		if (document_size != 0){
		for (int i = 1 ; i< (document_size+1) ; i++){
//			Base.getClient().setDefaultWebView(String.valueOf(i));
			System.out.println("WebView : " + i);
				if (AppiumUtil.isElementPresent(element,ConstantFile.TIMEOUT3)){
				ele = element;
				break;
				}
			}
		}else {
			ele = element;
		}
		return ele;
	}

	private String getReferenceImageB64(String photoName) throws URISyntaxException, IOException {
		URL refImgUrl = getClass().getClassLoader().getResource(photoName);
		File refImgFile = Paths.get(refImgUrl.toURI()).toFile();
		return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
	}

}
//testing