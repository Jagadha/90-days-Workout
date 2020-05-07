package evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Day16_Ajio {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
//1) Go to https://www.ajio.com/shop/sale
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//2) Enter Bags in the Search field and Select Bags in Women Handbags		
		Thread.sleep(3000);
		driver.findElementByName("searchVal").sendKeys("Bags");
		Thread.sleep(2000);
		driver.findElementByXPath("//span[text()='Women Handbags']//parent::a").click();
		driver.findElementByXPath("//div[@class='five-grid']").click();
//3) Click on five grid and Select SORT BY as "What's New"		
		WebElement sort = driver.findElementByXPath("//div[@class='filter-dropdown']/select");
		Select s = new Select(sort);
		s.selectByVisibleText("What's New");
//4) Enter Price Range Min as 2000 and Max as 5000		
		Thread.sleep(3000);	
		driver.findElementByXPath("//span[text()='price']").click();
		driver.findElementByName("minPrice").sendKeys("2000");
		driver.findElementByName("maxPrice").sendKeys("5000",Keys.ENTER);
		Thread.sleep(5000);
//5) Click on the product "Puma Ferrari LS Shoulder Bag"		
		List<WebElement> names = driver.findElementsByXPath("//div[@class='brand']//following-sibling::div[1]");
		List<WebElement> br = driver.findElementsByXPath("//div[@class='brand']");
		for (int i = 0; i < names.size(); i++) {
			if((names.get(i).getText().equals("Ferrari LS Shoulder Bag"))&&(br.get(i).getText().equals("Puma"))) {
				names.get(i).click();
				break;
			}
		}
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));
//6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon		
		String price = driver.findElementByXPath("//div[@class='prod-sp']").getText().replaceAll("\\D", "");
		int pri = Integer.parseInt(price);
		String promodis = driver.findElementByXPath("//div[@class='promo-desc']").getText().replaceAll("\\D", "").substring(2);
		int promoVal = Integer.parseInt(promodis);
		int discount = Integer.parseInt(driver.findElementByXPath("//div[@class='promo-discounted-price']/span").getText().replaceAll("\\D", ""));
		long discountedprice = 0;
		if(pri>=promoVal) {
			discountedprice = pri-discount;
		}
//7) Check the availability of the product for pincode 560043, print the expected delivery date if it is available		
		driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
		driver.findElementByName("pincode").sendKeys("635001");
		driver.findElementByXPath("//button[text()='CONFIRM PINCODE']").click();

		if(driver.findElementByXPath("//div[@class='edd-message-success']/div").isDisplayed()) {
			System.out.println("Expected Delivery: "+driver.findElementByXPath("//ul[@class='edd-message-success-details']//span").getText());
		}else {
			System.out.println("Currently not delivered in this area");
		}
		String useCode = driver.findElementByXPath("//div[@class='promo-title']").getAttribute("innerText");
//8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByXPath("//div[@class='other-info-toggle']").click();
		System.out.println(driver.findElementByXPath("//span[text()='Customer Care Address']//following-sibling::span[2]").getText());
//9) Click on ADD TO BAG and then GO TO BAG		
		driver.findElementByXPath("//div[@class='btn-gold']").click();
		Thread.sleep(10000);
	    driver.findElementByXPath("//span[@class='ic-pdp-add-cart']").click();
//10) Check the Order Total before apply coupon
//11) Enter Coupon Code and Click Apply	  
	    String total = driver.findElementByXPath("//span[@class='price-value bold-font']").getText().replaceAll("\\D", "");
	    if(total.contains(price)) {
	    	driver.findElementById("couponCodeInput").sendKeys(useCode.substring(8));
	    	driver.findElementByXPath("//button[text()='Apply']").click();
	   }
//12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
	   String[] split = driver.findElementByXPath("//span[text()='Coupon savings']//following-sibling::span").getText().split(" ");
	   int couponSavings = (int) Math.round(Double.parseDouble(split[1]));
	   Assert.assertEquals(couponSavings, discountedprice);	   
//13) Click on Delete and Delete the item from Bag	   
	   driver.findElementByXPath("//div[text()='Delete']").click();
	   driver.findElementByXPath("//div[text()='DELETE']").click();
//14) Close all the browsers	   
	   driver.quit();
	
	}

}
