package CustomListeners;

import Utils.LogUtils;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        if (testMethod != null) {
            LogUtils.info("Applying RetryAnalyzer to: " + testMethod.getName());
        }
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}