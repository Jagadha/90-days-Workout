package day1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Myntra {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		
// 1) Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");
		
//2) Mouse over on WOMEN (Actions -> moveToElement
//3) Click Jackets & Coats
		WebElement hover = driver.findElementByXPath("//a[@href='/shop/women']");
		Actions builder = new Actions(driver);
		builder.moveToElement(hover).perform();
		//Thread.sleep(3000);
		driver.findElementByLinkText("Jackets & Coats").click();

//4) Find the total count of item (top)
		String text = driver.findElementByXPath("//span[@class='title-count']").getText();
		String count = text.replaceAll("[^0-9]", "");
		int totalcount = Integer.parseInt(count);
		
//5) Validate the sum of categories count matches
		String str = driver.findElementByXPath("//span[@class='categories-num']").getText();
		String jacket = str.replaceAll("[^0-9]", "");
		int jacketcount = Integer.parseInt(jacket);
		
		String str2 = driver.findElementByXPath("(//span[@class='categories-num'])[2]").getText();
		String coat = str2.replaceAll("[^0-9]", "");
		int coatcount = Integer.parseInt(coat);
		
		if(jacketcount+coatcount==totalcount) {
			System.out.println("Count matches");
		}
		
//6) Check Coats
		driver.findElementByXPath("//label[text()='Coats']").click();
		
//7) Click + More option under BRAND
		driver.findElementByClassName("brand-more").click();
		
//8) Type MANGO and click checkbox		
		driver.findElementByXPath("//input[@placeholder='Search brand']").sendKeys("Mango");
		driver.findElementByXPath("//label[@class=' common-customCheckbox']").click();
		
//9) Close the pop-up x
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();
		Thread.sleep(3000);
		
//10) Confirm all the Coats are of brand MANGO
		List<WebElement> product = driver.findElementsByXPath("//h3[@class='product-brand']");
		int mangocount=0;
		for (WebElement productlist : product) {
			if(productlist.getText().contentEquals("MANGO")) {
				mangocount=mangocount+1;
			}
		}
		if(mangocount==product.size()) {
			System.out.println("All coats are of brand MANGO");
		}
		
//11) Sort by Better Discount
		WebElement sort = driver.findElementByClassName("horizontal-filters-sortContainer");
		builder.moveToElement(sort).perform();
		Thread.sleep(2000);
		driver.findElementByXPath("(//label[@class='sort-label '])[3]").click();
		Thread.sleep(2000);

//12) Find the price of first displayed item
		List<WebElement> pricelist = driver.findElementsByXPath("//span[@class='product-discountedPrice']");
		WebElement price = pricelist.get(0);
		String p = price.getText().replaceAll("\\D", "");
		System.out.println("Price of the first displayed item "+ Integer.parseInt(p));
		
//13) Mouse over on size of the first item
		WebElement size = driver.findElementByXPath("//div[@class='product-productMetaInfo']");
		builder.moveToElement(size).perform();

//14) Click on WishList Now
		driver.findElementByXPath("//span[@class='product-actionsButton product-wishlist product-prelaunchBtn']").click();

//15) Close Browser
		driver.close();
		
	}

}
