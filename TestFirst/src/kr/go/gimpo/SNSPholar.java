package kr.go.gimpo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SNSPholar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SNSPholar sp = new SNSPholar();
		sp.dataSNSPholar();
	}

	public void dataSNSPholar() {
		try {
			// ũ�� ����̺� ����
			WebDriver driver = null;
			String exePath = "C:\\workspace\\selenium\\FirstPj\\exe\\chromedriver_win32\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", exePath);

			// ��巹��(â ����) ũ�� ���� 
			ChromeOptions chromeOptions = new ChromeOptions();
//		    chromeOptions.setBinary("/Applications/Google Chrome Canary.app/Contents/MacOS/Google Chrome Canary");
			chromeOptions.addArguments("headless");
			driver = new ChromeDriver(chromeOptions);

			// ù��° ���� 
			driver.get("https://www.pholar.co/my/913211/profile");

			// ��� ��ũ ���� ����
			List<WebElement> myElements = driver.findElements(By.className("list_wrap"));
			for (WebElement e : myElements) {
				// ����� ��ũ�� �̿��Ͽ� 
				// �ٽ� ����
				driver.get(e.getAttribute("href"));

				// ��Ͽ��� ���� ����
				List<WebElement> mymElements = driver.findElements(By.className("item_info_wrap"));
				for (WebElement ee : mymElements) {
					List<WebElement> mymElements2 = ee.findElements(By.className("item_data"));
					for (WebElement eee : mymElements2) {
						// �ش� �ؽ�Ʈ ����
						System.out.println(eee.getText());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
