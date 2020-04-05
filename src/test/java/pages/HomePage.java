package pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.hellofresh.challenge.BaseTest;
import com.hellofresh.challenge.BrowserFactory;
import com.hellofresh.challenge.WebTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Utils;

public class HomePage extends BaseTest {

	By account = By.className("account");
	By logOutButton = By.className("logout");
	By heading = By.cssSelector("h1");
	By accountHolderName = By.className("account");
	By accountInfo = By.className("info-account");
	By womenSection = By.linkText("Women");
	By tshirt = By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li");
	By submit = By.name("Submit");
	By addToCart = By.xpath("//a[span[contains(text(), 'Add')]]");
	By checkOut = By.xpath("//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']");
	By cart_navigation = By.xpath("//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']");
	By processAddress = By.name("processAddress");
	By cgv = By.id("uniform-cgv");
	By carrier = By.name("processCarrier");
	By bankwire = By.className("bankwire");
	By confirmButton = By.xpath("//*[@id='cart_navigation']/button");
	By closeButton = By.xpath("//a[@title='Close']");

	Utils util = new Utils();
	Logger log = Logger.getLogger(HomePage.class);



	public String retrieveUserNameAfterSignIn() {
		try{
		String accountName = util.get_Element_Text(account);
		log.info("Logged in user is ::" + accountName);
		return accountName;
		}catch(Exception e){
			log.error("Error occured while retrieving username"+ e.toString());
			throw e;
		}
	}

	public void verifyMyAccountPageIsDisplayed() {
		
		assertTrue(getDriver().getCurrentUrl().contains("controller=my-account"));
		log.info("Current url contains controller=my-account");
		
	}

	public void verifyLogOutButtonisDisplayed() {
		util.verify_Element_displayed(logOutButton);
		log.info("Logout button is dispalyed");
	}

	public String verifyAccountName(String fullName) {
		String actualFullName = null;
		actualFullName = util.get_Element_Text(accountHolderName);
			log.info("Account name is displayed as :: " + actualFullName);
			return actualFullName;
//			assertEquals(fullName, actualFullName);
		
	}

	public boolean verifyWelcomeText() {
		boolean result=util.verify_Element_Text(accountInfo, "Welcome to yourr account.");
		return result;
	}

	public String getWelcomeText(){
		return util.get_Element_Text(accountInfo).trim();
	}
//	public void verifyUrlAfterLogin() {
//		try {
//			assertTrue(getDriver().getCurrentUrl().contains("controller=my-account"));
//			log.info("Current url contains controller=my-account");
//		} catch (AssertionError e) {
//			log.trace("The current URL is::" + getDriver().getCurrentUrl());
//			log.trace(e.getStackTrace());
//		}
//	}

	public void selectWomenSection()  {
		try{
		util.waitAndClick(womenSection);
		log.info("Selected women section");
		}catch(TimeoutException e){
			log.info("Women section is not found");
			log.error(e.getMessage());
			throw e;
		}


	}

	public void selectProduct() throws Exception {
		try{
		util.wait_explicit_till_element_Displayed(tshirt);
		log.info("Scrolling down to view the product");
		util.scrollToView(tshirt);
		log.info("Clicking on the product");
		util.click(tshirt);
		log.info("Opening the full view of the product");
		util.waitAndClick(tshirt);
		log.info("Selected the product");
		}catch(TimeoutException e){
			log.info("Could not select the product");
			log.error(e.getMessage());
			throw e;
		}

	}

	public void addToCart() throws InterruptedException {
		try {
			try{
			log.info("Clicking on Add To Cart button");
			util.waitAndClick(submit);
			util.waitForPageLoad();
		} catch (Exception e) {
			if (util.isElementDispalyed(closeButton)) {
				util.click(closeButton);
				log.info("Close button is displayed");
				util.waitForPageLoad();
				Thread.sleep(3000);
				util.waitAndClick(submit);
				
			}
		}
	}catch(TimeoutException e){
		log.info("Could not add the product to the cart");
		log.error(e.getMessage());
		throw e;
	}
		

	}
	public void proceedToCheckout(){
		try{
		log.info("Clicking on Proceed To Checkout button");
		util.waitAndClick(checkOut);
		}
		catch(TimeoutException e){
			log.info("Error occured during checkout");
			log.error(e.getMessage());
			throw e;
		}
	}

//	public void acceptTnCAndProceed() {
//		util.waitAndClick(cgv);
//		util.waitAndClick(carrier);
//		log.info("Selected the carrier");
//	}
//
//	public void payByBankWire() {
//		util.waitAndClick(bankwire);
//		log.info("Selected the payment method");
//
//	}

	public String confirmOrder() throws Exception {
		util.waitAndClick(confirmButton);
		util.wait_explicit_till_element_Displayed(heading);
		log.info("After order confirmation, dispalyed header is ::" + util.get_Element_Text(heading).toString());
		return util.get_Element_Text(heading).toString();

	}
}
