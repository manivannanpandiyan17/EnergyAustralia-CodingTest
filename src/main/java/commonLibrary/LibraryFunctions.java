package commonLibrary;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LibraryFunctions {

	public static AppiumDriver driver = null;
	private static AppiumDriverLocalService service = null;
	private static String AppiumNodeFilePath = "/opt/homebrew/bin/node";
	private static String AppiumJavaScriptServerFile = "/opt/homebrew/lib/node_modules/appium/build/lib/main.js";
	Response response;
	protected ArrayList<String> festivals;
	protected Set<String> bandListUI;
	protected Set<String> bandListAPI;
	protected ArrayList<String> bandName;
	JsonPath jsonPathEvaluator;



	public void setup() throws IOException, InterruptedException {

		try {

			// startAppiumServer();
			File appDir = new File("src/test/resources/app");
			File app = new File(appDir, "app-debug.apk");

			// Setting all the required desired capabilities for Main App
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "emulator-5554");
			capabilities.setCapability("automationName", "UIAutomator2");
			capabilities.setCapability("appWaitPackage", "com.energyaustralia.codingtestsample");
			// capabilities.setCapability("appWaitActivity", "android.intent.action.MAIN");
			capabilities.setCapability("app", app.getAbsolutePath());
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error while setting up appium");
		}
	}

	public static void startAppiumServer() throws IOException, InterruptedException {
		try {
			stopAppiumServer();
			System.out.println("Starting Appium Server .....");

			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.usingDriverExecutable(new File(AppiumNodeFilePath))
					.withAppiumJS(new File(AppiumJavaScriptServerFile)).withIPAddress("127.0.0.1").usingPort(4723)
					.withLogFile(new File("** txt")).withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/"));

			service.start();
			System.out.println("Appium Server Started !!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Unable to start appium server");
		}
	}

	public static void stopAppiumServer() throws IOException {
		try {
			service.stop();
			System.out.println("Appium Server is now Stopped!!");
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Appium Server is already Stopped !!");
		}
	}

	public Set<String> getBandListForFestivalsUI(String Festival) {


		try {
			List<WebElement> elements = driver.findElements(By.xpath(Festival));
			bandListUI = new HashSet<String>();
			System.out.println("Number of elements:" + elements.size());
			for (int i = 0; i < elements.size(); i++) {
				bandListUI.add(elements.get(i).getText());
			}
			scrollDown();
			Thread.sleep(10000);

			elements = driver.findElements(By.xpath(Festival));

			for (int i = 0; i < elements.size(); i++) {
				bandListUI.add(elements.get(i).getText());
			}
			System.out.println("Band List on Mobile " + bandListUI);
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error while getting list of Bands");
		}

		return bandListUI;

	}

	public void scrollDown() throws Throwable {

		try {
			WebElement ele01 = driver.findElement(AppiumBy.xpath("//*[@index='10']"));

			int centerX = ele01.getRect().x + (ele01.getSize().width / 2);

			double startY = ele01.getRect().y + (ele01.getSize().height * 2.8);

			double endY = ele01.getRect().y + (ele01.getSize().height * 0.1);
			// Type of Pointer Input
			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			// Creating Sequence object to add actions
			Sequence swipe = new Sequence(finger, 1);
			// Move finger into starting position
			swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX,
					(int) startY));
			// Finger comes down into contact with screen
			swipe.addAction(finger.createPointerDown(0));
			// Finger moves to end position
			swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX,
					(int) endY));
			// Get up Finger from Srceen
			swipe.addAction(finger.createPointerUp(0));

			// Perform the actions
			driver.perform(Arrays.asList(swipe));

		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error in scrolling down");
		}

	}

	// Establish API connection and Get Response
	public void getResponse(String url, String basePath) {
			
		try {
			RestAssured.baseURI = url;
			RestAssured.basePath = basePath;
			response = RestAssured.given().when().get();
			System.out.println("Response : " + response.asString());
			System.out.println("Response Code : " + response.getStatusCode());
		}
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error in fetching API response");
		}
	}
	
	// Fetch Festival Names
	public void fetchFestivalName() {
		
		try {
			jsonPathEvaluator = response.jsonPath();
			festivals = jsonPathEvaluator.get("name");
			System.out.println("Festival Name : " + festivals);
		}
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error Parsing API response while fetching festival Name");
		}
	}
	
	//Fetch BandName for Festivals
public void getBandListForFestivalsAPI(String festival, int index) {
		
		try {
			bandName = new ArrayList<String>();
			bandName = jsonPathEvaluator.get("bands["+index+"].name");
			System.out.println("Festival Name : " + festivals.get(0));
			System.out.println("Band Name : " + bandName);
			bandListAPI= new HashSet<String>(bandName);

		}
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error Parsing API response while fetching Band Name");
		}
	}


}
