package commonFunctions;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;
	//method for launch browser
	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else 
		{
			Reporter.log("Browser value is Not Matching",true);
		}
		return driver;
	}
	//method for launch Url
	public static void openUrl() throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}

	//method for wait for all webelement waitForElement
	public static void waitForElement(String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		
	}
	//method for textboxes
	public static void typeAction(String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}
	//method for buttons,links,radiobuttons,checkboxes,images
	public static void clickAction(String Locator_Type,String Locator_Value)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
		
	}
	//method for validate page title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_title=driver.getTitle();
		try {
		Assert.assertEquals(Actual_title, Expected_Title, "Title is Not matching ");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
			
		}
	}
	//method for closing browser
	public static void closeBrowser()
	{
		driver.quit();
	}
	//method for date generation
	public static String generatedate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("YYYY-MM-dd hh-mm-ss");
		return df.format(date);
	}
	//method for listboxes
	public static void dropDownAction(String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value=Integer.parseInt(Test_Data);
			WebElement element =driver.findElement(By.id(Locator_Value));
			Select select=new Select(element);
			select.selectByIndex(value);
		
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(Test_Data);
			WebElement element=driver.findElement(By.xpath(Locator_Value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value=Integer.parseInt(Test_Data);
			WebElement element=driver.findElement(By.name(Locator_Value));
			Select select=new Select(element);
			select.selectByIndex(value);
			
		}
		
	}
	//method for capturing stock number
	public static void stockCapture(String Locator_Type,String Locator_Value) throws Throwable
	{
		String Stocknum="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			Stocknum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
			
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			Stocknum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			Stocknum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//write stock number into note pad
		FileWriter fw=new FileWriter("./CaptureData/Stocknumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Stocknum);
		bw.flush();
		bw.close();
		
		
		
	}
	//method for stockTable
	public static void stockTable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/Stocknumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-button"))).click();
		Thread.sleep(4000);
		String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Thread.sleep(4000);
		Reporter.log(Exp_data+"  "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_data, Act_Data, "Stock Number is Not Matching");
			
		
		}catch(AssertionError a){
			System.out.println(a.getMessage());
			
		}
	}
	
	//method for capture supplier number
     public static void captureSupplier(String Locator_Type,String Locator_Value) throws Throwable
     {
    	 String Suppliernum="";
    	 if(Locator_Type.equalsIgnoreCase("id"))
    	 {
    		 Suppliernum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
    	 }
    	 else if(Locator_Type.equalsIgnoreCase("xpath"))
    	 {
    		 Suppliernum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
    	 }
    	 else if(Locator_Type.equalsIgnoreCase("name"))
    	 {
    		 Suppliernum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
    	 }
    	 //writing suppliernumber into notepad
    	 FileWriter fw=new FileWriter("./CaptureData/Suppliernumber.txt");
    	 BufferedWriter bw=new BufferedWriter(fw);
    	 bw.write(Suppliernum);
    	 bw.flush();
    	 bw.close();
    	 
     }
     //method for SupplierTable
     public static void supplierTable() throws Throwable
     {
    	 //read suppliernumber from notepad
    	 FileReader fr=new FileReader("./CaptureData/Suppliernumber.txt");
    	 BufferedReader br=new BufferedReader(fr);
    	 String Exp_num=br.readLine();
    	 if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).isDisplayed())
    		 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-panel"))).click();
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).click();
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).clear();
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).sendKeys(Exp_num);
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-button"))).click();
    	 Thread.sleep(4000);
    	 String Act_num=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
    	 Thread.sleep(4000);
    	 Reporter.log(Exp_num+"   "+Act_num,true);
    	 try {
    		 Assert.assertEquals(Exp_num, Act_num, "Suppliernumber is Not Matching");
    	 
    		 
     }catch(AssertionError a)
    	 {
    	 System.out.println(a.getMessage());
    	 }
     }
     //method for capture customernumber
     public static void captureCustomer(String Locator_Type,String Locator_Value) throws Throwable
     {
    	 String Customernum="";
    	 if(Locator_Type.equalsIgnoreCase("id"))
    	 {
    		 Customernum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
    				 
    	 }
    	 else if(Locator_Type.equalsIgnoreCase("xpath"))
    	 {
    		 Customernum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
    	 }
    	 else if(Locator_Type.equalsIgnoreCase("name"))
    	 {
    		 Customernum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
    	 }
    	 //method for writing Customernumber into notepad
    	 FileWriter fw=new FileWriter("./CaptureData/Customernumber.txt");
    	 BufferedWriter bw=new BufferedWriter(fw);
    	 bw.write(Customernum);
    	 bw.flush();
    	 bw.close();
    	 
     }
     //method for customerTable
     public static void customerTable() throws Throwable
     {
    	 FileReader fr=new FileReader("./CaptureData/Customernumber.txt");
    	 BufferedReader br=new BufferedReader(fr);
    	 String Exp_num=br.readLine();
    	 if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).isDisplayed())
    		 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-panel"))).click();
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).clear();
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-textbox"))).sendKeys(Exp_num);
    	 driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search-button"))).click();
    	 Thread.sleep(4000);
    	 String Act_num=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
    	 Thread.sleep(4000);
    	 Reporter.log(Exp_num+"  "+Act_num,true);
    	 try {
    		  Assert.assertEquals(Exp_num, Act_num, "Customer number is Not Matching");
    	 
     }catch(AssertionError a)
    	 {
    	 System.out.println(a.getMessage());
    	 }
     }
     
    	 
     
	
	
	

	}
