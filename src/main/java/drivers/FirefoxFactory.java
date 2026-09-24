package drivers;

import Utils.LogUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxFactory extends AbstractDriver {

    private FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
        profile.setPreference("startup.homepage_welcome_url", "about:blank");
        profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        profile.setPreference("signon.generation.enabled", false);
        profile.setPreference("signon.management.page.breach-alerts.enabled", false);
        profile.setPreference("dom.webnotifications.enabled", false);
        profile.setPreference("dom.push.enabled", false);
        profile.setPreference("dom.disable_open_during_load", false);
        profile.setPreference("datareporting.policy.dataSubmissionEnabled", false);
        profile.setPreference("app.update.enabled", false);
        profile.setPreference("browser.shell.checkDefaultBrowser", false);
        options.setProfile(profile);
        return options;
    }

    @Override
    public WebDriver createDriver() {
        LogUtils.info("FirefoxDriver is created successfully");
        return new FirefoxDriver(getOptions());
    }
}