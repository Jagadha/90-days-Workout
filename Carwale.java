package day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class Carwale {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver =  new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		driver.get("https://www.carwale.com/");
		
		driver.findElementByXPath("//li[@data-tabs='usedCars']").click();
		
		driver.findElementById("usedCarsList").sendKeys("Chennai");
		Thread.sleep(3000);
		driver.findElementById("usedCarsList").sendKeys(Keys.ENTER);
		
		driver.findElementByXPath("//li[text()='8 Lakh']").click();
		driver.findElementByXPath("(//li[text()='12 Lakh'])[2]").click();
		driver.findElementById("btnFindCar").click();
		
		driver.findElementByXPath("//div[@class='globalLocBlackOut']").click();
		
		WebElement city = driver.findElementById("drpCity");
		Select dropcity = new Select(city);
		dropcity.selectByValue("176");

		driver.findElementByName("CarsWithPhotos").click();
		Thread.sleep(5000);
		js.executeScript("window.scrollBy(0,1000);");
		driver.findElementByXPath("(//li[@data-manufacture-en='Hyundai']//span)[1]").click();
		Thread.sleep(3000); js.executeScript("window.scrollBy(0,1000);");
		driver.findElementByXPath("//span[text()='Creta']").click();
		Thread.sleep(3000);
		js.executeScript("arguments[0].scrollIntoView();", driver.findElementByName("fuel"));
		driver.findElementByName("fuel").click();
		driver.findElementByXPath("//span[text()='Petrol']").click();
		Thread.sleep(5000);
		
		WebElement bestmatch = driver.findElementById("sort");
		Select sortcars = new Select(bestmatch);
		sortcars.selectByVisibleText("KM: Low to High");
		
		String s = null; int ik =0;
		List<WebElement> listkms = driver.findElementsByXPath("//span[@class='slkms vehicle-data__item']");
		
		List<Integer> l = new ArrayList<>();
		List<Integer> sl = new ArrayList<>();
		for (int i = 0; i < listkms.size(); i++) {
			s = listkms.get(i).getText().replaceAll("\\D", "");
			ik = Integer.parseInt(s);
			l.add(ik);
		}
		Collections.sort(l);
		sl.addAll(l);
		if(sl==l) {
			System.out.println("Car list is sorted as per the kms");
		}
		else {
				System.out.println("Car list is not sorted as per the kms");
		}
//10) Add the least KM ran car to Wishlist  		
		for (int i = 0; i < listkms.size(); i++) {
			if(Integer.parseInt(listkms.get(i).getText().replaceAll("\\D", ""))==sl.get(0)) {
				js.executeScript("window.scrollBy(0,1000);");
				driver.findElementByXPath("(//span[@class='shortlist-icon--inactive shortlist'])["+(i+1)+"]").click();
			}
		}
//11) Go to Wishlist and Click on More Details  		
		driver.findElementByXPath("//li[@class='action-box shortlistBtn']").click();
		driver.findElementByLinkText("More details »").click();
		
//12) Print all the details under Overview in the Same way as displayed in application 
		Set<String> wH = driver.getWindowHandles();
		List<String> lwH = new ArrayList<>(wH);
		driver.switchTo().window(lwH.get(1));
		List<WebElement> light = driver.findElementsByXPath("//div[@id='overview']//ul//div[@class='equal-width text-light-grey']");
		List<WebElement> dark = driver.findElementsByXPath("//div[@id='overview']//ul//div[@class='equal-width dark-text']");
		Map<String, String> m = new LinkedHashMap<>();

		for (int j = 0; j < light.size(); j++) {
			String desc = light.get(j).getText();
			String val = dark.get(j).getText();
			m.put(desc, val);
		}
		
		for (Entry<String, String> eachm : m.entrySet()) {
			
			System.out.println(eachm.getKey()+"\t\t\t"+eachm.getValue());
			
		}
		
//13) Close the browser.		
		driver.quit();

	}

}
