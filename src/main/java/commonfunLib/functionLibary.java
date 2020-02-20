package commonfunLib;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utils.PropertyFileUtil;

public class functionLibary {

 static WebDriver driver;
 
 public static WebDriver startBrowser() throws IOException{
	 
	 if (PropertyFileUtil.getvalueForkey("browser").equalsIgnoreCase("chrome")) {
		
	System.setProperty("webdriver.chrome.driver","D:\\Naveen82\\StockAccount_Hybrid_maven\\drivers\\chromedriver.exe");
	
	driver=new ChromeDriver();
	 
	 }else if (PropertyFileUtil.getvalueForkey("Browser").equalsIgnoreCase("firefox")) {
		
		 System.setProperty("webdriver.gecko.driver","D:\\Naveen82\\StockAccount_Hybrid_maven\\drivers\\chromedriver.exe");
			
			driver=new FirefoxDriver();
			 
	}else {
		
		 System.setProperty("webdriver.ie.driver","D:\\Naveen82\\StockAccount_Hybrid_maven\\drivers\\chromedriver.exe");
			
			driver=new InternetExplorerDriver();
	}
 
 return driver;
 }


public static void openapp(WebDriver driver) throws IOException 
{
	driver.get(PropertyFileUtil.getvalueForkey("url"));

	driver.manage().window().maximize();

}

public static void waitforElement(WebDriver driver,String locatortype, String locatorvalue,String waittime)
{
WebDriverWait mywait=new WebDriverWait(driver, Integer.parseInt(waittime));
if (locatortype.contains("id")) {
	
	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if (locatortype.contains("name")) {
	
	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
}
else if(locatortype.contains("xpath")) {
	
	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
}
else 
{
	System.out.println("unable to locate waitforelement method with"+locatortype);
}

}

public static void typeAction(WebDriver driver,String locatortype, String locatorvalue,String testdata)
{
if (locatortype.contains("id")) 
{
driver.findElement(By.id(locatorvalue)).clear();
driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
}
else if (locatortype.contains("name")) 
{
	driver.findElement(By.name(locatorvalue)).clear();
	driver.findElement(By.name(locatorvalue)).sendKeys(testdata);	
}
else if(locatortype.contains("xpath")) 
{
	driver.findElement(By.xpath(locatorvalue)).clear();
	driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);	
}
else
{
	System.out.println("unable to locate for typeAction method with "+locatortype);
}

}

public static void clickAction(WebDriver driver,String locatortype, String locatorvalue)
{
if (locatortype.contains("id")) 
{
	
	driver.findElement(By.id(locatorvalue)).click();

}
else if (locatortype.contains("name")) 
{
	driver.findElement(By.name(locatorvalue)).click();
}
else if (locatortype.contains("xpath")) 
{
	driver.findElement(By.xpath(locatorvalue)).click();
}
else 
{
	System.out.println("unable to locate for typeAction method with "+locatortype);	
}

}

public static String getdate()
{
Date d=new Date();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss");
String req=sdf.format(d);
return req;
}

public static void tableValidation(WebDriver driver,String column) throws Exception{
	
	FileReader  fr=new FileReader("./CaptureData/suppnumber.txt");
	BufferedReader br=new BufferedReader(fr);
	
	String Exp_data=br.readLine();
	
	if(driver.findElement(By.id(PropertyFileUtil.getvalueForkey("searchtextbox"))).isDisplayed()){
		Thread.sleep(5000);
		driver.findElement(By.id(PropertyFileUtil.getvalueForkey("searchtextbox"))).sendKeys(Exp_data);
		driver.findElement(By.id(PropertyFileUtil.getvalueForkey("searchbutton"))).click();
	}else{
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("searchpanelbutton"))).click();
		Thread.sleep(5000);
		driver.findElement(By.id(PropertyFileUtil.getvalueForkey("searchtextbox"))).sendKeys(Exp_data);
		driver.findElement(By.id(PropertyFileUtil.getvalueForkey("searchbutton"))).click();
	}
	
	WebElement table=driver.findElement(By.id(PropertyFileUtil.getvalueForkey("suppliertable")));
	
	List<WebElement>rows=table.findElements(By.tagName("tr"));
	
	for(int i=1;i<rows.size();i++){
	       String act_data= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span")).getText();	
	       Assert.assertEquals(Exp_data, act_data); 
	       System.out.println(act_data+"   "+Exp_data);
	       break;
	}
	
}


public static void captureData(WebDriver driver,String locatortytpe,
		String locatorvalue) throws Exception{
	
	String supplierdata="";
	
	if(locatortytpe.equalsIgnoreCase("id")){
		supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	
	else if(locatortytpe.equalsIgnoreCase("xpath")){
		supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	
	else if(locatortytpe.equalsIgnoreCase("name")){
		supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	
	FileWriter fw=new FileWriter ("D:\\Naveen82\\StockAccount_Hybrid_maven\\CaptureData\\suppnumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(supplierdata);
	bw.flush();
	bw.close();	
}






public static void closeapp()
{
driver.close();
}	

}



