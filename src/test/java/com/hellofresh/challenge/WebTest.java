package com.hellofresh.challenge;

import static org.testng.Assert.assertEquals;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
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
	public Object[][] getTestData() {
		Utils util = new Utils();
		Object data[][] = util.getData("Test");
		return data;
	}

//	@Test(dataProvider = "getTestData")
	public void test(Method method, String loginMail, String loginPwd, String regEmail, String regPwd, String name,
			String surName, String fullName, String company, String address1, String address2, String city,
			String postCode, String other, String phone, String mobile, String alias) throws Exception {
		log.info("Executing test\n");

		log.info("\nlogin mail::" + loginMail + " \nloginpwd::" + loginPwd + " \nemail::" + regEmail + " \npwd::"
				+ regPwd + " \nNAme ::" + name + " \nsurname::" + surName + " \nfullname::" + fullName + " \ncompany ::" + company
				+ " \naddr1::" + address1 + " \naddr2::" + address2 + " \ncity::" + city + " \npostcode::" + postCode + " \nother::"
				+ other + " \nphone::" + phone + " \nmobile::" + mobile + " \nalias::" + alias);

	}

	/*
	 * This test case is for verifying the signin process for a new user. The
	 * data required for creating new user is passed externally from excel file.
	 * Execution logs can be found in application.log file and the report is
	 * placed in ExtentReports folder.
	 * 
	 */

	@Test(dataProvider = "getTestData")
	public void signInTest(Method method, String loginMail, String loginPwd, String regEmail, String regPwd, String name,
			String surName, String fullName, String company, String address1, String address2, String city,
			String postCode, String other, String phone, String mobile, String alias) throws Exception {
		ExtentTest logger = ExtentTestManager.startTest(method.getName(), "Create new User");
		signIn = new SignInPage();
		home = new HomePage();
		log.info("Starting SignIn Test");
		logger.log(LogStatus.INFO, "Starting SignIn Test");
		String heading = signIn.createAccount(regEmail, regPwd, name, surName,
				company, address1, address2, city, postCode,
				other, phone, mobile, alias, logger);
		String loggedInUserName = home.retrieveUserNameAfterSignIn();
//		String newAccountName = data.get("name") + " " + data.get("sName");
		String newAccountName=name+ " "+surName;

		/*
		 ***** The assertion here is to verify if the heading is dispalyed as
		 * expected. The heading should be dispalyed as "MY ACCOUNT"
		 */

		try {
			assertEquals(heading, "MY ACCOUNT");
			logger.log(LogStatus.PASS, "The heading is correctly displayed");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL, "The heading is NOT correctly displayed");
			throw e;
		}

		/*
		 ***** The assertion here is to verify if account name for newly created
		 * account is dispalyed as expected(as entered during registration(First
		 * name + Surname)) ***
		 */
		try {
			Assert.assertEquals(loggedInUserName, newAccountName);
			logger.log(LogStatus.PASS, "New ccount name is displayed correctly");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL, "New account name and expected account name did not match");
			throw e;
		}

		/*
		 * This step is to verify if Welcome text is dispalyed as expected.
		 */

		try {
			String welcomeText = home.getWelcomeText();
			Assert.assertTrue(welcomeText.contains("Welcome to your account."));
			log.info("Welcome text is  displayed as expected");
			logger.log(LogStatus.PASS, "Welcome text is displayed as expected");
		} catch (AssertionError e) {
			log.info("Welcome text is not displayed as expected");
			logger.log(LogStatus.FAIL, "Welcome text is not displayed as expected");
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
		 ***** The assertion here is to verify if my account page is correctly
		 * displayed. The expected result is: The current url should have
		 * "?controller=my-account" text in it ***
		 */
		try {
			home.verifyMyAccountPageIsDisplayed();
			logger.log(LogStatus.PASS, "My account page(?controller=my-account) is opened ");
		} catch (AssertionError e) {
			log.info(e.toString());
			logger.log(LogStatus.FAIL,
					"My account page(?controller=my-account) is NOT correctly opened" + e.toString());
			throw e;
		}
	}

	 @Test
	public void logInTest(Method method) throws Exception {
		ExtentTest logger = ExtentTestManager.startTest(method.getName(),
				"Valid Login Scenario with existing username and password.");

		log.info("Starting Login Test");

		logger.log(LogStatus.INFO, "Starting Login Test");

		login = new LoginPage();
		home = new HomePage();
		// String actualFullName = null;
		String heading = login.login(data.get("exist_Email"), data.get("exist_Pwd"), logger);
		/*
		 ***** The assertion here is to verify if the heading is dispalyed as
		 * expected. The heading should be dispalyed as "MY ACCOUNT"
		 */
		try {
			assertEquals(heading, "MY ACCOUNT", "Heading is not as expected");
		} catch (AssertionError e) {
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
		/*
		 * This assertion is to verify if the logged in user's name is dispalyed
		 * as mentioned in the data source(TestData.xlsx)
		 */
		try {
			String actualFullName = home.verifyAccountName(data.get("fName"));
			assertEquals(actualFullName, data.get("fName"));
		} catch (AssertionError e) {
			log.info("Expected account name is and actual account name did not match");
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}

		/*
		 * This step is to verify if Welcome text is displayed as expected.
		 */

		try {
			String welcomeText = home.getWelcomeText();
			Assert.assertTrue(welcomeText.contains("Welcome to your account."));
			log.info("Welcome text is  displayed as expected");
			logger.log(LogStatus.PASS, "Welcome text is displayed as expected");
		} catch (AssertionError e) {
			log.info("Welcome text is not displayed as expected");
			logger.log(LogStatus.FAIL, "Welcome text is not displayed as expected");
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
		 ***** The assertion here is to verify if my account page is correctly
		 * displayed. The expected result is: The current url should have
		 * "?controller=my-account" text in it ***
		 */
		try {
			home.verifyMyAccountPageIsDisplayed();
		} catch (AssertionError e) {
			log.info("Current URL does not contain controller-my-accountt");
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}

	}

	/*
	 * This test is to checkout and place an order for an existing customer
	 */
	 @Test
	public void checkoutTest(Method method) throws Exception {

		ExtentTest logger = ExtentTestManager.startTest(method.getName(), "Order Checkout For existing customer");
		login = new LoginPage();
		home = new HomePage();
		order = new OrderConfirmationPage();
		checkOut = new CheckoutPage();
		logger.log(LogStatus.INFO, "Starting Checkout Test");
		log.info("Starting Checkout Test");
		/*
		 * This assertion is to make sure the title is displayed correctly
		 */
		try {
			assertEquals(login.login(data.get("exist_Email"), data.get("exist_Pwd"), logger), "MY ACCOUNT",
					"The expected title did not match with expected title..Expected title is MY ACCOUNT");
		} catch (AssertionError e) {
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
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
		String heading = checkOut.confirmOrder();
		/*
		 * This assertion is to verify the heading is dspalyed as ORDER
		 * CONFIRMATION
		 */
		try {
			Assert.assertEquals(heading, "ORDER CONFIRMATION");
		} catch (AssertionError e) {
			log.info(e);
			logger.log(LogStatus.FAIL, e.toString());
			throw e;
		}
		/*
		 * This step is to verify Current page is the last step of ordering
		 */
		logger.log(LogStatus.INFO, "Last step of order");
		try {
			order.verifyLastStepOfOrderisDisplayed();
		} catch (AssertionError e) {
			log.info("Last Step of order has errors" + e.toString());
			throw e;
		}
		log.info("Verifying details in order confirmation page");
		logger.log(LogStatus.INFO, "Order Confirmation Page");
		/*
		 * This step is to verify Order confirmation page is displayed
		 */
		try {
			Assert.assertTrue(order.verifyOrderConfirmationPageIsDispalyed().contains("controller=order-confirmation"),
					"Confirmation page is not correctly displayed");
		} catch (AssertionError e) {
			log.info("Confirmation page URL not contain controller=order-confirmation");
			logger.log(LogStatus.ERROR, "Confirmation page URL not contain controller=order-confirmation");
			throw e;
		}
		/*
		 * This step is to verify that order confirmation message is displayed
		 */
		log.info("Verifying Confirmation Message");
		logger.log(LogStatus.INFO, "Verifying Confirmation Message");
		try {
			String message = order.orderConfirmationMessageIsDispalyed();
			Assert.assertTrue(message.contains("Your order on My Store is complete."),
					"Actual mssg " + message + "does not contain Your order on My Store is completee.");
		} catch (AssertionError e) {
			log.info("Dispalyed message does not contain Your order on My Store is completee.");
			logger.log(LogStatus.ERROR, e.toString());
			throw e;
		}

	}

}
