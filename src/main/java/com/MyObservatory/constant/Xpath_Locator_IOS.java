package com.MyObservatory.constant;

import utilities.ExcelUtil;

public class Xpath_Locator_IOS {

	public static class myObservatory {
		public static final String checkMainPage = "//*[@text='MyObservatory' or @text='我的天文台' and @class='UIAStaticText']";
		public static final String menuBtn = "//*[@text='Menu, left side panel']";
		public static final String settingsBtn = "//*[@text='Settings' and (./preceding-sibling::* | ./following-sibling::*)[@class='UIAImage'] and ./parent::*[@class='UIAView']]";
		public static final String languageDropList = "//*[contains(@text,'LANGUAGE')]/following::*/following::*";
		public static final String backBtn = "//*[@text='Back']";
		public static final String click9DayForecast = "//*[@text='九天预报' or @text='九天預報' or @text='9-Day Forecast']";
		public static final String check9DayForecast = "//*[@text='天气预报' or @text='天氣預報' or @text='Weather Forecast' and @class='UIAStaticText']";
		public static final String checkTmrForecast = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";
	}
}



