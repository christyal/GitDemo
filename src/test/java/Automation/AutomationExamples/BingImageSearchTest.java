package Automation.AutomationExamples;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BingImageSearchTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void searchAndClickImage() throws InterruptedException {
        try {
            driver.get("https://www.bing.com/");
            Thread.sleep(7000);

            // Search input
            WebElement searchBox = driver.findElement(By.name("q"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='sunset';", searchBox);
            //Thread.sleep(1000);
            searchBox.submit();

            // Click on Images tab
            Thread.sleep(3000);
            WebElement imageTab = driver.findElement(By.xpath("//a[contains(text(),'Images')]"));
            js.executeScript("arguments[0].click();", imageTab);

            // Wait and click first image
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

         // Click on 'Images' tab
         WebElement imagesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Images']")));
         js.executeScript("arguments[0].click();", imagesTab);

         // Wait for first image
         WebElement firstImage = wait.until(ExpectedConditions.presenceOfElementLocated(
             By.xpath("(//div[contains(@class,'img_cont')]//img)[1]")
         ));

         // Scroll and click
         js.executeScript("arguments[0].scrollIntoView(true);", firstImage);
         js.executeScript("arguments[0].click();", firstImage);


            // ✅ Assert something to ensure test passes
            WebElement imageViewer = driver.findElement(By.xpath("//div[@id='OverlayIFrameContainer' or @class='imgContainer']"));
            Assert.assertTrue(imageViewer.isDisplayed(), "Image Viewer should be displayed");

            System.out.println("Test Passed: Image opened successfully.");
        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
