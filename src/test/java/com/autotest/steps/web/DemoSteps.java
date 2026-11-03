package com.autotest.steps.web;

import com.autotest.util.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DemoSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private String pageTitle;

    @Given("打开图书列表页面")
    public void openBooksPage() {
        driver = DriverFactory.getDriver("web");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://demoqa.com/books");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBox")));
    }

    @When("获取页面标题")
    public void getTitle() {
        pageTitle = driver.getTitle();
        System.out.println("【调试】页面标题：" + pageTitle);
    }

    @Then("页面标题包含 {string}")
    public void checkTitleContains(String expectText) {
        Assert.assertTrue(pageTitle.contains(expectText), "标题校验失败，实际：" + pageTitle);
    }

    @When("在搜索框输入 {string}")
    public void inputSearchWord(String keyword) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBox")));
        searchInput.clear();
        searchInput.sendKeys(keyword);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("页面包含 {string}")
    public void pageContainsText(String text) {
        String pageSource = driver.getPageSource();
        System.out.println("【调试】校验页面是否包含：" + text);
        Assert.assertTrue(pageSource.contains(text), "页面未找到关键词：" + text);
    }

    @After
    public void afterScenario() {
        DriverFactory.closeDriver();
    }
}
