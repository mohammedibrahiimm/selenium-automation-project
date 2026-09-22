package CustomListeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private boolean retry = false;

    @Override
    public boolean retry(ITestResult result) {

        if (!retry && result.getStatus() == ITestResult.FAILURE) {
            retry = true;
            return true;
        }

        retry = false;
        return false;
    }
}