package POM;

import Utils.LogUtils;
import Utils.actionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {
    private WebDriver driver;
    private final By addToCartbtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_badge");
    private actionUtils action;
    public HomePage(WebDriver driver){
        this.driver = driver;
        this.action = new actionUtils(driver);
    }

    public HomePage addToCart(){
        action.click(addToCartbtn);
        return this;
    }

    public HomePage validateCartIcon(){
        String cartIconText = action.getText(cartIcon);
        LogUtils.info("Current Cart Icon Text "+cartIconText);
        Assert.assertEquals(cartIconText,"1","Wrong Count");
        return this;
    }

}
