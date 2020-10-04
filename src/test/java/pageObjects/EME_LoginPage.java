package pageObjects;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import junit.framework.Assert;

public class EME_LoginPage extends EME_landingpage {
	public static Logger log = LogManager.getLogger(EME_LoginPage.class.getName());
	WebDriver driver;

	@Test
	public void login() throws IOException {

		driver = EME_landingpages();

		testEnv = extent.createTest("Check - verify login page redirection");

		driver.findElement(By.xpath("//a[@href='/login']")).click();
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'email')]")))
				.isDisplayed()) {

			testEnv.log(Status.PASS, "Login page load successfully.");
			log.info("login page load success");

		} else {

			testEnv.log(Status.FAIL, "Fail to load Login Page" + capture(driver));
			log.info("login page load fail");

		}

		// Check Invalid Functionality
		testEnv = extent.createTest("Check Invalid Login Functionality");

		testEnv.log(Status.INFO, "Check with Invalid Username and valid password");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(prop.getProperty("invalidUsername"));
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(prop.getProperty("Password"));
		driver.findElement(By.xpath("//button[@label='SIGN IN']")).click();
		String expErrormsg = "No User Found with the provided email! Unable to Login!!";
		String actErrormsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='ant-message-custom-content ant-message-error']/span"))).getText();
		System.out.println(actErrormsg);
		Assert.assertEquals(expErrormsg, actErrormsg);

		driver.navigate().refresh();

		testEnv.log(Status.INFO, "Check with Valid Username and Invalid password");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(prop.getProperty("Username"));
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(prop.getProperty("invalidPassword"));
		driver.findElement(By.xpath("//button[@label='SIGN IN']")).click();
		String expErrormsg2 = "Email and Password Mismatch! Unable to Login!!";
		String actErrormsg2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='ant-message-custom-content ant-message-error']/span"))).getText();
		System.out.println(actErrormsg2);
		Assert.assertEquals(expErrormsg2, actErrormsg2);

		// Check Valid login functionality
		driver.navigate().refresh();

		testEnv = extent.createTest("Check Valid Login Functionality");

		testEnv.log(Status.INFO, "Check with valid Username and valid password");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(prop.getProperty("Username"));
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(prop.getProperty("Password"));
		driver.findElement(By.xpath("//button[@label='SIGN IN']")).click();

		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Marketplace')]")))
				.isDisplayed()) {

			testEnv.log(Status.PASS, "Login functionality Pass.");
			log.info("Login Successfull");

		} else {

			testEnv.log(Status.FAIL, "Login failed with valid credentials" + capture(driver));
			log.error("Login failed with valid credentials");

		}
	}

}
