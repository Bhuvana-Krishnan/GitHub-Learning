package datadriventesting;

import java.io.IOException;
import java.time.Duration;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTabletoExcel_Naveen {

	public static void main(String[] args) throws IOException
	
	{
		WebDriver driver;
			
		ResourceBundle rb=ResourceBundle.getBundle("Config");
		String br=rb.getString("browser");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_population");
		
		String path ="./datafiles/population.xlsx";
		
		XLUtility_Webscrape xlutil = new XLUtility_Webscrape(path);
		
		//Write header in excel sheet
		//xlutil.setCellData("Sheet1", 0, 0, "Rank");
		xlutil.setCellData("Sheet1", 0, 0, "Country");
		xlutil.setCellData("Sheet1", 0, 1, "Population");
		xlutil.setCellData("Sheet1", 0, 2, "% of World");
		xlutil.setCellData("Sheet1", 0, 3, "Date");
		xlutil.setCellData("Sheet1", 0, 4, "Source");
		
		//capture table rows
		
		WebElement table = driver.findElement(By.xpath("//table[@class='wikitable sortable sticky-header col2left col6left jquery-tablesorter']/tbody"));
		
		int rows = table.findElements(By.xpath("tr")).size(); //rows present in web table
		
		for(int r = 1; r<=rows; r++)
		{
			
		//read data from web table	
		//String rank = table.findElement(By.xpath("tr["+r+"]/td[1]")).getText();
		String country = table.findElement(By.xpath("tr["+r+"]/td[1]")).getText();
		String population = table.findElement(By.xpath("tr["+r+"]/td[2]")).getText();
		String perofWorld = table.findElement(By.xpath("tr["+r+"]/td[3]")).getText();
		String date = table.findElement(By.xpath("tr["+r+"]/td[4]")).getText();
		String source = table.findElement(By.xpath("tr["+r+"]/td[5]")).getText();
			
		System.out.println(country+population+perofWorld+date+source);
		
		//writing the data in XL sheet
		
		//xlutil.setCellData("Sheet1", r, 0, rank);
		xlutil.setCellData("Sheet1", r, 0, country);
		xlutil.setCellData("Sheet1", r, 1, population);
		xlutil.setCellData("Sheet1", r, 2, perofWorld);
		xlutil.setCellData("Sheet1", r, 3, date);
		xlutil.setCellData("Sheet1", r, 4, source);
		
		}
		
		System.out.println("Web Scrapping is done successfully.....");
		
		driver.close();
		
	}

	}


