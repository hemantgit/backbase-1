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
 * This test creates a Computer, then remove it.
 * It is expected that is possible to remove the computer.
 *  
 */

public class DeleteTest {

	private final String COMPUTER_NAME = "Dhielber's Computer";

	private WebDriver driver;
	private ExtentReports extentReport;
	private ExtentTest extentTest;

	@BeforeClass
	public void beforeClass() {

		extentReport = ExtentManager.Instance();

		extentTest = extentReport.startTest("Delete Computer Test Case", "Verify it is possible to delete a computer.");

		driver = Utility.startChromeDriver();
		extentTest.log(LogStatus.INFO, "Browser Launched.");

	}

	@Test
	public void deleteComputerTest() {

		Utility.createComputer(COMPUTER_NAME, driver, extentTest);

		boolean removeCreatedComputer = Utility.removeComputer(COMPUTER_NAME, driver, extentTest);

		boolean expectetResult = true;

		Assert.assertEquals(removeCreatedComputer, expectetResult);

		extentTest.log(LogStatus.PASS, "Computer was removed.");
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

		extentTest.log(LogStatus.INFO, "Closing WebDriver.");
		Utility.closeDriver();

		extentReport.endTest(extentTest);
		extentReport.flush();
		extentReport.close();

	}

}
