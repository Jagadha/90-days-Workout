package day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Honda {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		if(driver.findElementByXPath("//button[@class='close']").isDisplayed()) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='close']")));
			driver.findElementByXPath("//button[@class='close']").click();
		}
		
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("link_Scooter")));
		driver.findElementById("link_Scooter").click();
		driver.findElementByXPath("//img[contains(@src,'dio')]").click();

//3) Click on Specifications and mouseover on ENGINE
		driver.findElementByLinkText("Specifications").click();
		WebElement dioengine = driver.findElementByLinkText("ENGINE");
		Actions builder = new Actions(driver);
		builder.moveToElement(dioengine).perform();

//4) Get Displacement value		
		String disp1 = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText().replaceAll("[A-Za-z]","" );
		double diodisp = Double.parseDouble(disp1);

//5) Go to Scooters and click Activa 125
		driver.findElementById("link_Scooter").click();
		driver.findElementByXPath("//img[contains(@src,'activa-125')]").click();

//6) Click on Specifications and mouseover on ENGINE	
		driver.findElementByLinkText("Specifications").click();
		WebElement actengine = driver.findElementByLinkText("ENGINE");
		builder.moveToElement(actengine).perform();
		
//7) Get Displacement value
		String disp2 = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText().replaceAll("[A-Za-z]", "");
		double actdisp = Double.parseDouble(disp2);

//8) Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
		if(diodisp>actdisp) {
			System.out.println("Dio has better displacement");
		}else {
			System.out.println("Activa 125 has better displacement");
		}
		
//9) Click FAQ from Menu 		
		driver.findElementByLinkText("FAQ").click();

//10) Click Activa 125 BS-VI under Browse By Product
		driver.findElementByLinkText("Activa 125 BS-VI").click();

//11) Click  Vehicle Price 
		driver.findElementByXPath("//a[text()=' Vehicle Price']").click();
		
//12) Make sure Activa 125 BS-VI selected and click submit
		WebElement model = driver.findElementById("ModelID6");
		Select mod = new Select(model);
		List<WebElement> allsec = mod.getAllSelectedOptions();
		for (int i = 0; i < allsec.size(); i++) {
			if(allsec.get(i).getText().contains("Activa 125")) {
				System.out.println("Activa 125 BS-VI is selected");
			}
		}		
		driver.findElementByXPath("//button[contains(@onclick,'validateformPriceMaster')]").click();

//13) click the price link
		String text = driver.findElement(By.xpath("(//table[@id='tblPriceMasterFilters']//td)[2]")).getText();
		if(text.contains("Activa 125")) {
			driver.findElementByXPath("//table[@id='tblPriceMasterFilters']//a").click();
		}
			
//14) Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));
		WebElement stateid = driver.findElementById("StateID");
		WebElement cityid = driver.findElementById("CityID");
		Select state = new Select(stateid);
		state.selectByVisibleText("Tamil Nadu");
		Select city = new Select(cityid);
		city.selectByVisibleText("Chennai");
		
//15) Click Search
		driver.findElementByXPath("//button[text()='Search']").click();
		
//16) Print all the 3 models and their prices
		List<WebElement> rows = driver.findElementsByXPath("//tbody[@style='background-color:white']//tr");
		
		for(int i = 0; i < rows.size(); i++) {
			WebElement eachRow = rows.get(i);
			List<WebElement> cols = eachRow.findElements(By.xpath("//tbody[@style='background-color:white']//tr["+(i+1)+"]//td"));
			if(cols.size()==3) {
				for(int j=0 ; j < cols.size()-1;j++) {
					System.out.print(driver.findElementByXPath("//tbody[@style='background-color:white']//tr["+(i+1)+"]//td["+(j+2)+"]").getText());
					System.out.print(" ");				
				}
			}
				else {
					for(int j=0 ; j < cols.size();j++) {
					System.out.print(driver.findElementByXPath("//tbody[@style='background-color:white']//tr["+(i+1)+"]//td["+(j+1)+"]").getText());
					System.out.print("\t");
				}
			}
			System.out.println();
		}
		driver.quit();
	}
}