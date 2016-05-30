package org.goeuro.data_providers

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.testng.annotations.DataProvider

/**
 * Created by konica.sarker on 21.07.2015.
 */
class ExistingCities {
    static Log log = LogFactory.getLog(this.getClass());

    @DataProvider(name = "CityPairs")
    public static Object[][] validCityPairs() {
        def array = new Object[1][]
        array[0] = ["Berlin", "Prag, Tschechien"]
        array
    }

    @DataProvider(name = "FarCityPairs")
    public static Object[][] validFarCityPairs() {
        def array = new Object[1][]
        array[0] = ["Berlin", "Sofia, Bulgarien"]
        array
    }

    @DataProvider(name = "ManyRoutesCityPairs")
    public static Object[][] validManyRoutesCityPairs() {
        def array = new Object[1][]
        array[0] = ["Berlin", "Hamburg, Deutschland"]
        array
    }
}