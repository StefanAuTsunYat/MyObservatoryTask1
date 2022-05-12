@MyObservatory
Feature: MyObservatory Test
Description: This feature will test MyObservatory 9-Day Forecast features

  Background:
    Given User is on main Page

#Change any language in excel if needed (resource/TestData.xlsx)
  @ACTA @IOS @Android @MyObservatory_001
  Scenario: MyObservatory User is able to see 9-Day Forecast
    When User selects language
    Then User clicks and checks 9-Day Forecast
    And User checks weather forecast tomorrow


