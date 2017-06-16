package backbase;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.ExtentManager;
import utility.Utility;

/*
 * This test creates a Computer, then search for the recent created Computer and Open it to prove it is readable. 
 * It is expected that the computer is created AND can be found after that AND can be opened after that.
 * After the test, the created Computer is removed. 
 *  
 */

public class ReadTest {

	private final String COMPUTER_NAME = "Dhielber's Computer";

	private WebDriver driver;
	private ExtentReports extentReport;
	private ExtentTest extentTest;

	@BeforeClass
	public void beforeClass() {

		extentReport = ExtentManager.Instance();
		extentTest = extentReport.startTest("Read Computer Test Case", "Verify it is possible to read a computer.");

		driver = Utility.startChromeDriver();
		extentTest.log(LogStatus.INFO, "Browser Launched.");

	}

	@Test
	public void readComputerTest() {

		Utility.createComputer(COMPUTER_NAME, driver, extentTest);

		boolean readComputer = Utility.readComputer(COMPUTER_NAME, driver, extentTest);

		boolean expectetResult = true;

		Assert.assertEquals(readComputer, expectetResult);

		extentTest.log(LogStatus.PASS, "Computer was read.");
		String screenshot = Utility.takeScreenshot(driver, extentTest.getTest().getName());
		extentTest.log(LogStatus.PASS, extentTest.addScreenCapture(screenshot));

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			String screenshot_path = Utility.takeScreenshot(driver, result.getName());
			String image = extentTest.addScreenCapture(screenshot_path);
			extentTest.log(LogStatus.FAIL, extentTest.getDescription(), image);
		}

	}

	@AfterClass
	public void afterClass() {

		Utility.removeComputer(COMPUTER_NAME, driver, extentTest);

		extentTest.log(LogStatus.INFO, "Closing WebDriver.");
		Utility.closeDriver();

		extentReport.endTest(extentTest);
		extentReport.flush();
		extentReport.close();

	}

}
