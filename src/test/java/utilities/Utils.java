package utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNGException;
import com.hellofresh.challenge.WebTest;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Utils extends WebTest {
	Logger log = Logger.getLogger(Utils.class);

	static Xls_Reader reader;

	public void verifyTitle(String title) {
		boolean isTitleCorrectlyDisplayed = true;
		if (getDriver().getCurrentUrl().contains(title)) {
			log.info("Title is correctly displayed");
		} else {
			log.info("Title is NOT correctly displayed..The dispalyed title is ::" + getDriver().getCurrentUrl()
					+ "	and it does not contain ::" + title);
			isTitleCorrectlyDisplayed = false;
		}
		assertTrue(isTitleCorrectlyDisplayed);
	}

	public void verify_Element_Not_displayed(WebDriver driver, By by) {
		wait_explicit_till_element_Displayed(driver, by);
		boolean result = getDriver().findElement(by).isDisplayed();
		Assert.assertFalse(result, by + "	Element not displayed as expected");

	}

	public void verify_Element_displayed(By by) {
		wait_explicit_till_element_Displayed(getDriver(), by);
		boolean result = getDriver().findElement(by).isDisplayed();
		Assert.assertEquals(result, true, by + "	Element not displayed.");

	}

	public boolean isElementDispalyed(By by) {
		wait_explicit_till_element_Displayed(getDriver(), by);
		return getDriver().findElement(by).isDisplayed();

	}

	public String get_Element_Text(By by) {
		try {
			return getDriver().findElement(by).getText();
		} catch (Exception e) {
			log.info(e.toString());
			return null;
		}

	}

	public boolean verify_Element_Text(By by, String text) {
		String strActual = getDriver().findElement(by).getText().trim();
		boolean res = false;
		if (strActual.contains(text)) {
			res = true;
		}

		return res;
	}

	public String get_Attribute_Value(By by, String strAttribute) {
		String result = getDriver().findElement(by).getAttribute(strAttribute);
		return result;
	}

	public void wait_explicit_till_element_Displayed(WebDriver driver, By by) {

		WebDriverWait waitnew = new WebDriverWait(driver, 20);
		waitnew.until(ExpectedConditions.visibilityOfElementLocated(by));

	}

	public void waitForPageLoad() {
		getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			log.info("Waiting for page to be loaded fully");
			return;
		}
	}

	// *******************************************************//
	public void sendText(By by, String text) {

		WebElement objInput = getDriver().findElement(by);
		objInput.sendKeys(text);

	}

	public void wait_explicit_till_element_Clickable(WebElement objElement) {

		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.elementToBeClickable(objElement));

	}

	public void selectByValue(By by, String value) {
		Select select = new Select(getDriver().findElement(by));
		select.selectByValue(value);

	}

	public void selectByVisibleText(By by, String text) {

		Select select = new Select(getDriver().findElement(by));
		select.selectByVisibleText(text);
	}

	public void refreshPage() {
		getDriver().navigate().refresh();
		waitForPageLoad();
	}

	public void wait_explicit_till_element_Displayed(By by) {

		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void click(By by) {
		getDriver().findElement(by).click();

	}

	public void scrollToView(By by) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(by));
	}

	public void enterText(By by, String text) {

		WebElement objInput = getDriver().findElement(by);
		objInput.sendKeys(text);
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

	}

	public void verifyTitleAfterLogin(String actual, String Expected) {
		try {
			Assert.assertEquals(Expected, actual);
			log.trace("Title is as expected");
		} catch (AssertionError e) {
			log.trace("Title is Not as expected");
			log.trace(e.getStackTrace());
		}
	}

	// public void wait_explicit_till_element_Clickable(By by) {
	// try{
	// WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
	// waitnew.until(ExpectedConditions.elementToBeClickable(by));
	// }catch(NoSuchElementException e){
	// log.info(e);
	// throw e;
	// }
	//
	//
	// }

	public void waitAndClick(By by) {

		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.visibilityOfElementLocated(by));
		getDriver().findElement(by).click();

	}

	public ArrayList<Object[]> getDataFromExcel() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			String path = System.getProperty("user.dir") + "\\TestData.xlsx";
			reader = new Xls_Reader(path);
		} catch (Exception e) {
			log.info(e.getStackTrace());

		}

		for (int rowNum = 2; rowNum <= reader.getRowCount("Test"); rowNum++) {
			String existingUserEmail = reader.getCellData("Test", "ExistingUserEmail", rowNum);
			System.out.println("mail id is:: " + existingUserEmail);
			String existingUserPassword = reader.getCellData("Test", "ExistingUserPassword", rowNum);
			String email = reader.getCellData("Test", "Email", rowNum);
			String pwd = reader.getCellData("Test", "RegistrationPwd", rowNum);
			String name = reader.getCellData("Test", "Name", rowNum);
			String surname = reader.getCellData("Test", "SurName", rowNum);
			String fullName = reader.getCellData("Test", "FullName", rowNum);
			String company = reader.getCellData("Test", "Company", rowNum);
			String address1 = reader.getCellData("Test", "Address1", rowNum);
			String address2 = reader.getCellData("Test", "Address2", rowNum);
			String city = reader.getCellData("Test", "City", rowNum);
			String postcode = reader.getCellData("Test", "Postcode", rowNum);
			String other = reader.getCellData("Test", "Other", rowNum);
			String phone = reader.getCellData("Test", "Phone", rowNum);
			String mobile = reader.getCellData("Test", "Mobile", rowNum);
			String alias = reader.getCellData("Test", "Alias", rowNum);
			Object ob[] = { existingUserEmail, existingUserPassword, email, pwd, name, surname, fullName, company,
					address1, address2, city, postcode, other, phone, mobile, alias };
			myData.add(ob);

		}
		return myData;
	}

	public void fullScreenCapture(String methodName) throws IOException {
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(getDriver());
		// Screenshot screenshot = new
		// AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.75f),
		// 1000)).takeScreenshot(getDriver());
		ImageIO.write(screenshot.getImage(), "PNG",
				new File(System.getProperty("user.dir") + "/screenshots/" + methodName + ".png"));

	}
}
