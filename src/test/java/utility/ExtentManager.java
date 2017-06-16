package utility;

import com.relevantcodes.extentreports.ExtentReports;

/*
 * This class is responsible for managing the Report Instance, assuring that only 
 * one report will be created and will consolidate all the execution information. 
 */

public class ExtentManager {

	protected final static String REPORT_FILE = System.getProperty("user.dir") + "\\output\\Report.html";

	public static ExtentReports Instance() {
		ExtentReports extent;
		String Path = REPORT_FILE;
		extent = new ExtentReports(Path, false);

		return extent;
	}

}