package org.goeuro.pages

import org.apache.commons.lang3.time.DateUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.goeuro.models.SearchResultItem
import org.goeuro.models.SearchResultList
import ru.yandex.qatools.allure.annotations.Step

/**
 * Created by dmitriy.romanov on 1/11/2016.
 */
class SearchPage extends BasePage {
    static Log log = LogFactory.getLog(this.getClass());
    static url = ""

    static at = {
        $("#travel-search-results")
    }


    static content = {
        header {} //tbd
        searchResult { $(".nine.columns.push-three") }
        resultList { searchResult.$(".tabs-content.contained") }
        trainResultList { resultList.$("#results-train") }
        busResultList { resultList.$("#results-bus") }
        flightResultList { resultList.$("#results-flight") }

        tabTrain { $("#tab_train") }
        tabFlight { $("#tab_flight") }
        tabBus { $("#tab_bus") }

        pagination(required: false) { getActiveTab().$(".pagination") }
        nextButton(required: false) { pagination.$(".next a") }
        lastButton(required: false) { pagination.$(".last a") }
    }

    public String getActiveTabName() {
        searchResult.$("dd.active").attr("id")
    }

    public getActiveTab() {
        def tab = getActiveTabName()
        switch (tab) {
            case "tab_train": trainResultList
                break
            case "tab_flight": flightResults
                break
            case "tab_bus": busResultList
                break
        }
    }




    public SearchResultList getTrainResults() {
        getResults(trainResultList)
    }

    public SearchResultList getBusResults() {
        getResults(busResultList)
    }

    public SearchResultList getFlightResults() {
        getResults(flightResultList)
    }


    public nextPage() {
        log.debug("Pressing Next button")
        def previousPage = getCurrentPageNo()
        if (nextButton) {
            nextButton.click()
        }
        waitFor(
                30,
                {
                    previousPage != getCurrentPageNo()
                }
        )
        waitForLoadingComplete()
    }

    public String getCurrentPageNo() {
        pagination.$(".current.disabled").text()
    }

    public getCurrentTab() {
        searchResult.$(".tabs.contained dd.active").attr("id").replaceAll("tab_", "")
    }

    public chooseTab(String tab) {
        if (getCurrentTab() == tab) {
            return
        }
        switch (tab) {
            case "train": tabTrain.click()
                break
            case "flight": tabFlight.click()
                break
            case "bus": tabBus.click()
                break
        }

        waitFor(
                10,
                {
                    getCurrentTab() == tab
                }
        )
        waitForLoadingComplete()
    }

    @Step
    public SearchResultList getPageResults() {
        log.info("Building result list for a page")
        reportingHelper.takeScreenShotToReport("Building a test result set", getDriver())
        def results = getActiveTab().$(".result")
        SearchResultList resultList = new SearchResultList()

        def combinedValue, timeString, days
        results.each { row ->
            log.debug("Parsing the row: ${row.text()}")
            days = 0
            try {
                SearchResultItem item = new SearchResultItem()
                def tr = row.$(".unstyled.mtop10.other-devices tr").has("td.time-dep")
                combinedValue = tr.$("td").first().text()
                timeString = combinedValue.findAll("\\d*:\\d*").first()
                item.timeFrom = Date.parse("HH:mm", timeString)
                item.cityFrom = combinedValue.replace(timeString, "").trim()

                combinedValue = tr.$("td.departure-datetime").text()
                timeString = combinedValue.findAll("\\d*:\\d*").first()
                if (combinedValue.contains("+")) {
                    days = combinedValue.findAll("(\\+\\d*)").first().findAll("\\d").first().toInteger()
                }

                item.timeTo = DateUtils.addDays(Date.parse("HH:mm", timeString), days)
                item.cityTo = combinedValue.replace(timeString, "").trim()

                item.stops = tr.$("td.stops").text().findAll("\\d*").first().toInteger()

                timeString = tr.$("td.total-leg-time").text()
                item.duration = Date.parse("HH:mm'h'", timeString)
                combinedValue = row.$(".price-cell").text()
                item.cost = Float.valueOf(combinedValue.findAll("\\d\\d*.\\d*").first())
                resultList.add(item)
            } catch (Exception e) {
                log.error("Error during parsing the row: ${e}")
            }
        }
        resultList
    }

    public SearchResultList getResults() {
        SearchResultList resultList = new SearchResultList()
        resultList.items.addAll(getPageResults().items)
        while (lastButton) {
            def r = getPageResults()
            resultList.items.addAll(getPageResults().items)
            nextPage()
        }
        resultList
    }


}