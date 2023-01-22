package pages;

import org.junit.Assert;
import commonLibrary.LibraryFunctions;

public class FestivalsPage extends LibraryFunctions {

	String festivalName = "//*[@resource-id='com.energyaustralia.codingtestsample:id/festivalTextView' and @text='%s']//parent::*/android.widget.TextView[1]";
	String url = "https://eacp.energyaustralia.com.au/";
	String basePath = "codingtest/api/v1/festivals";

	// Launch Application
	public void launchApplication() {
		try {
			setup();
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to launch the app");
		}
	}

	// Get API Response
	public void getAPIResponse() {
		try {
			getResponse(url, basePath);
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to launch the app");
		}

	}

	public void verifyFestivalAndItsBand() {
		try {
			fetchFestivalName();
			for (int i = 0; i < festivals.size(); i++) {
				getBandListForFestivalsUI(String.format(festivalName, festivals.get(0)));
				getBandListForFestivalsAPI(festivals.get(i),i);
				Assert.assertEquals(bandListUI.size(), bandName.size());
				System.out.println("BandLists in UI : " + bandListUI);
				System.out.println("BandLists in API : " + bandListAPI);
				Assert.assertTrue(bandListUI.equals(bandListAPI));
			}

		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to match Mobile UI with API data");
		}

	}
}
