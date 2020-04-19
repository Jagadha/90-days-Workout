package day4;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HP {

	public static ChromeDriver driver;
	public static WebDriverWait wait;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);

//1) Go to https://store.hp.com/in-en/
		driver.get("https://store.hp.com/in-en/");
		Thread.sleep(3000);
		frpopup();
		cookies();

//2) Mouse over on Laptops menu and click on Pavilion
		WebElement laptops = driver.findElementByXPath("//span[text()='Laptops']");
		Actions builder = new Actions(driver);
		builder.moveToElement(laptops).perform();
		driver.findElementByXPath("//span[text()='Pavilion Gaming']").click();
		Thread.sleep(5000);

//3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		adpopup();
		cookies();
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click();
		driver.findElementByXPath("//span[text()='Intel Core i7']").click();
		Thread.sleep(3000);

//4) Hard Drive Capacity -->More than 1TB
		driver.findElementByXPath("//span[text()='More than 1 TB']").click();
		Thread.sleep(3000);

//5) Select Sort By: Price: Low to High
		Select price = new Select(driver.findElementById("sorter"));
		price.selectByValue("price_asc");
		Thread.sleep(3000);

//6) Print the First resulting Product Name and Price
		List<WebElement> pdtna = driver.findElementsByXPath("//a[@class='product-item-link']");
		System.out.println("Product name: " + pdtna.get(0).getText());
		List<WebElement> pdtprice = driver.findElementsByXPath("//span[contains(@id,'product-price')]");
		String pdtpri = pdtprice.get(2).getText().replaceAll("\\D", "");
		int pri = Integer.parseInt(pdtpri);
		System.out.println("Price of the product: " + pri);

//7) Click on Add to Cart
		driver.findElementByXPath("//span[text()='Add To Cart']").click();
		Thread.sleep(3000);

//8) Click on Shopping Cart icon --> Click on View and Edit Cart
		driver.findElementByXPath("//a[@class='action showcart']").click();
		driver.findElementByXPath("//a[@class='action primary viewcart']").click();

//9) Check the Shipping Option --> Check availability at Pincode
		driver.findElementByName("pincode").sendKeys("600044");
		driver.findElementByXPath("//button[@class='primary standard_delivery-button']").click();
		Thread.sleep(5000);

//10) Verify the order Total against the product price
		String text = driver.findElementByXPath("//td[@data-th='Order Total']").getText().replaceAll("\\D", "");
		int total = Integer.parseInt(text);
		if (pri == total) {
			System.out.println("Verified the total");

//11) Proceed to Checkout if Order Total and Product Price matches
			driver.findElementByXPath("//span[text()='Proceed to Checkout']").click();
		}

//12) Click on Place Order
		driver.findElementByXPath("//div[@class='place-order-primary']/button").click();

//13) Capture the Error message and Print
		System.out.println("Error message: " + driver.findElementByXPath("//div[@class='message notice']").getText());

//14) Close Browser
		driver.close();
	}

	public static void frpopup() {
		try {
			wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElementByXPath("//span[@class='optly-modal-close close-icon']")));
			if (driver.findElementById("ifr_popup").isDisplayed()) {
				driver.findElementByXPath("//span[@class='optly-modal-close close-icon']").click();
			}
		} catch (Exception e) {
		}
	}

	public static void cookies() {
		try {
			if (driver.findElementByClassName("optanon-alert-box-bg").isDisplayed()) {
				driver.findElementByXPath("//button[@class='optanon-alert-box-close banner-close-button']").click();
			}
		} catch (Exception e) {
		}
	}

	public static void adpopup() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='inside_notify_content']")));
			Thread.sleep(5000);
			if (driver.findElementByXPath("//div[@class='inside_notify_content']").isDisplayed()) {
				driver.findElementByXPath("//div[@class='inside_closeButton fonticon icon-hclose']").click();
			}
			if (driver.findElementByXPath("//div[@id='inside_chatPaneHeader']").isDisplayed()) {
				driver.findElementByXPath("//div[@title='Close Chat Pane']").click();
			}
		} catch (Exception e) {
		}
	}
}