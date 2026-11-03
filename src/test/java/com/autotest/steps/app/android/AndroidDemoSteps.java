package com.autotest.steps.app.android;

import com.autotest.util.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class AndroidDemoSteps {

    // 使用父接口WebDriver，规避编译运行ContextAware类找不到问题
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("启动API Demos应用")
    public void launchApiDemos() {
        driver = DriverFactory.getDriver("android");
        // 冷启动App延长等待到30秒
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // 等待首页Accessibility元素可见
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Accessibility")));
    }

    @When("点击 {string} 菜单")
    public void clickMenu(String menuName) {
        WebElement el = wait.until(ExpectedConditions
                .elementToBeClickable(AppiumBy.accessibilityId(menuName)));
        el.click();
    }

    @Then("安卓页面包含文本 {string}")
    public void checkPageText(String text) {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(text), "页面未找到：" + text);
    }

    @After
    public void tearDownApp() {
        DriverFactory.closeDriver();
    }
}
