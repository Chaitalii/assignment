package pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import com.hellofresh.challenge.BaseTest;
import utilities.Utils;
import org.apache.log4j.Logger;

/*
 * This page has webelements and operations from order confirmation page
 */
public class OrderConfirmationPage extends BaseTest {

	By last_Four = By.xpath("//li[@class='step_done step_done_last four']");
	By current_Last = By.xpath("//li[@id='step_end' and @class='step_current last']");
	By orderConfirmationMessage = (By.xpath("//*[@class='cheque-indent']/strong"));
	By confirmButton = By.xpath("//*[@id='cart_navigation']/button");
	Utils util = new Utils();

	Logger log = Logger.getLogger(OrderConfirmationPage.class);

	public String verifyOrderConfirmationPageIsDispalyed() {
		return getDriver().getCurrentUrl();

	}

	public String orderConfirmationMessageIsDispalyed() {

		return util.get_Element_Text(orderConfirmationMessage);

	}

	public void verifyLastStepOfOrderisDisplayed() {
		log.info("Verifying Last step of order is displayed");
		try {
			util.verify_Element_displayed(last_Four);
			util.verify_Element_displayed(current_Last);
		} catch (Exception e) {
			log.error(e.toString());
		}

	}

}
