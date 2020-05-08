package evaluation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Day17_MicrosoftAzure {
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		Map<String,Object> exc = new HashMap<>();
        exc.put("download.default_directory", "D:\\Downloads");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", exc);
        
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver,30);
		
//1) Go to https://azure.microsoft.com/en-in/
		driver.get("https://azure.microsoft.com/en-in/");
		
//2) Click on Pricing
		driver.findElementByLinkText("Pricing").click();
		
//3) Click on Pricing Calculator
		driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
		Thread.sleep(5000);
		
//4) Click on Containers
		js.executeScript("window.scrollBy(0,100);");
		driver.findElementByXPath("//button[@data-event-property='containers']").click();
		
//5) Select Container Instances(
		driver.findElementByXPath("(//span[@class='product']//span[text()='Container Instances'])[2]").click();

//6) Click on Container Instance Added View
		js.executeScript("window.scrollBy(0,1000);");
		Thread.sleep(3000);
		
//7) Select Region as "South India"
		WebElement region = driver.findElementByXPath("//select[@aria-label='Region']");
		Select re = new Select(region);
		re.selectByVisibleText("South India");
		
//8) Set the Duration as 180000 seconds
		driver.findElementByName("seconds").sendKeys(Keys.BACK_SPACE);
		driver.findElementByName("seconds").sendKeys(Keys.BACK_SPACE);
		Thread.sleep(3000);
		driver.findElementByName("seconds").sendKeys("180000");
		
//9) Select the Memory as 4GB	
		WebElement Memory = driver.findElementByName("memory");
		Select mem = new Select(Memory);
		mem.selectByVisibleText("4 GB");
		
//10) Enable SHOW DEV/TEST PRICING		
		driver.findElementByName("devTestSelected").click();
		
//11)Select Indian Rupees  as currency
		WebElement curr = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select c = new Select(curr);
		c.selectByValue("INR");
		
//12) Print the Estimated monthly price
		System.out.println("Estimated Monthly Cost: "+driver.findElementByXPath("(//span[@class='numeric']/span)[6]").getText().replaceAll("[^0-9.]", ""));

//13) Click on Export to download the estimate as excel
		driver.findElementByXPath("//button[text()='Export']").click();

//14) Verify the downloaded file in the local folder
		String expectedfile = "ExportedEstimate";
		File f = new File(exc.get("download.default_directory")+"\\"+expectedfile);
		Assert.assertEquals(f.getName(), expectedfile);
	
//15) Navigate to Example Scenarios and Select CI/CD for Containers
		WebElement example = driver.findElementByLinkText("Example Scenarios");
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		example.click();
		driver.findElementByXPath("//span[text()='CI/CD for Containers']").click();

//16) Click Add to Estimate
		js.executeScript("window.scrollBy(0,500);");
		Thread.sleep(5000);
		driver.findElementByXPath("//button[text()='Add to estimate']").click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='row column modal-header']//h4")));

//17) Change the Currency as Indian Rupee
		WebElement currency = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select indcurr = new Select(currency);
		indcurr.selectByValue("INR");
		
//18) Enable SHOW DEV/TEST PRICING		
		driver.findElementById("devtest-toggler").click();
		
//19) Export the Estimate
		driver.findElementByXPath("//button[text()='Export']").click();
		
//20) Verify the downloaded file in the local folder
		String expectedfile1 = "ExportedEstimate (1)";
		File f1 = new File(exc.get("download.default_directory")+"\\"+expectedfile1);
		Assert.assertEquals(f1.getName(), expectedfile1);

		driver.close();
	}

}
