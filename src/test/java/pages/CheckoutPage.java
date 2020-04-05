package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.hellofresh.challenge.BaseTest;

import utilities.Utils;

public class CheckoutPage extends BaseTest {

	By women = By.linkText("Women");
	By tshirt = By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li");
	By submit = By.name("Submit");
	By checkOut = By.xpath("//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']");
	By address = By.name("processAddress");
	By cgv = By.id("uniform-cgv");
	By carrier = By.name("processCarrier");
	By bankwire = By.className("bankwire");
	By nav = By.xpath("//*[@id='cart_navigation']/button");
	By heading = By.cssSelector("h1");
	By cart_navigation = By.xpath("//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']");
	By processAddress = By.name("processAddress");
	By confirmButton = By.xpath("//*[@id='cart_navigation']/button");

	Logger log = Logger.getLogger(CheckoutPage.class);

	Utils util = new Utils();

	public void verifyOrderDetails() throws Exception {
		try {
			log.info("Verifying the details of the order");
			util.waitAndClick(cart_navigation);
		} catch (TimeoutException e) {
			log.info("Order Summary details could not be found");
			log.error(e.getMessage());
			throw e;
		}
	}

	public void verifyDeliveryAddress() {
		try {
			log.info("Clicking on Proceed after verifying delivery and billing addresses");
			util.waitAndClick(processAddress);
		} catch (TimeoutException e) {
			log.info("Could not click on proceed after address verification");
			log.error(e.getMessage());
			throw e;
		}
	}

	public void acceptTnCAndProceed() throws Exception {
		try {
			util.waitAndClick(cgv);
			log.info("Accepted terms and conditions");
		} catch (TimeoutException e) {
			log.info("Could not accept tnc");
			log.error(e.getMessage());
			throw e;
		}

	}

	public void selectCarrier() {
		util.waitAndClick(carrier);
		log.info("Clicking on Proceed after selecting my carrier");
	}

	public void payByBankWire() {
		try {
			util.waitAndClick(bankwire);
			log.info("Selected the payment method");
		} catch (Exception e) {
			log.info(e);

		}

	}

	public String confirmOrder() {
		try {
			util.waitAndClick(confirmButton);
			util.wait_explicit_till_element_Displayed(heading);
			log.info("After order confirmation, dispalyed header is ::" + util.get_Element_Text(heading).toString());
			return util.get_Element_Text(heading).toString();
		} catch (Exception e) {
			log.error("An error occurred", e.fillInStackTrace());
			return e.toString();

		}

	}

}
