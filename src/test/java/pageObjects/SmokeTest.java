package pageObjects;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class SmokeTest extends EME_LoginPage {
	public static Logger Smokelog = LogManager.getLogger(SmokeTest.class.getName());
	WebDriver driver;

	@Test
	public void smokerun() throws IOException {
		test_SmokeTest = extent.createTest("Smoke Test Demo Platform");

		driver = login();
		test_SmokeTest.log(Status.PASS, "Login functionality Pass.");
		Smokelog.info("Smoke Test Over");

	}
}
