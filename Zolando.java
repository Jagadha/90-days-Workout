package day14;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Zolando {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
//1) Go to https://www.zalando.com/ 
		driver.navigate().to("https://www.zalando.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//2) Get the Alert text and print it 		
		Thread.sleep(5000);
		Alert a = driver.switchTo().alert();
		System.out.println(a.getText());
//3) Close the Alert box and click on Zalando.uk 
		a.accept();
		
		driver.findElementByXPath("//a[contains(@href,'uk')]").click();
//4) Click Women--> Clothing and click Coat  
		driver.findElementByXPath("//span[text()='Women']").click();
		
		WebElement clothing = driver.findElementByXPath("//span[text()='Clothing']");
		Actions builder = new Actions(driver);
		builder.moveToElement(clothing).perform();
		driver.findElementByXPath("//span[text()='Coats']").click();

//5) Choose Material as cotton (100%) and Length as thigh-length 
		Thread.sleep(5000);
		driver.findElementById("uc-btn-accept-banner").click();
		driver.findElementByXPath("//span[text()='Material']").click();
		driver.findElementByXPath("//span[text()='cotton (100%)']").click();
		driver.findElementByXPath("//button[text()='Save']").click();

		Thread.sleep(5000);
		driver.findElementByXPath("//span[text()='Length']").click();
		driver.findElementByXPath("//span[text()='thigh-length']").click();
		driver.findElementByXPath("//button[text()='Save']").click();
		Thread.sleep(5000);
		
//6) Click on Q/S designed by MANTEL - Parka coat 
		List<WebElement> brna = driver.findElementsByXPath("//div[contains(@class,'cat_brandName')]");
		List<WebElement> arna = driver.findElementsByXPath("//div[contains(@class,'cat_articleName')]");
		for (int i = 0; i < brna.size(); i++) {
			if((brna.get(i).getText().equals("Q/S designed by"))&&(arna.get(i).getText().contains("MANTEL - Parka"))) {
				brna.get(i).click();
				break;
			}
		}
//7) Check the availability for Color as Olive and Size as 'M' 		
		driver.findElementByXPath("(//img[@alt='olive'])[2]").click();
		Thread.sleep(3000);
//8) If the previous preference is not available, check  availability for Color Navy and Size 'M' 
		if(driver.findElementByXPath("//h2[text()='Out of stock']").isDisplayed()) {
			driver.findElementByXPath("(//img[@alt='navy'])[2]").click();
			Thread.sleep(3000);
		}
		driver.findElementByXPath("//span[text()='Choose your size']").click();
		driver.findElementByXPath("//span[text()='M']").click();
//9) Add to bag only if Standard Delivery is free 		
		String str = driver.findElementByXPath("//span[text()='Standard delivery']//following-sibling::div//button/span").getText();
		if(str.equals("Free")) {
			driver.findElementByXPath("//span[text()='Add to bag']").click();
			Thread.sleep(5000);
		}
//10) Mouse over on Your Bag and Click on "Go to Bag" 		
		WebElement cart = driver.findElementByXPath("//a[@class='z-navicat-header_navToolItemLink']");
		builder.moveToElement(cart).perform();
		driver.findElementByXPath("//a[@href='/cart']").click();
//11) Capture the Estimated Deliver Date and print 
		System.out.println(driver.findElementByXPath("//div[@data-id='delivery-estimation']/span").getText());
//12) Mouse over on FREE DELIVERY & RETURNS*, get the tool tip text and print 
		WebElement freedeli = driver.findElementByXPath("//a[contains(@href,'Do-delivery-and-returns')]");
		builder.moveToElement(freedeli).perform();
		System.out.println(driver.findElementByXPath("//a[text()='Free delivery & returns*']/parent::span").getAttribute("title"));
//13) Click on FREE DELIVERY & RETURNS		
		driver.findElementByXPath("//a[contains(@href,'Do-delivery-and-returns')]").click();
//14) Click on Start chat in the Start chat and go to the new window 
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()='Start chat']").click();
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));
//15) Enter you first name and a dummy email and click Start Chat 
		driver.findElementById("prechat_customer_name_id").sendKeys("Dora");
		driver.findElementById("prechat_customer_email_id").sendKeys("dora#234@gmail.com");
		driver.findElementByXPath("//span[text()='Start Chat']").click();
		Thread.sleep(4000);

//16) Type Hi, click Send and print the reply message and close the chat window
		driver.findElementById("liveAgentChatTextArea").sendKeys("Hi");
		driver.findElementByXPath("//button[text()='Send']").click();
		Thread.sleep(2000);
		System.out.println(driver.findElementByXPath("(//span[@class='operator']//span[@class='messageText'])[3]").getText());
		driver.close();
	}

}
