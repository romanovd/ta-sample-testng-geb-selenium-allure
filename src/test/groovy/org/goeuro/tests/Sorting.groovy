package org.goeuro.tests

import org.goeuro.data_providers.ExistingCities
import org.goeuro.models.SearchResultList
import org.goeuro.pages.HomePage
import org.goeuro.pages.SearchPage
import org.testng.Assert
import org.testng.annotations.Listeners
import org.testng.annotations.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Features
import ru.yandex.qatools.allure.annotations.Severity
import ru.yandex.qatools.allure.annotations.Title
import ru.yandex.qatools.allure.model.SeverityLevel

/**
 * Created by dmitriy.romanov on 1/11/2016.
 */

@Listeners([org.goeuro.listeners.TestListener.class])
class Sorting extends BaseTest {

    @Features("Search Results -> Ordering -> By Price")
    @Title('CityPairs')
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check correctness of ordering results")
    @Test(
            timeOut = 600000L,
            dataProvider = "CityPairs",
            dataProviderClass = ExistingCities.class,
            enabled = true
    )
    public void testAscending(cityFrom, cityTo) {
        log.info("Test started")
        def tab = "train"
        reportingHelper.reportText("Test Data:", "${cityFrom} -> ${cityTo} by ${tab}")

        HomePage homePage = browser.to(HomePage)

        homePage.Search(
                [
                        from: cityFrom,
                        to  : cityTo
                ]
        )

        SearchPage page = browser.at(SearchPage)

        page.chooseTab(tab)
        SearchResultList results = page.getResults()

        def costsList = []

        results.items.each { row ->
            costsList << row.cost
        }

        reportingHelper.reportText("Actual Cost array", costsList.toString())
        if (costsList.sort(false)!=costsList) {
            reportingHelper.reportText("Expected Cost array", costsList.sort(false).toString())
        }

        Assert.assertEquals(costsList.sort(false),costsList, "Search results list is not sorted by cost")
        sleep(1000)
    }

    @Features("Search Results -> Ordering -> By Price")
    @Title('FarCityPairs')
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check correctness of ordering results")
    @Test(
            timeOut = 600000L,
            dataProvider = "FarCityPairs",
            dataProviderClass = ExistingCities.class,
            enabled = true
    )
    public void testAscendingFarCities(cityFrom, cityTo) {
        log.info("Test started")
        def tab = "bus"
        reportingHelper.reportText("Test Data:", "${cityFrom} -> ${cityTo} by ${tab}")

        HomePage homePage = browser.to(HomePage)

        homePage.Search(
                [
                        from: cityFrom,
                        to  : cityTo
                ]
        )

        SearchPage page = browser.at(SearchPage)

        page.chooseTab(tab)
        SearchResultList results = page.getResults()

        def costsList = []

        results.items.each { row ->
            costsList << row.cost
        }

        reportingHelper.reportText("Actual Cost array", costsList.toString())
        if (costsList.sort(false)!=costsList) {
            reportingHelper.reportText("Expected Cost array", costsList.sort(false).toString())
        }

        Assert.assertEquals(costsList.sort(false),costsList, "Search results list is not sorted by cost")
        sleep(1000)
    }

    @Features("Search Results -> Ordering -> By Price")
    @Title('ManyRoutesCityPairs')
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check correctness of ordering results")
    @Test(
            timeOut = 600000L,
            dataProvider = "ManyRoutesCityPairs",
            dataProviderClass = ExistingCities.class,
            enabled = true
    )
    public void testAscendingManyRoutes(cityFrom, cityTo) {
        log.info("Test started")
        def tab = "bus"
        reportingHelper.reportText("Test Data:", "${cityFrom} -> ${cityTo} by ${tab}")

        HomePage homePage = browser.to(HomePage)

        homePage.Search(
                [
                        from: cityFrom,
                        to  : cityTo
                ]
        )

        SearchPage page = browser.at(SearchPage)

        page.chooseTab(tab)
        SearchResultList results = page.getResults()

        def costsList = []

        results.items.each { row ->
            costsList << row.cost
        }

        reportingHelper.reportText("Actual Cost array", costsList.toString())
        if (costsList.sort(false)!=costsList) {
            reportingHelper.reportText("Expected Cost array", costsList.sort(false).toString())
        }

        Assert.assertEquals(costsList.sort(false),costsList, "Search results list is not sorted by cost")
        sleep(1000)
    }
}
