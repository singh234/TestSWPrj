package com.retailmenot.tests;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.retailmenot.pages.DealsPage;
import com.retailmenot.pages.HomePage;
import com.retailmenot.util.Utility;

import jxl.read.biff.BiffException;

public class VerifyNumberofItem_Test {
	
   WebDriver driver;
   HomePage homePage;
   DealsPage dealsPage;
   Logger logger;
	
	@Parameters({"url","browserType"})
	@BeforeClass
	public void launchBrowser(String url,String browserType){
		
		if(browserType.equals("FF"))
		{
		    driver = new FirefoxDriver();
		}
		else if(browserType.equals("IE")){
			System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
		    driver = new InternetExplorerDriver();
		}
		else{
		    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		    driver = new ChromeDriver();
		}
		driver.get(url);
		
		logger=Logger.getLogger("VerifyNumberofItem_Test");
        
	    PropertyConfigurator.configure("Log4j.properties");
        
	    logger.info("Browser Launched Successfully");
		
	}
	
	
	
	@DataProvider(name="DP")
	public String[][] feedDP() throws BiffException, IOException{
		String data[][] = Utility.readExcel("VerifyDealsInput.xls", "sanity");
		return data;
	}
	
	@Parameters({"pageTitle"})
	@Test
	public void verifyDealPageTitle(String pageTitle){
		homePage = new HomePage(driver);
		dealsPage = homePage.browsetoDealPage();
		boolean actualTitle = dealsPage.verifydealsPage(pageTitle);
		Assert.assertTrue(actualTitle);
		logger.info("Verify Deals Page Title is Passed");
		
	}
	
	//@Parameters({"productCategory","dealsCount"})
	@Test(dependsOnMethods={"verifyDealPageTitle"},dataProvider="DP")
	public void verifyDealsCount(String productCategory,String dealsCount ){
		int actualCount = dealsPage.verifyDealsCount(productCategory);
		logger.info("Actual count of the products"+actualCount);
		int expectedCount = Integer.parseInt(dealsCount);
		Assert.assertEquals(actualCount, expectedCount);
		
	}

}
