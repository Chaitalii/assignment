package utilities.ExtentReports;

import com.hellofresh.challenge.WebTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Utils;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/*
 * Our test class listens to this listener class.
 * This class decides the actions to be taken based on the etst execution result(Success/Failure/Skip) 
 */
public class TestListener extends WebTest implements ITestListener {

	Utils util = new Utils();

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {

		iTestContext.setAttribute("WebDriver", this.getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {

		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {

		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	// @Override
	public void onTestFailure(ITestResult iTestResult) {

		// Get driver from BaseTest and assign to local webDriver variable.
		Object testClass = iTestResult.getInstance();
		WebDriver webDriver = ((WebTest) testClass).getDriver();

		// Take base64Screenshot screenshot.
		String base64Screenshot = "data:image/png;base64,"
				+ ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

		// ExtentReports log and screenshot operations for failed tests.
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
				ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
//		try {
//			util.fullScreenCapture(iTestResult.getMethod().getMethodName());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			util.fullScreenCapture(iTestResult.getMethod().getMethodName());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		// ExtentReports log operation for skipped tests.
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

}
