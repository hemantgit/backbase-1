package utility;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*
 * This class was created to reuse code and keep the tests as clean as possible.
 */

public class Utility {

	private static final String MAIN_PAGE = "http://computer-database.herokuapp.com/computers";
	static WebDriver mDriver;

	public static String takeScreenshot(WebDriver driver, String screenName) {

		try {

			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			String dest = System.getProperty("user.dir") + "\\output\\screenshots\\" + screenName + ".png";

			File destination = new File(dest);

			FileUtils.copyFile(source, destination);

			return dest;

		} catch (Exception e) {
			System.out.println("Exception while taking screenshot." + e.getMessage());
			return e.getMessage();
		}

	}

	public static WebDriver startChromeDriver() {

		String chromeDriverLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\webdrivers\\chrome\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);

		mDriver = new ChromeDriver();

		mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		mDriver.manage().window().maximize();

		return mDriver;

	}

	public static boolean createComputer(String computerName, WebDriver driver, ExtentTest extentTest) {

		boolean result = false;

		try {

			extentTest.log(LogStatus.INFO, "Navigating to Main Page.");
			if (!driver.getCurrentUrl().equals(MAIN_PAGE)) {
				driver.get(MAIN_PAGE);
			}

			extentTest.log(LogStatus.INFO, "Adding a new Computer.");
			driver.findElement(By.id("add")).click();
			driver.findElement(By.id("name")).sendKeys(computerName);
			driver.findElement(By.id("introduced")).sendKeys("2017-06-15");
			driver.findElement(By.id("discontinued")).sendKeys("2027-06-15");

			Select dropdown = new Select(driver.findElement(By.id("company")));

			dropdown.selectByIndex(10);

			driver.findElement(By.cssSelector("input[type='submit']")).submit();

			WebElement createdWarning = driver.findElement(By.xpath("//*[@id='main']/div[1]"));

			result = createdWarning.getText().contains("has been created");

		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception while creating the computer.");
			return result;
		}

		return result;

	}

	public static boolean searchComputer(String computerName, WebDriver driver, ExtentTest extentTest) {

		boolean result = false;

		try {

			extentTest.log(LogStatus.INFO, "Navigating to Main Page.");
			if (!driver.getCurrentUrl().equals(MAIN_PAGE)) {
				driver.get(MAIN_PAGE);
			}

			extentTest.log(LogStatus.INFO, "Searching Computer.");
			driver.findElement(By.id("searchbox")).sendKeys(computerName);
			driver.findElement(By.id("searchsubmit")).submit();

			result = driver.findElements(By.linkText(computerName)).size() > 0;

		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception while searching for the computer.");
			return result;
		}

		return result;

	}

	public static boolean removeComputer(String computerName, WebDriver driver, ExtentTest extentTest) {

		boolean result = false;

		try {

			extentTest.log(LogStatus.INFO, "Navigating to Main Page.");
			if (!driver.getCurrentUrl().equals(MAIN_PAGE)) {
				driver.get(MAIN_PAGE);
			}

			extentTest.log(LogStatus.INFO, "Searching Computer to remove.");
			driver.findElement(By.id("searchbox")).sendKeys(computerName);
			driver.findElement(By.id("searchsubmit")).submit();

			if (driver.findElements(By.linkText(computerName)).size() > 0) {
				extentTest.log(LogStatus.INFO, "Computer found. Removing.");

				driver.findElement(By.linkText(computerName)).click();

				driver.findElement(By.cssSelector("input[type='submit'][class='btn danger']")).submit();

				WebElement deletedWarning = driver.findElement(By.xpath("//*[@id='main']/div[1]"));

				result = deletedWarning.getText().contains("has been deleted");

			} else {
				extentTest.log(LogStatus.ERROR, "Computer not found. Impossible to remove.");

			}

		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception while removing the computer.");
			return result;
		}

		return result;

	}

	public static boolean readComputer(String computerName, WebDriver driver, ExtentTest extentTest) {

		boolean result = false;

		try {

			extentTest.log(LogStatus.INFO, "Navigating to Main Page.");
			if (!driver.getCurrentUrl().equals(MAIN_PAGE)) {
				driver.get(MAIN_PAGE);
			}

			extentTest.log(LogStatus.INFO, "Searching Computer to read.");
			driver.findElement(By.id("searchbox")).sendKeys(computerName);
			driver.findElement(By.id("searchsubmit")).submit();

			if (driver.findElements(By.linkText(computerName)).size() > 0) {
				extentTest.log(LogStatus.INFO, "Computer found. Reading it.");

				driver.findElement(By.linkText(computerName)).click();

				result = driver.findElement(By.id("name")).getAttribute("value").equals(computerName);

			} else {
				extentTest.log(LogStatus.ERROR, "Computer not found. Impossible to remove.");

			}

		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception while removing the computer.");
			return result;
		}

		return result;

	}

	public static void closeDriver() {

		mDriver.quit();

	}

	public static boolean updateComputer(String computerName, String updatedComputerName, WebDriver driver, ExtentTest extentTest) {
		boolean result = false;

		try {

			extentTest.log(LogStatus.INFO, "Navigating to Main Page.");
			if (!driver.getCurrentUrl().equals(MAIN_PAGE)) {
				driver.get(MAIN_PAGE);
			}

			extentTest.log(LogStatus.INFO, "Searching Computer to update.");
			driver.findElement(By.id("searchbox")).sendKeys(computerName);
			driver.findElement(By.id("searchsubmit")).submit();

			if (driver.findElements(By.linkText(computerName)).size() > 0) {
				extentTest.log(LogStatus.INFO, "Computer found. Updating.");

				driver.findElement(By.linkText(computerName)).click();

				driver.findElement(By.id("name")).clear();
				driver.findElement(By.id("name")).sendKeys(updatedComputerName);

				driver.findElement(By.cssSelector("input[type='submit'][class='btn primary']")).submit();

				WebElement updatedWarning = driver.findElement(By.xpath("//*[@id='main']/div[1]"));

				result = updatedWarning.getText().contains("has been updated");

			} else {
				extentTest.log(LogStatus.ERROR, "Computer not found. Impossible to Update.");

			}

		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception while Updating the computer.");
			return result;
		}

		return result;

	}

}
