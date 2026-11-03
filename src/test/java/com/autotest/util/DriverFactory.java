package com.autotest.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * @param runMode web / android
     * @return WebDriver
     */
    public static WebDriver getDriver(String runMode) {
        if (driver.get() == null) {
            if ("android".equalsIgnoreCase(runMode)) {
                UiAutomator2Options options = new UiAutomator2Options();

                // ========== Android原生API‑Demos 配置 ==========
                options.setCapability("deviceName", "Android Emulator");
                options.setCapability("platformName", "Android");
                options.setCapability("automationName", "UiAutomator2");

                // 完整Activity类名，兼容Android7.1.2模拟器
                options.setAppPackage("io.appium.android.apis");
                options.setAppActivity("io.appium.android.apis.ApiDemos");

                // noReset=false：每次启动重置App，回到首页（Demo测试必须false）
                options.setCapability("noReset", false);
                options.setCapability("newCommandTimeout", 60);

                try {
                    driver.set(new AndroidDriver(new URL("http://127.0.0.1:4723"), options));
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Appium服务URL配置错误，请确认appium已启动在4723端口", e);
                }

            } else {
                // ========== PC‑Web Chrome Selenium ==========
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start‑maximized");
                chromeOptions.addArguments("--disable‑notifications");

                driver.set(new ChromeDriver(chromeOptions));
            }
        }
        return driver.get();
    }

    /**
     * 关闭驱动，清除ThreadLocal
     */
    public static void closeDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}
