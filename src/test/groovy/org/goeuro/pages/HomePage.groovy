package org.goeuro.pages

import geb.Page
import geb.waiting.WaitTimeoutException
import org.openqa.selenium.By
import ru.yandex.qatools.allure.annotations.Step

/**
 * Created by dmitriy.romanov on 1/11/2016.
 */
class HomePage extends BasePage {
    static final int TIMES = 3
    static url = ""

    static at = {
        $(".homepage-content")
    }


    static content = {
        searchForm { $("#search") }
        fromInput { $("#from_filter") }
        toInput { $("#to_filter") }
        searchButton { $("#search-form__submit-btn") }
        fromBox { $("div", class: "five columns from-box") }

        suggestionList(required: false, wait: true) {
            searchForm.$(By.xpath("//ul[contains(@class,'ui-autocomplete') and contains(@style,'display: block;')]"))
        }

        inputFromAutoComplete(required: false, wait: true) {
            $(By.xpath("//div[@class='five columns to-box']//ul[contains(@class,'ui-autocomplete ui-menu')]"))
        }


        inputToAutoComplete(required: false, wait: true) {
            $(By.xpath("//div[@class='five columns to-box']//ul[contains(@class,'ui-autocomplete ui-menu')]"))
        }

        departureDate { $("#departure_date") }
    }

    public void fillForm(params) {
        if (params["from"]) {
            fromInput.firstElement().clear()
            fromInput << params["from"]
            pickUPSeggestion(params["from"])
        }

        if (params["to"]) {
            toInput.firstElement().clear()
            toInput << params["to"]
            pickUPSeggestion(params["to"])
        }
        sleep(100)
    }

    protected void pickUPSeggestion(suggestion) {
        def item = suggestionList.$(By.xpath(".//li[contains(.,'${suggestion}')]")).first()

        if (item) {
            item.click()
        } else {
            log.error("Could not pickup ${suggestion} from a list")
        }
    }

    @Step
    public Page Search(params) {
        def page
        fillForm(params)
        reportingHelper.takeScreenShotToReport("Search started", getDriver())

        sleep(1000)
        def attempt = 1
        def notOpened = true
        while ((attempt <= TIMES) && (notOpened)) {
            log.debug("Make a click")
            searchButton.firstElement().click()
            try {
                waitFor("slow",
                        {
                            browser.at(SearchPage)
                        }
                )
                notOpened = false
            }
            catch (WaitTimeoutException e) {
                log.debug "retry search action"
                return page
            }
            attempt++
        }
        waitForLoadingComplete()

        reportingHelper.takeScreenShotToReport("Search displayed", getDriver())
        page
    }

}