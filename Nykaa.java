package day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);

//1) Go to https://www.nykaa.com/
		driver.navigate().to("https://www.nykaa.com/");
		
//2) Mouseover on Brands and Mouseover on Popular
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='menu-dropdown-icon']")));
		WebElement brands = driver.findElementByXPath("//li[@class='menu-dropdown-icon']");
		Actions builder = new Actions(driver);
		Thread.sleep(5000);
		builder.moveToElement(brands).perform();
		Thread.sleep(5000);
		WebElement popular = driver.findElementByXPath("//a[@class='brandHeadingbox ']");
		builder.moveToElement(popular).perform();
		
//3) Click L'Oreal Paris		
		driver.findElementByXPath("//li[@class='brand-logo menu-links'][5]").click();
		
//4) Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));
		Thread.sleep(5000);
		if(driver.getTitle().contains("L'Oreal Paris")) {
			System.out.println("New Window opened with Loreal brand");
		}
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[@class='pull-left']")));
		
//5) Click sort By and select customer top rated
		driver.findElementByXPath("//span[@class='pull-left']").click();
		driver.findElementByXPath("//span[text()='customer top rated']").click();
		Thread.sleep(3000);
		
//6) Click Category and click Shampoo
		driver.findElementByXPath("//div[text()='Category']").click();
		driver.findElementByXPath("//label[@for='chk_Shampoo_undefined']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("(//label[@for='chk_Shampoo_undefined'])[2]").click();
		Thread.sleep(3000);
		
//7) check whether the Filter is applied with Shampoo
		String text = driver.findElementByXPath("//ul[@class='pull-left applied-filter-lists']").getText();
		if(text.contains("Shampoo")) {
			System.out.println("Filter is applied");
		}
		
//8) Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElementByXPath("//h2[contains(@title,'Oreal Paris Colour Protect Shampoo')]").click();
		
//9) GO to the new window and select size as 175ml
		Set<String> wH1 = driver.getWindowHandles();
		List<String> l1 = new ArrayList<>(wH1);
		driver.switchTo().window(l1.get(2));
		driver.findElementByXPath("(//span[@class='size-pallets'])[2]").click();

//10) Print the MRP of the product
		System.out.println("MRP of the product: "+driver.findElementByXPath("//span[@class='post-card__content-price-offer']").getText().replaceAll("\\D", ""));
		
//11) Click on ADD to BAG
		driver.findElementByXPath("//button[@class='combo-add-to-btn prdt-des-btn js--toggle-sbag nk-btn nk-btn-rnd atc-simple m-content__product-list__cart-btn  ']").click();
		
//12) Go to Shopping Bag 
		driver.findElementByClassName("AddBagIcon").click();
		
//13) Print the Grand Total amount
		System.out.println("Grand Total Price: "+driver.findElementByXPath("//div[@class='value medium-strong']").getText().replaceAll("\\D", ""));
		
//14) Click Proceed
		driver.findElementByXPath("//button[@class='btn full fill no-radius proceed ']").click();		
		
//15) Click on Continue as Guest
		driver.findElementByXPath("//button[@class='btn full big']").click();
		
//16) Print the warning message (delay in shipment)
		System.out.println("Warning Message: "+driver.findElementByXPath("//div[@class='message']").getText());	

//17) Close all windows
		driver.quit();
	}

}
