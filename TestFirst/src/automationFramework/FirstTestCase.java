package automationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTestCase {

	public static void main(String[] args) {
		WebDriver driver = null;
		try {
			String exePath = "D:\\eclipse-workspace\\selenium\\TestFirst\\exe\\chromedriver.exe";

			System.setProperty("webdriver.chrome.driver", exePath);
			driver = new ChromeDriver();
			
	        //Launch the Online Store Website
			driver.get("http://www.gimpo.go.kr/mayor/main.do");
			WebElement facecon = driver.findElement(By.className("face-con"));
//			System.out.println(facecon.getText());
	        
//			driver.get("http://www.r18.com");

			
			JavascriptExecutor js; 
			if (driver instanceof JavascriptExecutor) { 
			    js = (JavascriptExecutor)driver; 
			    js.executeScript(
			    	     "var inputs = document.getElementsByClassName('face-con')[0].toString();" + 
			    	     "alert(inputs);");
			}
			
			
			// Print a Log In message to the screen
	        System.out.println("Successfully opened the website www.Store.Demoqa.com");

			//Wait for 5 Sec
			Thread.sleep(5000);
			
	        
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			// Close the driver
			driver.quit();

		}
    }
}
