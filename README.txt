IDE used:
- Eclipse.

Dependencies:
- Selenium WebDriver (chromedriver);
- TestNG;
- ExtentReport.

After importing the project, please update the project using Maven (Alt + F5) to get the dependencies.
You also will need to have the Eclipse TestNG plugin installed. It can be download via Eclipse Marketplace, under Help menu.

If everything went OK, you will see the following sctructure:

- src
	|
	-- test	
		|
		-- java
		|	|
		|	-- backbase 
		|	|	|
		|	|	-- CreateTest.java
		|	|	-- DeleteTest.java
		|	|	-- ReadTest.java
		|	|	-- UpdateTest.java
		|	|
		|	-- testsuite			
		|	|	|
		|	|	-- backbaseTestSuite.xml
		|	|
		|	-- utility
		|		|
		|		-- ExtentManager.java
		|		-- Utility.java
		|
		-- resources
			|
			-- webdrivers
				|
				-- chrome
					|
					-- chromedriver.exe
- output
	|
	-- screenshots
	|	|
	|	-- *.png
	|
	-- Report.html
					
To run the tests individually, right click on the java file and run it as a TestNG Test.
Also, you can run the whole set of tests by right clicking the backbaseTestSuite.xml and running it as TestNG Suite.

After running, you will find the report inside the "output" folder. The screenshots are related to the last execution only.

I tried to create the variables, methods and classes with the best names I thought to avoid comments into the code. 
As ExtentReport uses logs between code lines, I believe the code would not be so clear if I put a lot of comments.

For test purposes and assurance, the tests take screenshots when Passing and Failing.
I haven't inserted any precondition for any tests, as I created single computers. 
However, for production purposes, it would be good to check whether a computer 
already exists when creating it, since the system allows duplicated computers.