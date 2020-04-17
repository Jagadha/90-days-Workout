package day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Makemytrip {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);

//1) Go to https://www.makemytrip.com/
		driver.navigate().to("https://www.makemytrip.com/");
		
//2) Click Hotels
		driver.findElementByXPath("//span[@class='chNavIcon appendBottom2 chSprite chHotels']").click();

//3) Enter city as Goa, and choose Goa, India
		driver.findElementById("city").click();
		WebElement city = driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']");
		city.sendKeys("Goa");
		Thread.sleep(2000);
		city.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		
//4) Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementById("checkin").click();
		WebElement month = driver.findElementByXPath("(//div[@class='DayPicker-Month'])[2]");
		month.findElement(By.xpath("(//div[text()='15'])[2]")).click();
		String text = month.findElement(By.xpath("(//div[text()='15'])[2]")).getText();
		int currdate = Integer.parseInt(text);
		
		driver.findElementById("checkout").click();
		WebElement currmonth = driver.findElementByXPath("(//div[@class='DayPicker-Month'])[2]");
		currmonth.findElement(By.xpath("(//div[text()='"+(currdate+5)+"'])[2]")).click();
		
//5) Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply
		driver.findElementById("guest").click();
		driver.findElementByXPath("//li[@data-cy='adults-2']").click();
		driver.findElementByXPath("//li[@data-cy='children-1']").click();

		Select age = new Select(driver.findElementByXPath("//select[@data-cy='childAge-0']"));
		age.selectByVisibleText("12");
		driver.findElementByXPath("//button[@data-cy='submitGuest']").click();
		
//6) Click Search button
		driver.findElementById("hsw_search_button").click();
		Thread.sleep(5000);
		if(driver.findElementByXPath("//body[@class='bodyFixed overlayWholeBlack']").isDisplayed()) {
			driver.findElementByXPath("//body[@class='bodyFixed overlayWholeBlack']").click();
		}
		
		driver.switchTo().frame(driver.findElementById("webklipper-publisher-widget-container-notification-frame"));
		driver.findElementByClassName("we_forward").click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Baga']")));
		
//7) Select locality as Baga
		driver.findElementByXPath("//label[text()='Baga']").click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='5 Star']")));
		
//8) Select 5 start in Star Category under Select Filters
		driver.findElementByXPath("//label[text()='5 Star']").click();
		Thread.sleep(5000);
		
//9) Click on the first resulting hotel and go to the new window
		driver.findElementByXPath("//div[@class='makeFlex spaceBetween']//p").click();
		Set<String> wH = driver.getWindowHandles();
		List<String> l = new ArrayList<>(wH);
		driver.switchTo().window(l.get(1));

//10) Print the Hotel Name 
		System.out.println("Hotel Name: "+driver.findElementByXPath("//h1[@class='txtHeading font22 latoBlack blackText']").getText());
		
//11) Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("//span[@class='latoBold font10 blueText pointer']").click();
		driver.findElementByXPath("//span[@class='latoBold font12 pointer makeFlex hrtlCenter right blueText']").click();
		driver.findElementByClassName("close").click();

//12) Click on BOOK THIS NOW
		driver.findElementById("detpg_headerright_book_now").click();
		Thread.sleep(3000);
		WebElement modal = driver.findElementByXPath("//div[@class='_Modal modalCont']");
		if(modal.isDisplayed()) {
			driver.findElementByClassName("close").click();
		}

//13) Print the Total Payable amount
		System.out.println("Total Payable Amount: "+driver.findElementById("revpg_total_payable_amt").getText().replaceAll("[a-zA-Z]", ""));

//14) Close the browser
		driver.close();
		driver.switchTo().window(l.get(0)).close();
	}

}
