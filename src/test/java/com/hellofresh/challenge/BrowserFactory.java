package com.hellofresh.challenge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class BrowserFactory {
	private static BrowserFactory instance = null;
	protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

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
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			switch (browser) {

			case "chrome":
				System.setProperty("webdriver.chrome.driver", "src/test/resources/New folder/chromedriver.exe");
				ChromeOptions chOptions = new ChromeOptions();
		
    chOptions.addArguments("enable-automation"); 
    chOptions.addArguments("--no-sandbox"); 
    chOptions.addArguments("--disable-infobars"); 
    chOptions.addArguments("--disable-dev-shm-usage"); 
    chOptions.addArguments("--disable-browser-side-navigation"); 
    chOptions.addArguments("--disable-gpu");
				chOptions.addArguments("--start-maximized");
				webDriver.set(new ChromeDriver(chOptions));
				break;

			case "firefox":
				System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.startup.homepage", "http://automationpractice.com/index.php");
				options.setAcceptInsecureCerts(true);
				caps = DesiredCapabilities.firefox();
				// caps.setCapability([capabilityName], [value]);
				options.merge(caps);
				webDriver.set(new FirefoxDriver(options));
				break;

			}
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			switch (browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
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