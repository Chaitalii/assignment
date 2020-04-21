package com.hellofresh.challenge;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebEndpoint;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class BrowserFactory {
	private static BrowserFactory instance = null;
	protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	/*
	 * This class is the browser factory in this framework This class maintains
	 * the thread safe drivers required for parallel execution and sets the
	 * drivers for browsers for mac and windows.
	 * 
	 */

	public BrowserFactory() {

	}

	public static BrowserFactory getInstance() {
		if (instance == null) {
			instance = new BrowserFactory();
		}
		return instance;
	}

	public final void setDriver(String browser) throws Exception {
		DesiredCapabilities caps = null;
		String browserName;
		boolean docker=true;
		String host=null;

		if (System.getProperty("BROWSER") != null && System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
			caps = DesiredCapabilities.firefox();
			browserName = "firefox";
		} else {
			caps = DesiredCapabilities.chrome();
			browserName = "chrome";
		}
		if(docker){
		if (System.getProperty("HUB_HOST") != null) {
			 host = System.getProperty("HUB_HOST");
			
		}else{
			host="localhost";
		}
			host = "http://" + host + ":4444/wd/hub";
			System.out.println("The complete url is ::" + host);
			try {
				webDriver.set(new RemoteWebDriver(new URL(host), caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} 
		

		else {
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				switch (browser) {

				case "chrome":
					System.setProperty("webdriver.chrome.driver", "src/test/resources/windows/chromedriver.exe");
					ChromeOptions chOptions = new ChromeOptions();
					chOptions.addArguments("--start-maximized");
					webDriver.set(new ChromeDriver(chOptions));
					break;

				case "firefox":
					System.setProperty("webdriver.gecko.driver", "src/test/resources/windows/geckodriver.exe");
					FirefoxOptions options = new FirefoxOptions();
					options.addPreference("browser.startup.homepage", "http://automationpractice.com/index.php");
					options.setAcceptInsecureCerts(true);
					caps = DesiredCapabilities.firefox();
					options.merge(caps);
					webDriver.set(new FirefoxDriver(options));
					break;

				}
			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				switch (browser) {
				case "chrome":
					System.setProperty("webdriver.chrome.driver", "src/test/resources/mac/chromedriver");
					ChromeOptions chOptions = new ChromeOptions();
					chOptions.addArguments("--start-maximized");
					webDriver.set(new ChromeDriver(chOptions));
					break;
				case "safari":
					DesiredCapabilities desiredcapabilities = DesiredCapabilities.safari();
					SafariOptions safarioptions = new SafariOptions();
					safarioptions.setUseTechnologyPreview(true);
					desiredcapabilities.setCapability(SafariOptions.CAPABILITY, safarioptions);
					webDriver.set(new SafariDriver());
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", "src/test/resources/mac/geckodriver");
					FirefoxOptions options = new FirefoxOptions();
					options.addArguments("--start-maximized");
					webDriver.set(new FirefoxDriver(options));
					break;
				}
			}
		}
	}

	public WebDriver getDriver() {
		return webDriver.get();
	}

	public void terminate() {
		// Remove the ThreadLocalMap element
		webDriver.remove();
	}
}