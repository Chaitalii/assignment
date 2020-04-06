package pages;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import com.hellofresh.challenge.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import utilities.Utils;

/*
 * This class has webelements and operations from Login page
 */

public class LoginPage extends BaseTest {

	Logger log= Logger.getLogger(LoginPage.class);
	By loginButton= By.className("login");
	By email= By.id("email");
	By password=By.id("passwd");
	By submitLogin=By.id("SubmitLogin");
	By heading= By.cssSelector("h1");
	Utils util ;

	
	
	public String login(String existingUserEmail, String existingUserPassword, ExtentTest logger) throws Exception {
	
		util= new Utils();
		util.waitForPageLoad();
		try{
		
		logger.log(LogStatus.PASS,  "Waiting for Login button to be available");
		log.info("Waiting for Login button to be available");
		util.wait_explicit_till_element_Displayed(loginButton);
		util.click(loginButton);
		logger.log(LogStatus.PASS,  "Clicked Login button");
		log.info("Clicked Login button");
		util.wait_explicit_till_element_Displayed(email);
		log.info("Entering mail id as ::"+existingUserEmail);
		util.enterText(email, existingUserEmail);
		logger.log(LogStatus.PASS,  "Entered email id");;
		util.wait_explicit_till_element_Displayed(password);
		if(existingUserPassword==null){
			log.info("The password seems to be blank.");
//			Thread.sleep(3000);
		}
		
		log.info("Entering pwd as ::"+existingUserPassword);
		logger.log(LogStatus.PASS,  "Entering password");
		util.enterText(password, existingUserPassword);
		util.wait_explicit_till_element_Displayed(submitLogin);
		util.click(submitLogin);
		util.waitForPageLoad(); 
		logger.log(LogStatus.PASS,  "Clicked submit button");
		log.info("Clicked submit button");
		util.wait_explicit_till_element_Displayed(heading);
		util.waitForPageLoad();
		return util.get_Element_Text(heading).toString();
		}catch(Exception e){
			log.error(e.toString());
			logger.log(LogStatus.FAIL,  e.toString());
			throw e;
		}

	}

	
	
}
