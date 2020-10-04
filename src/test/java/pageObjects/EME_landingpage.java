package pageObjects;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.excessmaterialsexchange.Base;
import com.aventstack.extentreports.Status;

public class EME_landingpage extends Base {
	public static Logger log = LogManager.getLogger(EME_landingpage.class.getName());
	WebDriver driver;

	public WebDriver EME_landingpages() throws IOException {
		driver = setupBrowser();
		return driver = openURL();
	}

	public WebDriver openURL() throws IOException {
		testEnv = extent.createTest("Check - Verify test URL loading");

		prop = dataset();

		driver.get(prop.getProperty("testURL"));
		log.info("Current URL is : " + testURL);
		testEnv.log(Status.INFO, "URL : " + prop.getProperty("testURL"));
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/login']"))).isDisplayed()) {

			System.out.println("Landing page redirection successful");
			testEnv.log(Status.PASS, "URL load successfully.");
			log.info("URL load successfully, " + testURL);

		} else {

			testEnv.log(Status.FAIL, "Fail to load URL" + capture(driver));
			log.error("URL failed to load : " + testURL);

		}
		return driver;
	}

}
