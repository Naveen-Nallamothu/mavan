package driverscript;



import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunLib.functionLibary;
import utils.ExcelFileUtil;

public class Driverscript {

	static WebDriver driver;
	public static void main(String[] args) throws Exception {
		ExcelFileUtil excel=new ExcelFileUtil();
		for(int i=1;i<=excel.rowCount("MasterTestCases");i++){
			String ModuleStatus="";
		
		if	(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("y"))
	{
			String TCModule=excel.getData("MasterTestCases", i, 1);
			
			ExtentReports report=new ExtentReports("D:\\Naveen82\\StockAccount_Hybrid_maven\\Reports\\myreport.html");
			ExtentTest write=report.startTest("logintest");
			for(int j=1;j<=excel.rowCount(TCModule);j++){
				
			    String Description=excel.getData(TCModule, j, 0);
				String Function_Name=excel.getData(TCModule, j, 1);
				String	Locator_Type=excel.getData(TCModule, j, 2);
				String Locator_Value=excel.getData(TCModule, j, 3);
				String Test_Data=excel.getData(TCModule, j, 4);
				
			try{		
				if (Function_Name.equalsIgnoreCase("startBrowser")) 
				{
					driver=functionLibary.startBrowser();
					write.log(LogStatus.INFO, Description);
				}
				
				else if (Function_Name.equalsIgnoreCase("openApplication")) 
				{
				 functionLibary.openapp(driver);	
				 write.log(LogStatus.INFO, Description);
				}
				else if (Function_Name.equalsIgnoreCase("waitForElement")) 
				{
				 functionLibary.waitforElement(driver, Locator_Type, Locator_Value, Test_Data);	
				 write.log(LogStatus.INFO, Description);
				}
				else if (Function_Name.equalsIgnoreCase("typeAction")) 
				{
				 functionLibary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);	
				 write.log(LogStatus.INFO,Description);
				}
				else if (Function_Name.equalsIgnoreCase("clickAction")) 
				{
				 functionLibary.clickAction(driver, Locator_Type, Locator_Value);	
				 write.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("closeBrowser")) {
					functionLibary.closeapp();
					write.log(LogStatus.INFO, Description);
				}
				
				else if(Function_Name.equalsIgnoreCase("captureData")){
					functionLibary.captureData(driver,  Locator_Type, Locator_Value);
					write.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("tableValidation")){
					functionLibary.tableValidation(driver, Test_Data);
					write.log(LogStatus.INFO, Description);
				}
				
				excel.setData(TCModule,j,5,"Pass");
				ModuleStatus="Pass";
			
			}catch(Exception e){
				System.out.println("the exception is ");
				e.printStackTrace();
				excel.setData(TCModule,j,5,"Fail");
				ModuleStatus="Fail";
				
				String reqDate=functionLibary.getdate();
				File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File("D:\\Naveen82\\StockAccount_Hybrid_maven\\Screenshots"+Description+reqDate+".png"));
				write.log(LogStatus.FAIL, Description);
				write.log(LogStatus.INFO,write.addScreenCapture("D:\\Naveen82\\StockAccount_Hybrid_maven\\Screenshots"+Description+reqDate+".png") );		
				
				break;
			}
	}
	
	
	if (ModuleStatus.equalsIgnoreCase("Pass")) {
		excel.setData("MasterTestCases",i,3,"Pass");
	} else {
		excel.setData("MasterTestCases",i,3,"Fail");
	}
		
	report.endTest(write);
	report.flush();
	
	}
		else {
		excel.setData("MasterTestCases", i, 3,"Not executed");
	         }
	
        }
	}
}
