package POM;

import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {
    private WebDriver driver;
    private final By addToCartbtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_badge");

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public HomePage addToCart(){
        driver.findElement(addToCartbtn).click();
        LogUtils.info("The “Add to cart” button for the Sauce Labs Backpack is clicked.");
        return this;
    }

    public HomePage validateCartIcon(){
        String cartIconText=driver.findElement(cartIcon).getText();
        LogUtils.info("Current Cart Icon Text "+cartIconText);
        Assert.assertEquals(cartIconText,"1","Wrong Count");
        return this;
    }

}
