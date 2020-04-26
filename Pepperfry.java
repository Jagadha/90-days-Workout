package day8;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Pepperfry {

	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
//1) Go to https://www.pepperfry.com/
		driver.get("https://www.pepperfry.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("regPopUp")));
		driver.findElementByXPath("//div[@id='regPopUp']/a").click();
//2) Mouseover on Furniture and click Office Chairs under Chairs			
		WebElement furniture = driver.findElementByXPath("//a[@rel='meta-furniture']");
		Actions builder = new Actions(driver);
		builder.moveToElement(furniture).perform();
	
		driver.findElementByLinkText("Office Chairs").click();
//3) click Executive Chairs
		driver.findElementByXPath("//h5[text()='Executive Chairs']").click();
//4) Change the minimum Height as 50 in under Dimensions
		driver.findElementByClassName("clipFilterDimensionHeightValue").sendKeys(Keys.DELETE);
		driver.findElementByClassName("clipFilterDimensionHeightValue").sendKeys(Keys.DELETE);
		Thread.sleep(3000);
		driver.findElementByClassName("clipFilterDimensionHeightValue").sendKeys("50",Keys.ENTER);
		Thread.sleep(3000);
//5) Add "Poise Executive Chair in Black Colour" chair to Wishlist
		driver.findElementByXPath("//a[@data-productname='Poise Executive Chair in Black Colour']").click();
//6) Mouseover on Homeware and Click Pressure Cookers under Cookware
		WebElement homeware = driver.findElementByXPath("//a[@rel='meta-homeware']");
		builder.moveToElement(homeware).perform();
		driver.findElementByLinkText("Pressure Cookers").click();
//7) Select Prestige as Brand		
		driver.findElementByXPath("//li[@data-search='Prestige']").click();
		Thread.sleep(3000);
//8) Select Capacity as 1-3 Ltr
		driver.findElementByXPath("//li[@data-search='1 Ltr - 3 Ltr']").click();
		Thread.sleep(5000);
//9) Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist		
		List<WebElement> cookers = driver.findElementsByXPath("//a[@class='clip-prd-dtl']");
		for(int i = 0; i < cookers.size(); i++) {
			if(cookers.get(i).getText().equals("Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr")) {
				driver.findElementByXPath("(//a[@data-tooltip='Add to Wishlist'])["+(i+1)+"]").click();
			}
		}
//10) Verify the number of items in Wishlist	
		String text = driver.findElementByXPath("//div[@class='wishlist_bar']/span").getText();
		if(text.equals("2")) {
			System.out.println("Verified the number of items in Wishlist");
		}
//11) Navigate to Wishlist
		driver.findElementByXPath("//div[@class='wishlist_bar']").click();
//12) Move Pressure Cooker only to Cart from Wishlist
		driver.findElementByXPath("//a[@data-tooltip='Compact view']").click();
		List<WebElement> wish = driver.findElementsByXPath("//p[@class='item_title pf-bold-txt']");
		for (int i = 0; i < wish.size(); i++) {
			if(wish.get(i).getText().contains("Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr")) {
				driver.findElementByXPath("(//a[@class='addtocart_icon'])["+(i+1)+"]").click();
			}
		}
//13) Check for the availability for Pincode		
		driver.findElementByXPath("//input[@class='srvc_pin_text']").sendKeys("600044");
		driver.findElementByLinkText("Check").click();
		Thread.sleep(3000);
//14) Click Proceed to Pay Securely
		driver.findElementByXPath("//div[@class='minicart_footer']/a").click();
//15 Click Proceed to Pay		
		driver.findElementByLinkText("PLACE ORDER").click();
//16) Capture the screenshot of the item under Order Item		
		driver.findElementByXPath("//span[text()='ORDER SUMMARY']").click();
		WebElement ssht = driver.findElementByXPath("//li[@class='onepge_ordersummary slick-slide slick-current slick-active']");
		File src = ssht.getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/order.jpg");
		FileUtils.copyFile(src, dest);
//17) Close the browser		
		driver.close();
	
	}

}
