package com.southwest.tests;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.southwest.pages.FlightsPage;
import com.southwest.pages.HomePage;

public class VerifyNumberofDeals {
	
	WebDriver driver;
	HomePage homePage;
	FlightsPage fp;
	Logger logger;
		
		@Parameters({"url","browserType"})
		@BeforeClass
		public void launchBrowser(String url,String browserType){
			
			if(browserType.equals("FF"))
			{
			    driver = new FirefoxDriver();
			    driver.manage().window().maximize();
			    System.out.println("Making channge for Github");
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
			
			logger=Logger.getLogger("VerifyNumberofFlightDeals");
	        
		    PropertyConfigurator.configure("Log4j.properties");
	        
		    logger.info("Browser Launched Successfully");
			
		    
		}
		
		@Parameters({"pageTitle","sourceplace","dest","startDate","endDate","noofAdults"})
		@Test
		public void verifyTitle(String pageTitle,String sourceplace,String dest,String startDate,String endDate,String noofAdults) {
			HomePage hp = new HomePage(driver);
			fp = hp.browsetoFlightPage();
			logger.info("Browsing to flights page");
			fp = hp.fillflightPage(sourceplace, dest, startDate, endDate,noofAdults );
			logger.info("filling the details to browse to deals page");
			boolean actresult = fp.verifyPageTitle(pageTitle);
			Assert.assertTrue(actresult);
			logger.info("Verify Flights Page Title is Passed");

			
		}
		
		@Parameters({"numberofFlightdeals"})
		@Test
		public void verifyflightDeals(String numberofFlightdeals){
			int actnumberofdeals = fp.verifynumberofFlights();
			int expectednumberofdeals = Integer.parseInt(numberofFlightdeals);
			Assert.assertEquals(actnumberofdeals, expectednumberofdeals);
			logger.info("Verify number of fligts deal check is passed");
			
		}

}
