package com.hellofresh.challenge;



import java.io.IOException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public class BaseTest extends BrowserFactory{

String homePageUrl="http://automationpractice.com/index.php";


@BeforeSuite
public void disableFreeMaker(){
//	This is just to disable freemake rlogging
	System.setProperty("org.freemarker.loggerLibrary", "none");
}
  
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) throws Exception {
       	getInstance();
    	setDriver(browser);
        getDriver().manage().window().maximize();
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
    