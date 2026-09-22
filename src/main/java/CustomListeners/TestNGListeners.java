package CustomListeners;

import Utils.LogUtils;
import org.testng.*;

public class TestNGListeners implements ITestListener, IInvokedMethodListener, IExecutionListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod()) {
            LogUtils.info("Before Invocation: " + method.getTestMethod().getMethodName()+" started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod()) {
            LogUtils.info("After Invocation: " + method.getTestMethod().getMethodName()+" finished");
        }
//        System.out.println("After Invocation: " + method.getTestResult()+" finished");
    }

    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test Passed: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        LogUtils.info("Test Failed: " + result.getMethod().getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
        LogUtils.info("Test Skipped: " + result.getMethod().getMethodName());
    }

    public void onExecutionStart() {
        LogUtils.info("Execution started");
    }

    public void onExecutionFinish() {
        LogUtils.info("Execution finished");
    }

}

/*
* We can use IInvokedMethodListener to perform actions before and after each test method invocation.
* The beforeInvocation method is called before the test method is invoked,
* and the afterInvocation method is called after the test method has completed.
* This allows us to add custom behavior around the execution of test methods,
* such as logging, setup, or teardown actions.
*
* 1- We can call it before method using annotation @Listeners(Listensers.TestNGListeners.class)
* 2- We can call it before method using testng.xml file
* 3- main -> resources -> META-INF (directory) -> services (directory) -> org.testng.ITestNGListener (file) -> Listeners.TestNGListeners (content of the file)
* Third one is a service loader mechanism, which allows TestNG to automatically discover and register the listener without explicitly specifying it in the test class or testng.xml file.
* */

/*
* onExecutionStart() runs before anything is started
* onExecutionFinish() runs after everything is finished
* */