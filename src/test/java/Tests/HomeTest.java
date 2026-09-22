package Tests;

import POM.LoginPage;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeTest {
    WebDriver driver;

    @Test
    public void addToCartTC(){
        new LoginPage(driver).
                Login("standard_user","secret_sauce").
                isLoggedIn("https://www.saucedemo.com/inventory.html").
                addToCart().
                validateCartIcon();
    }



    @BeforeMethod
    public void setUp(){
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver=new EdgeDriver(edgeOptions);
        driver.get("https://www.saucedemo.com/");
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
