package org.goeuro.listeners

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.goeuro.tests.BaseTest
import org.goeuro.utils.ReportingHelper
import org.openqa.selenium.WebDriver
import org.testng.ITestResult
import org.testng.TestListenerAdapter
import ru.yandex.qatools.allure.Allure
/**
 * Created by konica.sarker on 11.09.2015.
 */
public class TestListener extends TestListenerAdapter {
    static Log log = LogFactory.getLog(this.getClass());
    Allure lifecycle = Allure.LIFECYCLE
    public reportingHelper = new ReportingHelper()


    @Override
    public void onTestFailure(ITestResult result) {
        log.info("================ TEST  " + result.getName().toString() + "  FAILED ================")
        Object currentClass = result.getInstance()

        WebDriver webDriver = ((BaseTest) currentClass).browser.getDriver();
        if (webDriver != null) {
            reportingHelper.takeScreenShotToReport("Test failure screenshot", webDriver)
        } else {
            log.info("failed to get a screenshot")
        }
    }
}

