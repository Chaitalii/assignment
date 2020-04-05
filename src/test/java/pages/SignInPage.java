package pages;

import java.util.Date;
import org.openqa.selenium.By;
import com.hellofresh.challenge.BrowserFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Utils;

public class SignInPage extends BrowserFactory {
	
	By loginButton= By.className("login");
	By registrationEmail = By.id("email_create");
	By submitCreate = By.id("SubmitCreate");
	By gender = By.id("id_gender2");
	By firstName = By.id("customer_firstname");
	By LastName = By.id("customer_lastname");
	By password = By.id("passwd");
	By days = By.id("days");
	By months = By.id("months");
	By years = By.id("years");
	By company = By.id("company");
	By address1 = By.id("address1");
	By address2 = By.id("address2");
	By city = By.id("city");
	By state = By.id("id_state");
	By postcode = By.id("postcode");
	By other = By.id("other");
	By phone = By.id("phone");
	By phone_Mobile = By.id("phone_mobile");
	By alias = By.id("alias");
	By submitAccount = By.id("submitAccount");
	By heading = By.cssSelector("h1");
	String timestamp = String.valueOf(new Date().getTime());
	By female= By.xpath("//input[contains(@id, 'gender2')]");
	
	
	Utils util;

	public String createAccount(String email, String pwd,String fName, String LName,String comp, String adr1, String addr2, String City, String post,String ot, String ph, String mob, String al, ExtentTest logger) throws Exception {
		email=email+  timestamp + "@hf" + timestamp.substring(7) + ".com";
		util = new Utils();
		util.waitForPageLoad();
		util.wait_explicit_till_element_Displayed(loginButton);
		logger.log(LogStatus.PASS,  "Clicking on Login Button");
		util.click(loginButton);
		util.wait_explicit_till_element_Displayed(registrationEmail);
		
		util.enterText(registrationEmail, email);
		logger.log(LogStatus.PASS,  "Entered email id for registrstion");
		
		util.wait_explicit_till_element_Displayed(submitCreate);
		
		util.click(submitCreate);
		util.waitForPageLoad();
		logger.log(LogStatus.PASS,  "Registration is done");
		logger.log(LogStatus.PASS,  "Creating account(Fill up details)");
		try{
		
		util.wait_explicit_till_element_Displayed(firstName);
		try{
			util.waitAndClick(female);
		}catch(Exception e){
			
			util.waitAndClick(gender);
		}
		util.enterText(firstName, fName);
		util.enterText(LastName, LName);
		util.enterText(password, pwd);
		util.selectByValue(days, "1");
		util.selectByValue(months, "1");
		util.selectByValue(years, "2000");
		util.enterText(company, comp);
		util.enterText(address1, adr1);
		util.enterText(address2, addr2);
		util.scrollToView(postcode);
		util.enterText(city, City);
		util.selectByVisibleText(state, "Colorado");
		util.enterText(postcode, post);
		util.enterText(other,ot);
		util.enterText(phone, ph);
		util.enterText(phone_Mobile, mob);
		util.enterText(alias, al);
//		Thread.sleep(5000);
		util.click(submitAccount);
		util.waitForPageLoad();
		util.wait_explicit_till_element_Displayed(heading);
		return util.get_Element_Text(heading).toString();
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
		 

	}


}
