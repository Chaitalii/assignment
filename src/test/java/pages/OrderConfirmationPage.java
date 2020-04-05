package pages;


import org.openqa.selenium.By;
import org.testng.Assert;
import com.hellofresh.challenge.BaseTest;
import utilities.Utils;
import org.apache.log4j.Logger;


public class OrderConfirmationPage extends BaseTest{
	
	By last_Four= By.xpath("//li[@class='step_done step_done_last four']");
	By current_Last= By.xpath("//li[@id='step_end' and @class='step_current last']");
	By orderConfirmationMessage=(By.xpath("//*[@class='cheque-indent']/strong"));
	By confirmButton = By.xpath("//*[@id='cart_navigation']/button");
	Utils util= new Utils();
	
	Logger log = Logger.getLogger(OrderConfirmationPage.class);
	
	public void verifyOrderConfirmationPageIsDispalyed() {
		
		Assert.assertTrue(getDriver().getCurrentUrl().contains("controller=order-confirmation"));
		log.info("Order Confirmation Page is displayed");
		
	}
	public void orderConfirmationMessageIsDispalyed(){
	
	Assert.assertTrue(util.get_Element_Text(orderConfirmationMessage).contains("Your order on My Store is complete."));
	log.info("Order confirmation message is displayed");
		
	}
	public void verifyLastStepOfOrderisDisplayed(){
		log.info("Verifying Last step of order is displayed");
		util.verify_Element_displayed(last_Four);
		util.verify_Element_displayed(current_Last);
		
	}

}
