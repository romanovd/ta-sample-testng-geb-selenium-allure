package org.goeuro.pages

import geb.Page
import geb.waiting.WaitTimeoutException
import org.openqa.selenium.By
import ru.yandex.qatools.allure.annotations.Step

/**
 * Created by dmitriy.romanov on 1/11/2016.
 */
class HomePage extends BasePage {
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
        inputFromAutoComplete(required: false, wait: false) {
            fromBox.$("ul", class: "'ui-autocomplete ui-menu'")
        }


        inputToAutoComplete(required: false, wait: false) {
            $(By.xpath("//div[@class='five columns to-box']//ul[contains(@class,'ui-autocomplete ui-menu')]"))
        }

        departureDate { $("#departure_date") }
    }

    public void fillForm(params) {
        if (params["from"]) {
            fromInput << params["from"]
            pickUPSeggestion(inputFromAutoComplete, params["from"])
        }

        if (params["to"]) {
            toInput << params["to"]
            pickUPSeggestion(inputToAutoComplete, params["to"])
        }
        sleep(100)
    }

    protected void pickUPSeggestion(autoComplete, suggestion) {
        def firstItem = autoComplete.$("a", text: contains(suggestion))?.first()
        if (firstItem) {
            firstItem.click()
        } else {
            log.error("Could not pickup ${suggestion} from a list")
        }
    }

    @Step
    public Page SafeSearch(params) {
        def page
        fillForm(params)
        reportingHelper.takeScreenShotToReport("Search started", getDriver())

        searchButton.click()
        try {
            waitFor("slow", { browser.at(SearchPage) })
        }
        catch (WaitTimeoutException e) {
            log.debug "retry search action"
            searchButton.click()
        }
        waitForLoadingComplete()
        reportingHelper.takeScreenShotToReport("Search displayed", getDriver())
        page
    }

    public void Search(params, times = 3) {
        def attempt = 1
        def page
        while (attempt <= times) {
            page = SafeSearch(params)
            if (page.getClass() == SearchPage) {
                break
            }
        }
        browser.at(SearchPage)
        page
    }

}