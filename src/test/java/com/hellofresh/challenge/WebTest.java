package com.hellofresh.challenge;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.OrderConfirmationPage;
import pages.SignInPage;
import utilities.Utils;
import utilities.ExtentReports.ExtentTestManager;

/*
 * This is the test class. It has all test cases and the validations.
 */
public class WebTest extends BaseTest {

	Logger log = Logger.getLogger(WebTest.class);

	LoginPage login;
	SignInPage signIn;
	HomePage home;
	OrderConfirmationPage order;
	CheckoutPage checkOut;

	@DataProvider
	public Iterator<Object[]> getTestData() {
		Utils util = new Utils();
		ArrayList<Object[]> testData = util.getDataFromExcel();
		return testData.iterator();
	}

	/*
	 * This test case is for verifying the signin process for a new user.
	 * The data required for creating new user is passed externally from excel file.
	 * Execution logs can be found in application.log file and the report is placed in ExtentReports folder.
	 * 
	 */
	 @Test(dataProvider="getTestData",invocationCount = 20)
	public void signInTest(Method method, String exist_Email, String exist_Pwd, String email, String pwd, String name,
			String sName, String fName, String comp, String addr1, String addr2, String City, String post, String oth,
			String ph, String mob, String alias) throws Exception {
		ExtentTest logger = ExtentTestManager.startTest(method.getName(), "Create new User");
		signIn = new SignInPage();
		home = new HomePage();
		log.info("Starting SignIn Test");
		logger.log(LogStatus.INFO, "Starting SignIn Test");
		String heading = signIn.createAccount(email, pwd, name, sName, comp, addr1, addr2, City, post, oth, ph, mob,
				alias, logger);
		String loggedInUserName = home.retrieveUserNameAfterSignIn();
		String newAccountName = name + " " + sName;
		
		
		/*
		 *****  The assertion here is to verify if teh heading is dispalyed as expected.
		 *The heading should be dispalyed as "MY ACCOUNT" */
		
		try {
			assertEquals(heading, "MY ACCOUNT");
			logger.log(LogStatus.PASS, "The heading is correctly displayed");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL, "The heading is NOT correctly displayed");
			throw e;
		}
		
