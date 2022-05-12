package stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.MyObservatoryPage;

public class MyObservatoryStep {
	private MyObservatoryPage myObservatoryPage = new MyObservatoryPage();

	@Given("User is on main Page")
	public void checkMainPage() {
		myObservatoryPage.checkMainPage();
	}

	@When("User selects language")
	public void userSelectsLanguage() {
		myObservatoryPage.selectLanguage();
	}

	@Then("User clicks and checks 9-Day Forecast")
	public void userClicksDayForecastButton() throws Exception {
		myObservatoryPage.click9DayForecast();
		myObservatoryPage.check9DayForecast();
	}

	@And("User checks weather forecast tomorrow")
	public void userChecksWeatherForecastTomorrow() {
		myObservatoryPage.checkTmrForecast();
	}
}