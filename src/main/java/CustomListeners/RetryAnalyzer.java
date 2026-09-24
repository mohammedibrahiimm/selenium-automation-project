package CustomListeners;

import Utils.LogUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = 1;
    private int attempt = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && attempt < MAX_RETRIES) {
            attempt++;
            LogUtils.warn("Retrying '" + result.getMethod().getMethodName()
                    + "' — attempt " + attempt + "/" + MAX_RETRIES);
            return true;
        }
        return false;
    }
}