		/*
		 *****  The assertion here is to verify 
		 *if account name for newly created account 
		 *is dispalyed as expected(as entered during registration(First name + Surname)) ***
		 */
		try{
		Assert.assertEquals(loggedInUserName,newAccountName);
		logger.log(LogStatus.PASS, "New ccount name is displayed correctly");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL, "New account name and expected account name did not match");
			throw e;
		}
		
		/*
		 * This step is to verify if Welcome text is dispalyed as expected.
		 */
		
		try{
//			home.verifyWelcomeText();
			String welcomeText=home.getWelcomeText();
			Assert.assertTrue(welcomeText.contains("Welcome to your account."));
			log.info("Welcome text is  displayed as expected");
			logger.log(LogStatus.PASS, "Welcome text is displayed as expected");
			}catch (Exception e) {
				log.info("Welcome text is not displayed");
				logger.log(LogStatus.FAIL, "Welcome text is not displayed");
				log.info(e.toString());
				throw e;
			}
		/*
		 * This step is to verify if LogOut button is displayed
		 */
		try {
			home.verifyLogOutButtonisDisplayed();
			logger.log(LogStatus.PASS, "Logout button is displayed");
		} catch (Exception e) {
			log.info("Logout button is not dispalyed");
			log.info(e.toString());
			throw e;

		}
		
		
		/*
		 *****  The assertion here is to verify if my account page is correctly displayed. 
		 *The expected result is: The current url should have "?controller=my-account" text in it ***
		 */
		try{
		home.verifyMyAccountPageIsDisplayed();
		logger.log(LogStatus.PASS, "My account page(?controller=my-account) is opened ");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL, "My account page(?controller=my-account) is NOT correctly opened"+e.toString());
			throw e;
		}
		
		
		
		
		

	}

	@Test(dataProvider = "getTestData", invocationCount = 20)
	public void logInTest(Method method, String exist_Email, String exist_Pwd, String email, String pwd, String name,
			String surName, String fName, String comp, String addr1, String addr2, String City, String post, String oth,
			String ph, String mob, String alias) throws Exception {
		ExtentTest logger = ExtentTestManager.startTest(method.getName(),
				"Valid Login Scenario with existing username and password.");
		
		log.info("Starting Login Test");
		
		logger.log(LogStatus.INFO, "Starting Login Test");
		
		login = new LoginPage();
		home = new HomePage();
		String actualFullName = null;
		String heading = login.login(exist_Email, exist_Pwd, logger);
		/*
		 *****  The assertion here is to verify if teh heading is dispalyed as expected.
		 *The heading should be dispalyed as "MY ACCOUNT" */
		try {
			assertEquals(heading, "MY ACCOUNT", "Heading is not as expected");
		} catch (AssertionError e) {
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
		/*
		 * This assertion is to verify if the logged in user's name is dispalyed as mentioned in the data source(TestData.xlsx)
		 */
		try {
			actualFullName = home.verifyAccountName(fName);
			assertEquals(actualFullName, fName);
		} catch (AssertionError e) {
			log.info("Expected account name is ::" + fName + " and  displayed account name  is ::" + actualFullName);
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
//		try {
//			home.verifyWelcomeText();
//			log.info("Welcome message is displayed");
//		} catch (AssertionError e) {
//			log.info("Displayed text does not contains Welcome To your Account Text");
//			log.info(e);
//			logger.log(LogStatus.FAIL, e.toString());
//			throw e;
//		}
		
		/*
		 * This step is to verify if Welcome text is dispalyed as expected.
		 */
		
		try{
//			home.verifyWelcomeText();
			String welcomeText=home.getWelcomeText();
			Assert.assertTrue(welcomeText.contains("Welcome to your account."));
			log.info("Welcome text is  displayed as expected");
			logger.log(LogStatus.PASS, "Welcome text is displayed as expected");
			}catch (Exception e) {
				log.info("Welcome text is not displayed");
				logger.log(LogStatus.FAIL, "Welcome text is not displayed");
				log.info(e.toString());
				throw e;
			}
		/*
		 * This step is to verify if LogOut button is displayed
		 */
		try {
			home.verifyLogOutButtonisDisplayed();
		} catch (AssertionError e) {
			log.trace("Logout button is not displayed");
			log.trace(e.getStackTrace());
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
		/*
		 *****  The assertion here is to verify if my account page is correctly displayed. 
		 *The expected result is: The current url should have "?controller=my-account" text in it ***
		 */
		try {
			home.verifyMyAccountPageIsDisplayed();
		} catch (AssertionError e) {
			log.info("Current URL does not contain controller-my-accountt");
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}

	}

	 @Test(dataProvider="getTestData", invocationCount = 20)
	public void checkoutTest(Method method, String exist_Email, String exist_Pwd, String email, String pwd, String name,
			String surName, String fName, String comp, String addr1, String addr2, String City, String post, String Oth,
			String ph, String mob, String alias) throws Exception {

		ExtentTest logger = ExtentTestManager.startTest(method.getName(), "Order Checkout For existing customer");
		login = new LoginPage();
		home = new HomePage();
		order = new OrderConfirmationPage();
		checkOut = new CheckoutPage();
		logger.log(LogStatus.INFO, "Starting Checkout Test");
		log.info("Starting Checkout Test");
		try {
			assertEquals(login.login(exist_Email, exist_Pwd, logger), "MY ACCOUNT",
					"The expected title did not match with expected title..Expected title is MY ACCOUNT");
		} catch (AssertionError e) {
			log.info(e);
			throw e;
		}
		logger.log(LogStatus.INFO, "Clicking on Women section");
		home.selectWomenSection();
		logger.log(LogStatus.INFO, "Selecing the product");
		home.selectProduct();
		logger.log(LogStatus.INFO, "Adding product to the cart");
		home.addToCart();
		logger.log(LogStatus.INFO, "Proceeding for checkout");
		home.proceedToCheckout();
		logger.log(LogStatus.INFO, "Verifying orer details");
		checkOut.verifyOrderDetails();
		logger.log(LogStatus.INFO, "Verify Delivery address");
		checkOut.verifyDeliveryAddress();
		logger.log(LogStatus.INFO, "Accepting terms and conditions and proceeding");
		checkOut.acceptTnCAndProceed();
		logger.log(LogStatus.INFO, "Select carrier and proceed");
		checkOut.selectCarrier();
		logger.log(LogStatus.INFO, "Selecting payment option");
		checkOut.payByBankWire();
		String heading = checkOut.confirmOrder( );
		try {
			Assert.assertEquals(heading,"ORDER CONFIRMATION");
		} catch (AssertionError e) {
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
		// Verify order is confirmed
		logger.log(LogStatus.INFO, "Last step of order");
		try{
		order.verifyLastStepOfOrderisDisplayed();
		}catch(AssertionError e){
			log.info("Last Step of order has errors"+e.toString());
	          throw e;
	       }
		logger.log(LogStatus.INFO, "Order Confirmation Page");
		try{
		order.verifyOrderConfirmationPageIsDispalyed();
		}catch(AssertionError e){
	          log.info(e);
	          throw e;
	       }
		logger.log(LogStatus.INFO, "Confirmation Message");
		try{
		order.orderConfirmationMessageIsDispalyed();
		}catch(AssertionError e){
	          log.info(e);
	          throw e;
	       }

	}

}
