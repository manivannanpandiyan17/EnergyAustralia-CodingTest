package stepDefinition;


import org.junit.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FestivalsPage;

public class StepDefinition {

	FestivalsPage fp = new FestivalsPage();

	@Given("I launch the mobile app")
	public void i_launch_the_mobile_app() {
		try {
			fp.launchApplication();
			
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to launch the app");
		}
	}
	
	@When("I fetch data from API")
	public void i_fetch_data_from_api() {

		try {
			fp.getAPIResponse();
			
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to Get API response");
		}
	}


	@Then("the data fetched from API mathes with the Mobile UI")
	public void the_data_fetched_from_api_mathes_with_the_mobile_ui() {
		try {
			fp.verifyFestivalAndItsBand();
			
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to match Mobile UI with API data");
		}
	}

}
