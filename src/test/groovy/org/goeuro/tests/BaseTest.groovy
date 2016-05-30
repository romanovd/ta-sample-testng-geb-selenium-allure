package org.goeuro.tests

import geb.Browser
import geb.Page
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.goeuro.utils.ReportingHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

import java.lang.reflect.Method
/**
 * Created by konica.sarker on 02.12.2015.
 *
 */

class BaseTest {
    static Log log = LogFactory.getLog(this.getClass());
    public reportingHelper = new ReportingHelper()

    protected static Browser browser

    public at(Page page) {
        browser.at page
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        log.info("Before test started: ${method.getName()}")
        try {
            browser = new Browser()
            browser.setBaseUrl("http://www.goeuro.de")

            WebDriver driver = new FirefoxDriver()
            driver.manage().window().maximize()
            browser.setDriver(driver)

            log.debug("Created a new Browser: ${browser.getDriver()}")
        } catch (Exception e) {
            throw new Exception("Could not create a driver. Check browser's availability or grid configuration !\n" + e)
        }
    }

    @AfterMethod
    public void closeBrowser() {
        log.debug("Quiting a browser: ${browser.getDriver()}")
        try {
            browser.clearCookiesQuietly()
            browser.getDriver().quit()
        } catch (Exception e) {
            log.debug("Ignoring browser shutdown problems:")
            log.debug(e)
        }
    }

}