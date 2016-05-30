package org.goeuro.utils

import geb.Browser
import geb.Page
import org.apache.commons.io.FileUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import ru.yandex.qatools.allure.annotations.Attachment

import java.text.SimpleDateFormat
/**
 * Created by konica.sarker on 10.09.2015.
 */
class ReportingHelper extends Page {
    static Log log = LogFactory.getLog(this.getClass());
    static int testRunId

    public void takeScreenshot(testMethodName) {
        log.debug("===================== Taking Screenshot ====================")

        def sdf = new SimpleDateFormat("ddMMyyyy")
        def now = sdf.format(new Date()).toString()

        def folderName = System.getProperty("user.dir") + "\\SCREENSHOTS\\" + now + "\\"
        def folder = new File(folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        // generate a filename with methodname_currenttime.png
        sdf = new SimpleDateFormat("ddMMyyyy_HHmmss")
        now = sdf.format(new Date()).toString()

        def fullFileName = folderName + testMethodName + "_" + now
        try {
            def driver
            if (getBrowser()) {
                driver = getBrowser().getDriver()
            } else {
                driver = new Browser().getDriver()
            }
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
            FileUtils.copyFile(scrFile, new File(fullFileName + ".png"));
            log.info("********* Placed screen shot in " + fullFileName + " *******");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public byte[] takeScreenShotToReport(String attachName, WebDriver driver) throws IOException {
        log.debug("Making a screenshot ${attachName}")
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    @Attachment(value = "{0}", type = "text/plain")
    public String reportText(String reportLine, String message) {
        log.info("Report: [${reportLine}]\n:\n[\n${message}\n]")
        return message;
    }


}
