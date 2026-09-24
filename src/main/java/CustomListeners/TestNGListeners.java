package CustomListeners;

import Utils.LogUtils;
import Utils.PropertyReader;
import Utils.ScreenshotUtils;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListeners implements ITestListener, IInvokedMethodListener, IExecutionListener {

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            LogUtils.info("Before Invocation: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            LogUtils.info("After Invocation: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LogUtils.error("Test Failed: " + testName + " — " + result.getThrowable());
        ScreenshotUtils.capture(testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onExecutionStart() {
        PropertyReader.loadProperties();
        LogUtils.info("Execution started");
    }

    @Override
    public void onExecutionFinish() {
        LogUtils.info("Execution finished");
    }
}