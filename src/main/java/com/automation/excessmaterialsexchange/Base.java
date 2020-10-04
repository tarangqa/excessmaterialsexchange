/**
 * 
 */
package com.automation.excessmaterialsexchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * @author Dominic
 *
 */
public class Base {

	/**
	 * in Base file, Initialized >> WebDriver = Chrome, function =
	 * setupBrowser(), Extent report generation > Test Environment variable =
	 * testEnv, Screenshot capture = capture(driver),
	 */

	static WebDriver driver;
	protected WebDriverWait wait;
	protected Properties prop = new Properties();

	public String testURL = "https://test.excessmaterialsexchange.com/";

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest testEnv;
	public int WebDriverWaitTimeout = 300;
	public static boolean AssertEnable = false;

	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeSuite
	public void before() throws Throwable {
		// htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")
		// + "\\ExtentReports\\Automation_Report_" + dateFormat.format(new
		// Date()) + ".html");
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\Automation_Report\\EME_AutomationReport.html");
		htmlReporter.getTestRunnerLogs();
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// htmlReporter.loadXMLConfig("src/main/java/resources/extent-config.xml");
		// /* for manual config in Extent report HTML*/

		/* HTML report properties */
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setJS("js-string");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setReportName("Tarang | Automation Report");
		htmlReporter.config().setDocumentTitle("Extent-Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config()
				.setCSS("body > nav > div > a > img { content:url('http://tarangchokshi.in/img/favicon_png.png') }");

	}

	@AfterSuite
	public void extentReportClosure() {
		driver.close();
		extent.flush();
	}

	public WebDriver setupBrowser() {

		prop = dataset();
		String browserName = prop.getProperty("browser"); // This is to read broswer value defined in properties file
		System.out.println(browserName);
		log.info("Current browser is : " + browserName);

		if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		} else if (browserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 100);
		wait = new WebDriverWait(driver, WebDriverWaitTimeout);

		return driver;

	}

	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(System.getProperty("user.dir") + "/ScreenShots/" + "ErrSnap_" + System.currentTimeMillis()
				+ dateFormat.format(new Date()) + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

	public Properties dataset() {

		File file = new File("src//main//java//resources//data.properties");
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

}
