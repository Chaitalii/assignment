Pre-requisites:
1.Required browser should be installed in local machine
2.Maven and jdk should be installed
3.For running tests in Safari, make sure Allow Remote Automation option is enabled.

To run:
Browser name to be passed from testng.xml(location: parallel to the project repo). Chrome is set in the xml already.
Parallel execution is also enabled.


Go the project directory and run : mvn clean test

Reports:
TestNG Extent report is implemented in this project and the reports can be found in ExtentReports folder inside project directory.

The screenshots for failed cases are also attached in this report.

The full view screenshots of the failed tests can also be found in screenshots/ folder.

Logs:
Execution logs can be found in application.log file.
ExtentReport has execution log printed as well.

Test data required for test execution is placed in TestData.xlsx file inside project directory.


Note:
1. Tests can be run in windows and mac both.However, I don't have mac system, so could not do a test run for mac. I have run tests in windows.
I have placed the safari/chrome drivers for mac inside src/test/resources/mac folder. However, if they do not work, kindly delete theses files and place chrome/safari drivers for mac in the same location.

2. As there was no requirement to run the tests with multiple data set, I have just implemented excel as external data source as we have 1 set of data for testing at this point.
If required, I can implement DataProvider. Kindly let me know if you want me to implement this in this framework :)




