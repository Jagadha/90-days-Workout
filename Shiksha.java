package day13;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Shiksha {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver =  new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//1) Go to https://studyabroad.shiksha.com/		
		driver.get("https://studyabroad.shiksha.com/");
//2) Mouse over on Colleges and click MS in Computer Science &Engg under MS Colleges		
		WebElement coll = driver.findElementByXPath("//label[text()='Colleges ']");
		Actions builder = new Actions(driver);
		builder.moveToElement(coll).perform();
		WebElement MS = driver.findElementByLinkText("MS in Computer Science &Engg");
		builder.click(MS).perform();
//3) Select GRE under Exam Accepted and Score 300 & Below 			
		driver.findElementByXPath("//label[@for='exam-0']//span[1]").click();
		Thread.sleep(3000);		
		WebElement score = driver.findElementByXPath("//select[@class='score-select-field']");
		Select sco = new Select(score);
		sco.selectByVisibleText("300 & below");
		Thread.sleep(3000);
//4) Max 10 Lakhs under 1st year Total fees, USA under countries
		driver.findElementByXPath("//label[@for='fee-0']/span").click();
		Thread.sleep(3000);		
		driver.findElementByXPath("//label[@for='country-3']/span").click();
		Thread.sleep(3000);
//5) Select Sort By: Low to high 1st year total fees
		WebElement sorter = driver.findElementById("categorySorter");
		Select sort = new Select(sorter);
		sort.selectByVisibleText("Low to high 1st year total fees");
		Thread.sleep(3000);
//6) Click Add to compare of the College having least fees with Public University, Scholarship and Accomadation facilities
		List<WebElement> PU = driver.findElementsByXPath("//p[text()='Public university']/span");
		List<WebElement> Scholar = driver.findElementsByXPath("//p[text()='Scholarship']/span");
		List<WebElement> Accomodation = driver.findElementsByXPath("//p[text()='Accommodation']/span");

		for (int j = 0; j < PU.size(); j++) {
			if((PU.get(j).getAttribute("class").equals("tick-mark"))&&(Scholar.get(j).getAttribute("class").equals("tick-mark"))&&(Accomodation.get(j).getAttribute("class").equals("tick-mark"))) {
				System.out.println("Condition Matched");
				driver.findElementByXPath("(//label[contains(@class,'compareCheckboxLabel')]/span)["+(j+1)+"]").click();
				break;
			}
		}
//7) Select the first college under Compare with similar colleges 	
		driver.findElementByXPath("(//a[@class='add-tag-title'])[1]").click();
//8) Click on Compare College>	
		driver.findElementByXPath("//strong[text()='Compare Colleges >']").click();
//9) Select When to Study as 2021
		driver.findElementByXPath("//label[@for='year1']/span").click();
//10) Select Preferred Countries as USA
		driver.findElementByXPath("//div[text()='Preferred Countries']/following-sibling::div").click();
		driver.findElementByXPath("//label[starts-with(@for,'USA')]/span").click();
		driver.findElementByLinkText("ok").click();
//11) Select Level of Study as Masters
		driver.findElementByXPath("//label[contains(@for,'Masters')]/span").click();
//12) Select Preferred Course as MS
		driver.findElementByXPath("//div[text()='Preferred Course']/following-sibling::div").click();
		driver.findElementByXPath("//li[text()='MS']").click();
		Thread.sleep(2000);
//13) Select Specialization as "Computer Science & Engineering"
		driver.findElementByXPath("//div[text()='Preferred Specialisations']/following-sibling::div").click();
		driver.findElementByXPath("//li[text()='Computer Science & Engineering']").click();
//14) Click on Sign Up
		driver.findElementById("signup").click();
//15) Print all the warning messages displayed on the screen for missed mandatory fields
		List<WebElement> errors = driver.findElementsByXPath("//div[@class='helper-text']");
		System.out.println("Warning messages:");
		for (int i = 0; i < errors.size(); i++) {
			if(errors.get(i).getText().contains("Please")) {
				System.out.println(errors.get(i).getText());
			}
		}
				
	}

}
