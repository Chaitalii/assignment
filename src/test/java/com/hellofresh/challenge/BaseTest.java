package com.hellofresh.challenge;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import utilities.Utils;

/*
 * This is the base class in the framework; all the @before/@after configurations are maintained here.
 */
public class BaseTest extends BrowserFactory{

String homePageUrl="http://automationpractice.com/index.php";
public HashMap<String, String> data = new HashMap<>();


@BeforeSuite
public void disableFreeMaker(){
//	This is just to disable freemaker logging
	System.setProperty("org.freemarker.loggerLibrary", "none");
//	Utils util=new Utils();
//	data = util.getDataFromExcel();
}
  


    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) throws Exception {
//    	Get the browser instance
    	getInstance();
//    	Set the browser as per configuration
    	setDriver(browser);
//    	Maximize teh window
        getDriver().manage().window().maximize();
//       Launch the homepage url
    	getDriver().get(homePageUrl);
    	
  
    }
    @AfterMethod
    public void tearDown(ITestResult iTestResult) throws IOException {
        getDriver().quit();
    }
    @AfterClass
    public void terminateThreadElement(){
    	terminate();
    }
    
    
    
}
    