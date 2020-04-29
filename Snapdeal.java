package day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Snapdeal {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://www.snapdeal.com/");
//‎2) Mouse over on Toys, Kids' Fashion & more and click on Toys
		WebElement Toys = driver.findElementByXPath("//div[@id='leftNavMenuRevamp']/div[1]/div[2]/ul[1]/li[9]/a[1]/span[1]");		
		Actions builder = new Actions(driver);
		builder.moveToElement(Toys).click().perform();
		driver.findElementByXPath("//span[text()='Toys']").click();
//3) Click Educational Toys in Toys & Games ‎
		driver.findElementByXPath("//div[text()='Educational Toys']").click();
		Thread.sleep(3000);
//4) Click the Customer Rating 4 star and Up  
		driver.findElementByXPath("//label[@for='avgRating-4.0']").click();
		Thread.sleep(3000);
//5) Click the offer as 40-50 
		driver.findElementByXPath("//label[@for='discount-40%20-%2050']").click();
		Thread.sleep(3000);
//6) Check the availability for the pincode
		driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("600044");
		driver.findElementByXPath("//button[text()='Check']").click();
		Thread.sleep(3000);
//7) Click the Quick View of the first product  
		WebElement firstpdt = driver.findElementByXPath("//img[@class='product-image wooble']");
		builder.moveToElement(firstpdt).perform();
		driver.findElementByXPath("//div[text()[normalize-space()='Quick View']]").click();
//8) Click on View Details 
		driver.findElementByXPath("(//a[contains(@class,'btn btn-theme-secondary')])[1]").click();
//9) Capture the Price of the Product and Delivery Charge. Click 'Add to Cart'
		double pdtpri = Double.parseDouble(driver.findElementByXPath("//span[@itemprop='price']").getText());
		double delcharge = Double.parseDouble(driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText().replaceAll("\\D", ""));
		double item1 = pdtpri+delcharge;
		driver.findElementByXPath("//span[text()='add to cart']").click();
//10) Validate the You Pay amount matches the sum of (price+deliver charge) 
		double totalitem1 = Double.parseDouble(driver.findElementByXPath("//div[@class='you-pay']/span").getText().replaceAll("\\D", ""));
		
		if(item1==totalitem1) {
			System.out.println("Validated the amount");
		}
		
//11) Search for Sanitizer 
		driver.findElementById("inputValEnter").sendKeys("Sanitizer",Keys.ENTER);
//12) Click on Product "BioAyurveda Neem Power Hand Sanitizer" 
		driver.findElementByXPath("//p[contains(@title,'BioAyurveda Neem Power  Hand Sanitizer')]").click();
//13) Capture the Price and Delivery Charge 
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));
		
		double sanipri = Double.parseDouble(driver.findElementByXPath("//span[@itemprop='price']").getText());
		double delicha = Double.parseDouble(driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText().replaceAll("\\D", ""));
		double item2 = sanipri+delicha;
//14) Click on Add to Cart 
		driver.findElementByXPath("//span[text()='ADD TO CART']").click();
//15) Click on Cart 
		Thread.sleep(3000);
		driver.findElementByXPath("//span[@class='cartTextSpan']//following-sibling::i").click();
//16) Validate the Proceed to Pay matches the total amount of both the products 
		double finaltotal = Double.parseDouble(driver.findElementByXPath("//input[@type='button']").getAttribute("value").replaceAll("\\D", ""));
		System.out.println("Final Total: "+finaltotal);
		System.out.println("Total of two products: "+(item1+item2));
		if((item1+item2)==finaltotal) {
			System.out.println("Valiadated the total amount");
		}
//17) Close all the windows
		driver.quit();
	}

}
