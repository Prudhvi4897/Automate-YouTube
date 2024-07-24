package demo.wrappers;

//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import java.util.List;
import dev.failsafe.internal.util.Assert;
import dev.failsafe.internal.util.Durations;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;

import java.time.Duration;


public class Wrappers {

    public static boolean navigateToUrl(ChromeDriver driver, String uRL) {
        boolean status = false;
        try {
            driver.get(uRL);
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
            String pageUrl = driver.getCurrentUrl();
            if (pageUrl.equals(pageUrl)) {
                status = true;
                System.out.println("Navigated to the page successfully");
            } else {
                System.out.println("Navigation failed");
                status = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public static void clickElement(ChromeDriver driver, By xpath) throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(xpath));
            wait.until(ExpectedConditions.visibilityOf(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(1000);
    }

    public static void printMessage(ChromeDriver driver) {
        try {
            WebElement aboutHeadline = driver.findElement(By.xpath("//h1[contains(text(),'About')]"));
            System.out.println(aboutHeadline.getText());
            WebElement printMessageFirst = driver.findElement(By.xpath("(//p[contains(@class,'text-primary')])[1]"));
            System.out.println(printMessageFirst.getText());
            WebElement printMessageSecond = driver.findElement(By.xpath("(//p[contains(@class,'text-primary')])[2]"));
            System.out.println(printMessageSecond.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String maturityLastOfMovie(ChromeDriver driver) {
        try {
            Thread.sleep(1000);
    
            // Retrieve movie elements
            List<WebElement> movieElements = driver.findElements(By.xpath("//*[@id='video-title']"));
    
            if (!movieElements.isEmpty()) {
                WebElement lastElement = movieElements.get(movieElements.size() - 1);
                String ariaLabel = lastElement.getAttribute("aria-label");
    
                // Extract movie title and genre from aria-label
                String movieTitle = lastElement.getText().trim();
                String genre = extractGenreFromAriaLabel(ariaLabel);
    
                // Log movie title and genre
                System.out.println("Movie title: " + movieTitle);
                System.out.println("Genre found: " + genre);
                System.out.println("Full aria-label: " + ariaLabel);
    
                // Check genre against expected values
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue(genre.contains("Comedy") || genre.contains("Animation"),
                    "Expected genre to be Comedy or Animation but found " + genre);
    
                softAssert.assertAll();
            } else {
                System.out.println("No movie elements found.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return null;
    }
  
    
    public static String genreOfLastMovie(ChromeDriver driver) {
        try {
            // Wait for elements to be present (consider using WebDriverWait for better stability)
            Thread.sleep(1000);
    
            // Retrieve movie genre elements
            List<WebElement> movieGenreElements = driver.findElements(By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-grid-movie-renderer']/span"));
    
            if (!movieGenreElements.isEmpty()) {
                // Select the last element
                WebElement lastElement = movieGenreElements.get(movieGenreElements.size() - 1);
                String genreText = lastElement.getText().trim();
    
                // Extract genre part if the format is known
                String[] genreParts = genreText.split(" • ");
                if (genreParts.length > 0) {
                    genreText = genreParts[0].trim(); // Take the first part as the genre
                }
    
                // Log the genre for debugging
                System.out.println("Genre found: " + genreText);
    
                // Soft assert to check if the genre matches the expected values
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue(genreText.startsWith("Comedy") || genreText.startsWith("Animation"),
                    "Expected genre to be Comedy or Animation but found " + genreText);
    
                softAssert.assertAll();
            } else {
                System.out.println("No movie genre elements found.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return null;
    }
    
    
    private static String extractGenreFromAriaLabel(String ariaLabel) {
        // Example aria-label: "The Wolf of Wall Street by Comedy • 2013 2 hours, 44 minutes"
        String[] parts = ariaLabel.split("by");
        if (parts.length > 1) {
            String genrePart = parts[1].split("•")[0].trim(); // Extract text before "•"
            return genrePart;
        }
        return "Unknown";
    }
    

    public static void clickOnTab(ChromeDriver driver, String movieName) throws InterruptedException {
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement tabElement = driver.findElement(By.xpath("//a[@title='" + movieName + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", tabElement);
            wait.until(ExpectedConditions.visibilityOf(tabElement)).click();
            System.out.println(movieName + " is Clicked successfully");
        } catch (Exception e) {
            System.out.println(movieName + " Element is not clicked");
            System.out.println(e.getMessage());
        }
        Thread.sleep(1000);
    }

    public static void jumpToTheFirstSection(ChromeDriver driver) {
        try {
            List<WebElement> sectionElements = driver.findElements(By.xpath("//*[@id='dismissible' and @class='style-scope ytd-shelf-renderer' ]"));
            WebElement firstSection = sectionElements.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", firstSection);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clickOnNextButton(ChromeDriver driver, String sectionName, int times) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sectionElement = driver.findElement(By.xpath("//span[@id='title' and contains(text(),'" + sectionName + "')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", sectionElement);
        WebElement nextButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='title' and contains(text(),'" + sectionName + "')]//ancestor::div[@id='dismissible']//child::button[@aria-label='Next']")));
        try {
            for (int i = 0; i < times; i++) {
                if (nextButtonElement.isDisplayed() && nextButtonElement.isEnabled()) {
                    nextButtonElement.click();
                    System.out.println("Next click is Done");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void nameOfLastPlayList(ChromeDriver driver, String sectionName) throws InterruptedException {
        List<WebElement> playListNames = driver.findElements(By.xpath("//a[contains(@title,'" + sectionName + "')]//ancestor::div[@id='dismissible']//child::h3"));
        WebElement lastPlayListElement = playListNames.get(playListNames.size() - 1);
        String playListHeadLineText = lastPlayListElement.getText().trim();
        System.out.println(playListHeadLineText);
        Thread.sleep(1000);
    }

    public static void noOfTracks(ChromeDriver driver, String sectionName, String playListName) throws InterruptedException {
        try {
            WebElement tracksElement = driver.findElement(By.xpath("//a[contains(@title,'" + sectionName + "')]//ancestor::div[@id='dismissible']//h3[contains(text(),'" + playListName + "')]/../p"));
            String tracks = tracksElement.getText();
            String[] trackArray = tracks.split(" ");
            System.out.println(trackArray[0]);
            int trackCount = Integer.parseInt(trackArray[0]);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(trackCount <= 50, "The number of tracks listed is greater than 50: " + trackCount);
            softAssert.assertAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(1000);
    }

    public static void titleOfNews(ChromeDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement latestNewsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Latest news posts')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", latestNewsElement);

        List<WebElement> titleElements = driver.findElements(By.xpath("//*[@class='style-scope ytd-post-renderer']//h3"));
        for (WebElement titleElement : titleElements) {
            String title = titleElement.getText();
            System.out.println(title);
        }
    }

    public static void sumOfTheLikes(ChromeDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> likeElements = driver.findElements(By.xpath("//span[@id='like-count']"));
            int totalLikes = 0;
    
            for (WebElement likeElement : likeElements) {
                String likeText = likeElement.getText().replaceAll(",", "");
                totalLikes += Integer.parseInt(likeText);
            }
    
            System.out.println("Total likes: " + totalLikes);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }



public static void click(WebElement element) {
    try {
        element.click();
        System.out.println("Clicked on the element successfully.");
    } catch (Exception e) {
        System.out.println("Error clicking the element: " + e.getMessage());
    }
}

public static void sendKeys(WebElement element, String keys) {
    try {
        element.sendKeys(keys);
        System.out.println("Sent keys to the element successfully.");
    } catch (Exception e) {
        System.out.println("Error sending keys to the element: " + e.getMessage());
    }
}

public static void scrollToRight(ChromeDriver driver) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'scrollToRight'");
}

}