package automationFramework;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FacebookPostScrapper {
    // chc0720@gmail.com / 01111@@@
	public static String fbID = "chc0720@gmail.com";
    public static String fbPW = "01111@@@"; 
             
    public static int maxCount = 50; //gathering 'Like' count for each ID
    public static ArrayList<String> fbIDList = new ArrayList<String>();
     
    public static void main(String[] args) {       
         
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\GCYang\\git\\seleniumPj\\TestFirst\\exe\\chromedriver.exe"); //path for chromedriver
        WebDriver _driver = new ChromeDriver();
        _driver.get("https://www.facebook.com/");
                 
        WebElement loginID = _driver.findElement(By.id("email"));      
        WebElement loginPassword = _driver.findElement(By.id("pass"));
         
        loginID.sendKeys(fbID);
        loginPassword.sendKeys(fbPW);
        loginPassword.submit();    
         
        //수집하고자 하는 담벼락 id
        fbIDList.add("gimpocity.kr");
         
                                 
        for(String fbid : fbIDList) {          
            startScrap(_driver, fbid);         
        }
                             
        System.out.println("드라이버 종료");
        _driver.close();
    }
     
    public static void startScrap(WebDriver _driver, String fbid) {
        _driver.get("https://www.facebook.com/" + fbid);
         
        JavascriptExecutor jse = (JavascriptExecutor) _driver;
        jse.executeScript("scrollBy(0,2500);");    
        jse.executeScript("scrollBy(0,2500);");    
 
        int tryCount = 0;
        int allCount = 0;
        int retCount = 0;
         
        ArrayList<String> likeIDList = new ArrayList<String>();
         
        while (true) {
            retCount = getPosts(_driver, fbid, allCount, likeIDList);
            if (retCount == 0) {
                tryCount++;
                System.out.println("tryCount :" + tryCount);
                 
                if (tryCount >= 5) //더이상 새로운 post가 없음
                    break;
            } else {
                tryCount = 0;
            }
 
            allCount += retCount;                          
            System.out.println("allCount :" + allCount);
             
            if(allCount >= maxCount) break;
             
            jse.executeScript("scrollBy(0,2500);");                
            jse.executeScript("scrollBy(0,2500);");
        }
    }
 
    public static void writeLikecount(String fbid, String likecount) {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter("out.txt", true));
            String s = fbid + ":" + likecount;
            out.write(s);
            out.newLine();                     
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
    }
 
    public static int getPosts(WebDriver _driver, String fbid, int currentCount, ArrayList<String> likeIDList) {
        int count = 0;
        List<WebElement> posts = null;           
 
        try {
            posts = _driver.findElements(By.cssSelector("span[data-tooltip-uri^='/ufi/reaction/tooltip/?ft_ent_identifier']"));        
 
            for (WebElement post : posts) {        
                String likeAttribute = post.getAttribute("data-tooltip-uri");
 
                //if (likeAttribute != null && likeAttribute.contains("/ufi/reaction/tooltip/?ft_ent_identifier=")) {
                    if (likeIDList.contains(likeAttribute) == false) {
                        String likeCount = post.getText();
                        if (likeCount != null) {
                            System.out.println("==> Like Count:  " + likeCount);                                                                            
                            count++;                                                                               
                            writeLikecount(fbid, likeCount);
                             
                            if ((currentCount + count) >= maxCount) break;
                        }
                        likeIDList.add(likeAttribute);
                    }
                //}
            }
        }
        catch (StaleElementReferenceException e) {
            System.out.println("EXCEPTION");       
        }
 
        return count;
    }
}
