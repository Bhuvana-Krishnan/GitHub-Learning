package datadriventesting;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.IOException;

import java.time.Duration;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class Datadriventest {
	
	public static WebDriver driver;
	static ResourceBundle rb;  //for reading properties file
	static String br;   //to store browser name

	@BeforeClass
	public static WebDriver setup() {
		rb=ResourceBundle.getBundle("Config");
		br=rb.getString("browser");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;
	}
	
	@Test(dataProvider = "loginData")
	public void logintest(String user, String pwd,String exp)
	{
		driver.get("https://admin-demo.nopcommerce.com/login");
		
		WebElement txtemail	= driver.findElement(By.id("Email"));
		txtemail.clear();
		txtemail.sendKeys(user);
		
		
		WebElement txtpwd = driver.findElement(By.id("Password"));
		txtpwd.clear();
		txtpwd.sendKeys(pwd);
		
		driver.findElement(By.className("buttons")).click();//login button
		
		String exp_title = "Dashboard / nopCommerce administration";
		String act_title = driver.getTitle();
		
		if (exp.equals("valid"))
		{
			if(exp_title.equals(act_title)) {
				
				
				AssertJUnit.assertTrue(true);
			}
			else {
				AssertJUnit.assertTrue(false);
			}
		}
			
			else if(exp.equals("Invalid"))
			{
				
				if(exp_title.equals(act_title)) 
				{
					driver.findElement(By.className("nav-link")).click();
					AssertJUnit.assertTrue(false);
					
				}
				else {
					AssertJUnit.assertTrue(true);
				}
				
			}		
		//System.out.println(user+pwd+exp);		
	}
	
	@DataProvider(name="loginData")
	public String [][] getdata() throws IOException 
	{		
		/*String loginData[][] = {
				{"admin@yourstore.com","admin","valid"},
				{"admin@yourstore.com","adm","Invalid"},
				{"adm@yourstore.com","admin","Invalid"},
				{"adm@yourstore.com","adm","Invalid"}
		};*/
		
		// get data feom XL
		
		String path = "./datafiles/logindata_Webscrape.xlsx";
		XLUtility xlutils = new XLUtility(path);
		int totalrows = xlutils.getRowCount("Sheet1");
		int totalcols = xlutils.getColCount("Sheet1",1);
		
		String loginData[][]= new String[totalrows][totalcols];
		
		
		for(int i =1;i<=totalrows;i++) {
			
			for(int j=0;j<totalcols;j++)
			{
				loginData [i-1][j] = xlutils.getCellData("Sheet1", i, j);
			}
			
		}
		
		return loginData;
	}
	
	@AfterClass()
	void teardown()
	{
		driver.close();
	}

	
	
}
