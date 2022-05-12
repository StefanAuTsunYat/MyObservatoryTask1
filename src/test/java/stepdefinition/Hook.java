package stepdefinition;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.java.AfterStep;
import org.apache.commons.lang3.reflect.FieldUtils;

import base.Base;
import base.DriverContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import utilities.ExcelUtil;

public class Hook extends Base {

	@Before
	public void beforeScenario(Scenario scenario) throws IOException {
//		Base.setScenario(scenario);
		String testResultFolder = ExtentService.getScreenshotFolderName().split("/")[1];
		System.setProperty("currReportFolder", testResultFolder);
		Collection<String> tags = scenario.getSourceTagNames();
		for(String tag: tags){
			System.out.println(tag);
		}
		Base.setCaseID(scenario.getName());
		// System.out.println("This will run before the Scenario");
		System.out.println("-----------------------------------");
		System.out.println("Starting - " + scenario.getName());
		System.out.println("-----------------------------------");

		String platform = scenario.getName().endsWith("IOS")? "IOS" : "ANDROID";
		setCapabilities();
		ExcelUtil.readExcel(scenario.getName());
	}
	

	@After
	public void afterScenario(Scenario scenario) {
		System.out.println("-----------------------------------");
		System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
		System.out.println("Run time - " + getRunTime());
		System.out.println("-----------------------------------");
			

//		if ("failed".equals(scenario.getStatus())) {
		if ("FAILED".equals(scenario.getStatus().toString())) {
//			Throwable error = getError(scenario);

//			Base.setErrorMsg(error.getMessage());
//			Base.addCapture();

//			String path = ConstantFile.BASE_DIR + File.separator + "output" + File.separator + "logs" + File.separator + Base.getCaseID();
//			try {
//				FileOutputStream fos = new FileOutputStream(path);
//				BufferedOutputStream bos = new BufferedOutputStream(fos);

//				StringWriter sw = new StringWriter();/
//				PrintWriter pw = new PrintWriter(sw);
//				error.printStackTrace(pw);

//				Calendar calendar= Calendar.getInstance();
//				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

//				String content = calendar.getTime() + System.getProperty("line.separator") + sw.toString();

//				bos.write(content.getBytes(), 0 , content.getBytes().length);
//				bos.flush();
//				bos.close();
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//			client.setReportStatus("failed", "Test case failed");
//			renameReportFolder("fail");
//		}else {
//			client.setReportStatus("passed", "Test case passed");
//			renameReportFolder("pass");
//		}
		}
//		if ("FAILED".equals(scenario.getStatus().toString())) {
//			client.setReportStatus("failed", "Test case failed");
//		} else {
//			client.setReportStatus("passed", "Test case passed");
//		}
		try {
			DriverContext.getDriver().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DriverContext.getDriver().quit(); // Added by DF
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DriverContext.setDriver(null); // Added by DF
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterStep
	public void afterStep(Scenario scenario) {

		if ("FAILED".equals(scenario.getStatus().toString())) {
//			Base.saveCucumberScreenShotForStep("error");
		}

	}

	private static Throwable getError(Scenario scenario){
		Field field = FieldUtils.getField(((Scenario) scenario).getClass(), "stepResults", true);
		field.setAccessible(true);

		try{
			ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
			for(Result result : results){
				if(result.getError() != null){
					return result.getError();
				}
			}
		}catch (Exception e){
			System.out.println("fail to get error");
		}

		return null;
	}
}
