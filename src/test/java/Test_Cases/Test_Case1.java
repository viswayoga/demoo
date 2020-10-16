package Test_Cases;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Test_Case1
{
	public ExtentHtmlReporter hmtlReporter;
	public ExtentReports  extent;
	public ExtentTest test;
	
	@BeforeTest
	public void setUp()
	{
		hmtlReporter = new ExtentHtmlReporter("./reports/extent.html");
		
		hmtlReporter.config().setEncoding("utf-8");
		hmtlReporter.config().setDocumentTitle("way to automation report");
		hmtlReporter.config().setReportName("Automation  Test Results");
		hmtlReporter.config().setTheme(Theme.STANDARD);
		
		
		extent = new ExtentReports();
		extent.attachReporter(hmtlReporter);
		
		extent.setSystemInfo("Automation Tester", "viswanath");
		extent.setSystemInfo("Orgazation", "infosys");
		extent.setSystemInfo("BuiltNumber", "w2A-1234");
		
		
	}
	
	@AfterTest
	public void endReport()
	{
		extent.flush();
		
	}
	
	@Test
	public void doLogin()
	{
		test = extent.createTest("user login test");
		System.out.println("Executing the login Test");
		
	}
	
	@Test
	public void userReg()
	{
		test = extent.createTest("user Registration test");
	    Assert.fail("User Reg Test Failed");
		
	}
	
	@Test
	public void skipTest()
	{
		test = extent.createTest("user Registration test");
		throw new SkipException("Skipping the test cases");
	   
		
	}
	
	
	@AfterMethod
	public void tearDown(ITestResult rslt)
	{
		if(rslt.getStatus()==ITestResult.FAILURE)
		{
			String excepionMessage = Arrays.toString(rslt.getThrowable().getStackTrace());
			test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
					+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
					+ " \n");
			
		/*	
			try {

				ExtentManager.captureScreenshot();
				testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
								.build());
			} catch (IOException e) {

			}*/
			
			

			String failureLogg = "TEST CASE FAILED";
			Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			test.log(Status.FAIL, m);


			
		}else if(rslt.getStatus()==ITestResult.SKIP)
		{
           String methodName = rslt.getMethod().getMethodName();
			
			String logText = "<br>"+ "Test Cases: - "+methodName.toUpperCase()+" SKIPPED  "+"</br>";
			
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			test.skip(m);
			
			
		}else if(rslt.getStatus()==ITestResult.SUCCESS)
		{
			String methodName = rslt.getMethod().getMethodName();
			
			String logText = "<br>"+ "Test Cases: - "+methodName.toUpperCase()+"  PASSED"+"</br>";
			
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.pass(m);
			
		}
		
		
	}


}
