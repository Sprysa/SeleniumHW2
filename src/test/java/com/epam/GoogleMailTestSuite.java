package com.epam;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GoogleMailTestSuite {
  private static final String INITIAL_PAGE = "https://mail.google.com/";
  private static final String LOGIN = "epam.test.sprysa@gmail.com";
  private static final String PASSWORD = "die34nh2";
  private static final String EMAIL_ADDRESS = "sprysa@gmail.com";
  private static final String SUBJECT = "Test";
  private static final String MESSAGE = "Hi from epam.test.sprysa@gmail.com";

  private static final Logger LOG = LogManager.getLogger(GoogleMailTestSuite.class);
  private WebDriver driver;
  private WebDriverWait wait;

  @BeforeMethod
  private void init() {
    driver = DriverManager.getDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver,10);
  }

  @Test
  public void googleMailTest() {
    driver.get(INITIAL_PAGE);
    LOG.info("Input and submit login");
    WebElement emailInputElement = driver.findElement(By.id("identifierId"));
    emailInputElement.sendKeys(LOGIN);
    WebElement mailNextButton = driver.findElement(By.className("CwaK9"));
    mailNextButton.click();
    LOG.info("Input and submit password");
    WebElement passwordInputElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.
        cssSelector("input.whsOnd.zHQkBf[type=\"password\"]"))));
    passwordInputElement.sendKeys(PASSWORD);
    WebElement passwordNexButton = driver.findElement(By.id("passwordNext"));
    passwordNexButton.click();
    LOG.info("Open message window");
    WebElement composeButton = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.
        xpath("//div[text()=\"Compose\"]"))));
    composeButton.click();
    LOG.info("Fill message");
    WebElement addressElement = driver.findElement(By.name("to"));
    addressElement.sendKeys(EMAIL_ADDRESS);
    WebElement subjectElement = driver.findElement(By.name("subjectbox"));
    subjectElement.sendKeys(SUBJECT);
    WebElement messageBodyElement = driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]"));
    messageBodyElement.sendKeys(MESSAGE);
    LOG.info("Sent message");
    WebElement sendButtonElement = driver.findElement(By.xpath("//div[contains(@data-tooltip, \"Send\")]"));
    sendButtonElement.click();
    LOG.info("Check if message was sent");
    WebElement sentMessageIdentifier = driver.findElement(By.xpath("//span[contains(text(), 'Message sent.')]"));
    Assert.assertTrue(sentMessageIdentifier.isDisplayed(), "Message is not sent.");
  }

  @AfterMethod
  public void deinit() {
    driver.quit();
  }
}
