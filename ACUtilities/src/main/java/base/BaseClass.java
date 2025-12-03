package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	 public static WebDriver getDriver() {
	        return driver;
	 }
    protected WebDriverWait wait;
    protected ConfigReader configReader;
    private static final Logger LOGGER = Logger.getLogger(BaseClass.class.getName());

    @BeforeTest
    public void setUp() {
        // Load properties using ConfigReader
        configReader = new ConfigReader();
        String browser = configReader.getProperty("browser");
        String url = configReader.getProperty("url");
        ChromeOptions options = new ChromeOptions();
        
        String username = configReader.getUsername();
        String password = configReader.getPassword();
        
        int implicitWait = configReader.getIntProperty("");
        int explicitWait = configReader.getIntProperty("explicitWait");

        // Initialize WebDriver based on the browser specified in config
        switch (browser.toLowerCase()) {
            case "chrome":           	
                WebDriverManager.chromedriver().setup();
                options.setAcceptInsecureCerts(true);
                driver = new ChromeDriver(options);
                
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

       
      
        
       

		//Using the accept insecure cert method with true as parameter to accept the untrusted certificate
       
				
		//Creating instance of Chrome driver by passing reference of ChromeOptions object
		 //driver = new ChromeDriver(options);
		 // Maximize window
        driver.manage().window().maximize();
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        // Set explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));      
        driver.get(url);
        LOGGER.info("Browser " + browser + " launched and navigated to " + url);
        
        driver.findElement(By.xpath("//*[@id=\"login_link_top\"]")).click();
        driver.findElement(By.id("Bugzilla_login_top")).sendKeys(username);
        driver.findElement(By.id("Bugzilla_password_top")).sendKeys(password);

//        //for directly send the values
//        driver.findElement(By.id("Bugzilla_login_top")).sendKeys("surendrasgangwar");
//      driver.findElement(By.id("Bugzilla_password_top")).sendKeys("surendragang123$#");

        // Click the login button
        driver.findElement(By.xpath("//*[@id=\"log_in_top\"]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("enter_bug"))).click();
        //driver.findElement(By.id("enter_bug")).click();
      
        //driver.findElement(By.xpath("//*[@id=\"choose_product\"]/tbody/tr[6]/th/a")).click();
        driver.findElement(By.xpath("//*[@id=\"choose_product\"]/tbody/tr[13]/th/a")).click();
        
        
      
    }

    // Method to take screenshot on test failure
    public void captureScreenshot(String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("screenshots/" + testName + ".png"));
            LOGGER.info("Screenshot taken for test: " + testName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the current page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Tear down method to quit the browser
    @AfterTest
    public void tearDown(ITestResult result) {
        // If the test fails, take a screenshot
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
        LOGGER.info("Browser closed successfully.");
    }
}
