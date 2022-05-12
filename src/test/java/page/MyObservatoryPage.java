package page;

import base.BasePage;
import base.DriverContext;
import com.MyObservatory.constant.Xpath_Locator_Android;
import com.MyObservatory.constant.Xpath_Locator_IOS;
//import com.aiatss.uplifttest.constant.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import utilities.AppiumUtil;
import utilities.ExcelUtil;
import utilities.ScreenShotUtil;

public class MyObservatoryPage extends BasePage {

	private static AppiumDriver<MobileElement> driver = DriverContext.getDriver();

	public void checkMainPage(){
		if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator_Android.myObservatory.checkMainPage),20));
//			addCapture("Check Main Page is Present");
		} else {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator_IOS.myObservatory.checkMainPage),20));
//			addCapture("Check Main Page is Present");
		}
	}

	public void selectLanguage(){
			if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
				AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_Android.myObservatory.moreOptionBtn),10);
				AppiumUtil.clickElement(Xpath_Locator_Android.myObservatory.moreOptionBtn);
				AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_Android.myObservatory.languageDropList),10);
				AppiumUtil.clickElement(Xpath_Locator_Android.myObservatory.languageDropList);
				String selectLanguage = String.format("//*[@text='%s']",ExcelUtil.getValue("language"));
				AppiumUtil.clickElement(selectLanguage);
				ScreenShotUtil.addScreenShot();
			} else {
				AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_IOS.myObservatory.menuBtn),10);
				AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.menuBtn);
				AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_IOS.myObservatory.settingsBtn),10);
				AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.settingsBtn);
				AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_IOS.myObservatory.languageDropList),10);
				AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.languageDropList);
				String selectLanguage = String.format("//*[@text='%s']",ExcelUtil.getValue("language"));
				AppiumUtil.clickElement(selectLanguage);
				ScreenShotUtil.addScreenShot();
				AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.backBtn);
			}
	}

	public void click9DayForecast() throws Exception {
		if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_Android.myObservatory.menuBtn),10);
			AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.menuBtn);
			MobileElement swipeTo9DayForecast = DriverContext.driver.findElementByXPath(Xpath_Locator_Android.myObservatory.click9DayForecast);
			AppiumUtil.swipeElementVisible(swipeTo9DayForecast);
			AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_Android.myObservatory.click9DayForecast),10);
			AppiumUtil.clickElement(Xpath_Locator_Android.myObservatory.click9DayForecast);
			ScreenShotUtil.addScreenShot();
		} else {
			AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_IOS.myObservatory.click9DayForecast),10);
			AppiumUtil.clickElement(Xpath_Locator_IOS.myObservatory.click9DayForecast);
			ScreenShotUtil.addScreenShot();
		}
	}

	public void check9DayForecast(){
		if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_Android.myObservatory.check9DayForecast),10);
			ScreenShotUtil.addScreenShot();
		} else {
			AppiumUtil.waitElementClickable(By.xpath(Xpath_Locator_IOS.myObservatory.check9DayForecast),10);
			ScreenShotUtil.addScreenShot();
		}
	}

	public void checkTmrForecast(){
		if ("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator_Android.myObservatory.checkTmrForecast),20));
			ScreenShotUtil.addScreenShot();
		} else {
			Assert.assertTrue(AppiumUtil.isElementPresent(By.xpath(Xpath_Locator_IOS.myObservatory.checkTmrForecast),20));
			ScreenShotUtil.addScreenShot();
		}
	}
}



