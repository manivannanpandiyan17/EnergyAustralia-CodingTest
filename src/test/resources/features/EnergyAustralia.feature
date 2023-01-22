Feature: Energy Australia Coding Test
I want to verify the mobile app with API

  Scenario: Verify all Festival API data is available in mobile UI
  Given I launch the mobile app
  When I fetch data from API
  Then the data fetched from API mathes with the Mobile UI