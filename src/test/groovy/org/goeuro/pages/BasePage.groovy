package org.goeuro.pages

import geb.Page
import geb.waiting.WaitTimeoutException
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.goeuro.utils.ReportingHelper

/**
 * Created by konica.sarker on 07.08.2015.
 */
class BasePage extends Page {
    static Log log = LogFactory.getLog(this.getClass());
    public reportingHelper = new ReportingHelper()

    public void waitForLoadingComplete() {
        try {
            log.debug "waiting for loading process is started"
            waitFor(2,
                    {
                        !$(".loading").empty
                    })
        } catch (WaitTimeoutException e) {
            log.debug("loading was not started ?")
        }

        log.debug "waiting for loading process is finished"
        try {
            waitFor("slow",
                    {
                        $(".loading").empty
                    })

        } catch (WaitTimeoutException e) {
            log.debug("loading was not finished ?")
        }

    }
}