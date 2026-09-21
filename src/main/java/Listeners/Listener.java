package Listeners;

import DriverFactory.WebDriverFactory;
import Utilities.ActionsBot;
import Utilities.AllureUtils;
import Utilities.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements IInvokedMethodListener, ITestListener, IExecutionListener {

    private static final Logger log = LogManager.getLogger(Listener.class);

    // ---------- IInvokedMethodListener ----------

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            log.info("▶ {} started", testResult.getName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            attachScreenshot(testResult);
        }
    }

    // ---------- ITestListener ----------

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ {} passed", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("✘ {} failed", result.getName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⊘ {} skipped", result.getName());
    }

    // ---------- IExecutionListener ----------

    @Override
    public void onExecutionStart() {
        log.info("Execution started");
        PropertyReader.loadProperties();
        AllureUtils.cleanAllureResults();
    }

    @Override
    public void onExecutionFinish() {
        AllureUtils.setAllureEnvironment();
        log.info("Execution finished");
    }

    // ---------- helpers ----------

    private void attachScreenshot(ITestResult result) {
        WebDriver driver = WebDriverFactory.get();
        if (driver == null) {
            log.warn("No WebDriver available for '{}'; skipping screenshot", result.getName());
            return;
        }
        try {
            new ActionsBot(driver).takeScreenshot(result.getName());
        } catch (Exception e) {
            log.error("Failed to capture screenshot for '{}'", result.getName(), e);
        }
    }
}