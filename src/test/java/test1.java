import Utils.LogUtils;
import org.testng.annotations.Test;

public class test1 {
    @Test
    public void teeesst(){
        System.out.println("Hello");
        LogUtils.info("Hello Printed");
        LogUtils.info("Test Case Finished");
    }
}
