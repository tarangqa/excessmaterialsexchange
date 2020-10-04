package com.automation.excessmaterialsexchange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
	public WebDriver driver;

	public void login()
	{
		WebElement loginbutton = driver.findElement(By.xpath("//a[@href='/login']"));
	}
}
