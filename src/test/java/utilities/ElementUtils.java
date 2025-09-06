package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElementUtils {

    public static void click(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForElementClickable(driver, locator);
            element.click();
        } catch (Exception e) {
            System.out.println("⚠️ Regular click failed, trying JS click for: " + locator);
            jsClick(driver, locator);
        }
    }

    public static void jsClick(WebDriver driver, By locator) {
        WebElement element = WaitUtils.waitForElementPresent(driver, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void type(WebDriver driver, By locator, String text) {
        WebElement element = WaitUtils.waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    public static void typeWithoutClear(WebDriver driver, By locator, String text) {
        WebElement element = WaitUtils.waitForElementVisible(driver, locator);
        element.sendKeys(text);
    }

    public static String getText(WebDriver driver, By locator) {
        WebElement element = WaitUtils.waitForElementVisible(driver, locator);
        return element.getText().trim();
    }

    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        WebElement element = WaitUtils.waitForElementPresent(driver, locator);
        return element.getAttribute(attribute);
    }

    public static boolean isDisplayed(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEnabled(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static void selectDropdownByText(WebDriver driver, By locator, String text) {
        WebElement dropdown = WaitUtils.waitForElementVisible(driver, locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    public static void selectDropdownByIndex(WebDriver driver, By locator, int index) {
        WebElement dropdown = WaitUtils.waitForElementVisible(driver, locator);
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }

    public static void scrollToElement(WebDriver driver, By locator) {
        WebElement element = WaitUtils.waitForElementPresent(driver, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void hoverOverElement(WebDriver driver, By locator) {
        WebElement element = WaitUtils.waitForElementVisible(driver, locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static List<WebElement> getElements(WebDriver driver, By locator) {
        return WaitUtils.waitForElementsVisible(driver, locator);
    }

    public static int getElementCount(WebDriver driver, By locator) {
        try {
            return driver.findElements(locator).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean waitForElement(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}