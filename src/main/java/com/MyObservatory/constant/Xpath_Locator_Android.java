package com.MyObservatory.constant;

import utilities.ExcelUtil;

public class Xpath_Locator_Android {

		public static class myObservatory {
			public static final String checkMainPage = "//*[@text='MyObservatory' or @text='我的天文台']";
			public static final String moreOptionBtn = "//*[@contentDescription='More options']";
			public static final String menuBtn = "//*[@contentDescription='Navigate up']";
			public static final String languageDropList = "//*[@text='語言 / Language' or @text='Language / 語言' or @text='语言 / Language']";
			public static final String click9DayForecast = "//*[@text='九天预报' or @text='九天預報' or @text='9-Day Forecast' and @id='text']";
			public static final String check9DayForecast = "//*[@text='天气预报' or @text='天氣預報' or @text='Weather Forecast']";
			public static final String checkTmrForecast = "(//*[@id='sevenday_forecast_date' and (./preceding-sibling::* | ./following-sibling::*)[@contentDescription]])[1]";
		}
}
