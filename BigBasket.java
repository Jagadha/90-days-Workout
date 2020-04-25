package day6;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BigBasket {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chrome 2.38/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String ricetype="Ponni Boiled Rice - Super Premium";
		String kilo = "5 kg";
//1) Go to https://www.bigbasket.com/
		driver.get("https://www.bigbasket.com/");
//Setting location
		driver.findElementByXPath("//input[@value='Change Location']").click();
		driver.findElementByXPath("//button[@qa='continueBtn']").click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='dropdown full-wid hvr-drop']")));

//2) mouse over on  Shop by Category 		
		WebElement category = driver.findElementByXPath("//li[@class='dropdown full-wid hvr-drop']");
		WebElement foodgrains = driver.findElementByXPath("(//li[@data-submenu-id='foodgrains-oil-masala'])[2]");
		
		Actions builder = new Actions(driver);
		builder.moveToElement(category).perform();
		Thread.sleep(3000);
//3)Go to FOODGRAINS, OIL & MASALA --> RICE & RICE PRODUCTS 
		builder.moveToElement(foodgrains).perform();
		Thread.sleep(3000);
		driver.findElementByLinkText("Rice & Rice Products").click();
//4) Click on Boiled & Steam Rice		
		driver.findElementByXPath("//span[text()='Boiled & Steam Rice']//parent::div").click();
		Thread.sleep(2000);
//5) Choose the Brand as bb Royal
		driver.findElementByXPath("//span[text()='bb Royal']//parent::label").click();
		Thread.sleep(3000);

//6) Go to Ponni Boiled Rice - Super Premium and select 5kg bag from Dropdown
//7) print the price of Rice
//8) Click Add button
//9) Verify the success message displayed 	
		
		List<WebElement> pdtnames = driver.findElementsByXPath("//div[@qa='product_name']");
		for (int i =0; i<pdtnames.size()-1;i++) {
			if(pdtnames.get(i).getText().contains(ricetype)){
				driver.findElementByXPath("(//button[@class='btn btn-default dropdown-toggle form-control'])["+(i+1)+"]").click();
				driver.findElementByXPath("(//span[text()='5 kg']//parent::a)["+(i+1)+"]").click();
				String ricepri = driver.findElementByXPath("(//span[@class='discnt-price'])["+(i+1)+"]").getText().replaceAll("\\D", "");
				System.out.println("Price of the rice: "+ Integer.parseInt(ricepri));
				driver.findElementByXPath("(//span[@class='bskt-icon'])["+(i+1)+"]").click();
				Thread.sleep(3000);
		
				String text = driver.findElementByXPath("//div[@class='toast-title']").getText();
				if(text.contains("Successfully added "+ricetype+" "+kilo+" to the basket")) {
					System.out.println("Verified the success message");
				}
			}
		}

//10) Type Dal in Search field and enter
		driver.findElementByXPath("//input[@qa='searchBar']").sendKeys("Dal",Keys.ENTER);
		Thread.sleep(3000);
		List<WebElement> dalnames = driver.findElementsByXPath("//a[@ng-bind='vm.selectedProduct.p_desc']");
		for(int i=0;i<dalnames.size()-1;i++) {
			if(dalnames.get(i).getText().contains("Toor/Arhar Dal/")) {
				driver.findElementByXPath("(//button[@class='btn btn-default dropdown-toggle form-control'])["+i+"]").click();
				driver.findElementByXPath("//span[contains(text(),'2 kg')]//parent::a").click();
				driver.findElementByXPath("(//input[@ng-model='vm.startQuantity'])["+(i+1)+"]").click();
				driver.findElementByXPath("(//input[@ng-model='vm.startQuantity'])["+(i+1)+"]").sendKeys(Keys.DELETE);
				Thread.sleep(2000);
				driver.findElementByXPath("(//input[@ng-model='vm.startQuantity'])["+(i+1)+"]").sendKeys("2");
				String dalpri = driver.findElementByXPath("(//span[@class='discnt-price'])["+(i+1)+"]").getText().replaceAll("\\D", "");
				System.out.println("Price of the Dal: "+ Integer.parseInt(dalpri));
				driver.findElementByXPath("(//span[@class='bskt-icon'])["+(i+1)+"]").click();
			}
		}
		driver.findElementByXPath("//button[@class='toast-close-button']").click();
		Thread.sleep(5000);

//15) Mouse hover on My Basket
		WebElement cart = driver.findElementByXPath("//span[@title='Your Basket']");
		builder.moveToElement(cart).perform();
		Thread.sleep(3000);

//16) Validate the Sub Total displayed for the selected items
		String str1 = driver.findElementByXPath("(//div[@ng-bind='cartItem.quantity_mrp'])[1]").getText();
		String str2 = driver.findElementByXPath("(//div[@ng-bind='cartItem.quantity_mrp'])[2]").getText();
		double qty1 = Double.parseDouble(str1.substring(0, 1)); 
		double pri1 = Double.parseDouble(str1.substring(4));
		double qty2 = Double.parseDouble(str2.substring(0, 1));
		double pri2 = Double.parseDouble(str2.substring(4));
		double sub = (qty1*pri1) + (qty2*pri2);
		System.out.println("Sub Total: "+sub);
		String text = driver.findElementByXPath("//span[@ng-bind='vm.cart.cart_total']").getText();
		if(sub==Double.parseDouble(text)) {
			System.out.println("Validated the Subtotal");
		}
		
//17) Reduce the Quantity of Dal as 1 
		driver.findElementByXPath("(//button[@qa='decQtyMB'])[2]").click();
		Thread.sleep(3000);
		
//18) Validate the Sub Total for the current items
		String currstr2 = driver.findElementByXPath("(//div[@ng-bind='cartItem.quantity_mrp'])[2]").getText();
		double currqty2 = Double.parseDouble(currstr2.substring(0, 1));
		double currsub = (qty1*pri1) + (currqty2*pri2);
		System.out.println("Current Sub Total after modification: "+currsub);
		text = driver.findElementByXPath("//span[@ng-bind='vm.cart.cart_total']").getText();
		if(currsub==Double.parseDouble(text)) {
			System.out.println("Validated the current sub Total");
		}
		
//19) Close the Browser
		driver.close();
	}

}
