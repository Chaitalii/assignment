package utilities;

import org.apache.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Utils extends WebTest {
	Logger log = Logger.getLogger(Utils.class);

	static Xls_Reader reader;
	static Workbook book;
	static Sheet sheet;

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

	public void click(By by) {
		getDriver().findElement(by).click();

	}

	public String get_Element_Text(By by) {
		try {
			return getDriver().findElement(by).getText();
		} catch (Exception e) {
			log.info(e.toString());
			return null;
		}

	}

	void checkText(By selector, String text, int timeout) {
		WebDriverWait waitList = new WebDriverWait(getDriver(), timeout);
		waitList.until(ExpectedConditions.elementToBeClickable(selector));
		WebElement fetchText = getDriver().findElement(selector);
		fetchText.getText();
		Assert.assertTrue(fetchText.getText().contains(text));
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

	public void wait_explicit_till_element_Clickable(WebElement objElement) {
		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.elementToBeClickable(objElement));

	}

	public void wait_explicit_till_element_Clickable(By by) {
		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(by)));

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

	public void scrollToView(By by) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(by));
	}

	public void enterText(By by, String text) {
		try {
			if (getDriver().findElement(by).getAttribute("value").contains(text)) {
				// log.info(by + "has value as ::" + text);
			} else {
				click(by);
				clearTextBox(by);
				WebElement objInput = getDriver().findElement(by);
				objInput.sendKeys(text);
				// log.info("Entering value for " + by + " as " + text);
			}
		} catch (Exception e) {
			log.error(e.toString() + " Error occured for element::+" + by + "and text value::" + text);
			throw e;

		}

	}

	public void clearTextBox(By by) {
		getDriver().findElement(by).clear();
	}

	public void waitAndClick(By by) {

		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.visibilityOfElementLocated(by));
		getDriver().findElement(by).click();

	}

	/*
	 * This method is responsible for fetching data from excel and storing them
	 * into a map.
	 * 
	 */
	public HashMap<String, String> getDataFromExcel() {
		ArrayList<String> myData = new ArrayList<String>();
		HashMap<String, String> data = new HashMap<>();
		String path = null;
		try {
			if (System.getProperty("os.name").toLowerCase().contains("win")) {

				path = System.getProperty("user.dir") + "\\TestData.xlsx";
			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {

				path = System.getProperty("user.dir") + "/TestData.xlsx";
			}
			reader = new Xls_Reader(path);
		} catch (Exception e) {
			log.info(e.getStackTrace());

		}

		for (int rowNum = 2; rowNum <= reader.getRowCount("Test"); rowNum++) {
			String existingUserEmail = reader.getCellData("Test", "ExistingUserEmail", rowNum);
			data.put("exist_Email", existingUserEmail);
			String existingUserPassword = reader.getCellData("Test", "ExistingUserPassword", rowNum);
			data.put("exist_Pwd", existingUserPassword);
			String email = reader.getCellData("Test", "RegistrationEmail", rowNum);
			data.put("email", email);
			String pwd = reader.getCellData("Test", "RegistrationPwd", rowNum);
			data.put("pwd", pwd);
			String name = reader.getCellData("Test", "Name", rowNum);
			data.put("name", name);
			String surname = reader.getCellData("Test", "SurName", rowNum);
			data.put("sName", surname);
			String fullName = reader.getCellData("Test", "FullName", rowNum);
			data.put("fName", fullName);
			String company = reader.getCellData("Test", "CompanyName", rowNum);
			data.put("comp", company);
			String address1 = reader.getCellData("Test", "Address1", rowNum);
			data.put("addr1", address1);
			String address2 = reader.getCellData("Test", "Address2", rowNum);
			data.put("addr2", address2);
			String city = reader.getCellData("Test", "City", rowNum);
			data.put("City", city);
			String postcode = reader.getCellData("Test", "Postcode", rowNum);
			data.put("post", postcode);
			String other = reader.getCellData("Test", "Other", rowNum);
			data.put("oth", other);
			String phone = reader.getCellData("Test", "Phone", rowNum);
			data.put("ph", phone);
			String mobile = reader.getCellData("Test", "Mobile", rowNum);
			data.put("mob", mobile);
			String alias = reader.getCellData("Test", "Alias", rowNum);
			data.put("alias", alias);

		}
		return data;
	}

	/*
	 * This method captures fullscreen screenshot of the browser with support
	 * from Ashot library.
	 */
	public void fullScreenCapture(String methodName) throws IOException {
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(getDriver());
		ImageIO.write(screenshot.getImage(), "PNG",
				new File(System.getProperty("user.dir") + "/screenshots/" + methodName + ".png"));

	}

	public void wait_explicit_till_element_invisible(By by) {
		WebDriverWait waitnew = new WebDriverWait(getDriver(), 20);
		waitnew.until(ExpectedConditions.invisibilityOf(getDriver().findElement(by)));

	}

	public Object[][] getData(String sheetName) {
		FileInputStream file = null;

		try {

			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				file = new FileInputStream(System.getProperty("user.dir") + "\\TestData.xlsx");

			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {

				file = new FileInputStream(System.getProperty("user.dir") + "/TestData.xlsx");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.print(data[i][k]);
				// System.out.println();
			}
		}
		return data;

	}

}
