package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//Tarayıcıyı yükleme ve tarayıcı ile ilgili işlemler yapılıyor.
//Default olarak chrome geliyor.
// Tarayıcı değiştirmek için run- edit configutaion içerisinden env adına env dosyasında tanımlamama tarayıcı ismini vermek yeterli olacaktır.


public class DriverFactory {
    public static RemoteWebDriver getDriver() {

        String browser = System.getenv("BROWSER");
        browser = (browser == null) ? "CHROME": browser;

        switch (browser) {
            case "IE":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "CHROME":
            default:
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                if ("Y".equalsIgnoreCase(System.getenv("HEADLESS"))) {
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    options.addArguments("disable-notifications");
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("disable-infobars");
                    options.addArguments("allow-running-insecure-content");
                }

                return new ChromeDriver(options);
        }
    }
}
