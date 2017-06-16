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
 * This test creates a Computer, then search for the recent created Computer, Open it and  
 * change the Computer' name. 
 * It is expected that is possible to update the computer name.
 * After the test, the created Computer is removed. 
 *  
 */

public class UpdateTest {

	private final String UPDATED_COMPUTER_NAME = "Dhielber's New Computer";
	private final String COMPUTER_NAME = "Dhielber's Computer";

	private WebDriver driver;
	private ExtentReports extentReport;
	private ExtentTest extentTest;

	@BeforeClass
	public void beforeClass() {

		extentReport = ExtentManager.Instance();
		extentTest = extentReport.startTest("Update Computer Test Case", "Verify it is possible to update a computer.");

		driver = Utility.startChromeDriver();
		extentTest.log(LogStatus.INFO, "Browser Launched.");

	}

	@Test
	public void updateComputerTest() {

		Utility.createComputer(COMPUTER_NAME, driver, extentTest);

		boolean updateComputer = Utility.updateComputer(COMPUTER_NAME, UPDATED_COMPUTER_NAME, driver, extentTest);

		boolean expectetResult = true;

		Assert.assertEquals(updateComputer, expectetResult);

		extentTest.log(LogStatus.PASS, "Computer was updated.");
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

		Utility.removeComputer(UPDATED_COMPUTER_NAME, driver, extentTest);

		extentTest.log(LogStatus.INFO, "Closing WebDriver.");
		Utility.closeDriver();

		extentReport.endTest(extentTest);
		extentReport.flush();
		extentReport.close();

	}

}
