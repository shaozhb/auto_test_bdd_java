package com.autotest.runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        // features写死实际目录，不要用${}变量！
        features = "src/test/resources/features",
        glue = "com.autotest.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        // tags支持读取JVM系统参数 -Dcucumber.filter.tags=xxx
        tags = "${cucumber.filter.tags}"
)
public class TestRunner {
        private TestNGCucumberRunner runner;

        @BeforeClass(alwaysRun = true)
        public void setup() {
                runner = new TestNGCucumberRunner(this.getClass());
        }

        @Test(groups = "cucumber", description = "执行BDD场景", dataProvider = "scenarios")
        public void run(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
                runner.runScenario(pickleWrapper.getPickle());
        }

        @DataProvider
        public Object[][] scenarios() {
                return runner.provideScenarios();
        }

        @AfterClass(alwaysRun = true)
        public void tearDown() {
                if(runner != null) { // 增加判空防御，防止初始化失败空指针
                        runner.finish();
                }
        }
}
