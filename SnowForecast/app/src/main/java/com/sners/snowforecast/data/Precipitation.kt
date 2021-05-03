package com.sners.snowforecast.data

import java.util.ArrayList

//precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour)
//	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all).
//	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation,
//	0.002 in./hr. corresponds to very light precipitation,
//	0.017 in./hr. corresponds to light precipitation,
//	0.1 in./hr. corresponds to moderate precipitation,
//	and 0.4 in./hr. corresponds to heavy precipitation.

/**
 * Everything about Precipitation
 * @param inDaily daily weather data
 * @param inHourly hourly weather data
 * @param inMinutely minutely weather data
 * @param inCurrently current weather data
 */
class Precipitation(inDaily: Daily?, inHourly: Hourly?, inMinutely: Minutely?, inCurrently: Currently) {

    /**
     *  @property daily Daily weather info
     */
    private val daily: Daily?

    /**
     *  @property hourly Hourly weather info
     */
    private val hourly: Hourly?

    /**
     *  @property minutely Minutely weather info
     */
    private val minutely: Minutely?

    /**
     *  @property currently Current weather info
     */
    private val currently: Currently

    /**
     *  @property weatherHelper Helper class with useful weather functions
     */
    private val weatherHelper = WeatherHelper()

    /**
     *  @property minPrecipLight Min precip to take into account
     */
    private val minPrecipLight = 0.002

    /**
     *  @property minPrecipMedium Min precip to take into account if we're ignoring light precip
     */
    private val minPrecipMedium = 0.017

    init {
        daily = inDaily
        hourly = inHourly
        minutely = inMinutely
        currently = inCurrently

    }

    /**
     * Time til Precipitation
     * @param toIgnoreLightPrecip boolean
     * @return Minutes til precipitation
     */
    private fun timeTil(toIgnoreLightPrecip: Boolean): Long {

        var minutesTilPrecip = -1
        var minutesTilPrecipControl = -1
        val minPrecip = if (toIgnoreLightPrecip) minPrecipMedium else minPrecipLight

        //  Case = currently -> 0
        //  Case = minutely -> minutesTil
        //  Case = hourly -> hoursTil * 60
        //  Case = daily -> daysTil * 60 * 24
        when {
            //  It's raining now
            currently.precipIntensityNum > minPrecip -> minutesTilPrecip = 0
            null != minutely && timeTilRain(minutely.minutelyData, minPrecip, 1) >=0 ->
                minutesTilPrecip = timeTilRain(minutely.minutelyData, minPrecip, 1)
            null != hourly && timeTilRain(hourly.hourlyData, minPrecip, 1) >=0 ->
                minutesTilPrecip = timeTilRain(hourly.hourlyData, minPrecip, 60)
            null != daily && timeTilRain(daily.dailyData, minPrecip, 1) >=0 ->
                minutesTilPrecip = timeTilRain(daily.dailyData, minPrecip, 60 * 24)
        }


        return minutesTilPrecip.toLong()
    }

    /**
     * Time til Precipitation as string
     * @param toIgnoreLightPrecip boolean
     * @return Formatted time string
     */
    fun timeTilString(toIgnoreLightPrecip: Boolean): String? {

        var timeTilString: String? = WeatherConstants.NONE_FORECAST
        val timeTil = timeTil(toIgnoreLightPrecip)
        if (timeTil > 0) {
            timeTilString = weatherHelper.formatTime(timeTil)
        } else if (timeTil == 0L) {
            timeTilString = WeatherConstants.NOW
        }
        return timeTilString
    }

    /**
     * Period when Intensity Exceeded
     * @param intervalData the data to search
     * @param minValue The minimum value under which we ignore
     * @return Integer index of element where condition was first met
     */
    private fun periodWhenIntensityExceeded(intervalData: ArrayList<IntervalData>, minValue: Double): Int {
        var periodFound = -1
        var precipExceedsMin = false
        var intervalCounter = 0
        while (intervalCounter < intervalData.size && !precipExceedsMin) {
            val fieldValue = intervalData[intervalCounter].precipIntensity
            if (fieldValue > minValue) {
                precipExceedsMin = true
                periodFound = intervalCounter
            }
            intervalCounter++
        }
        return periodFound
    }

    /**
     * Time til Rain
     * @param intervalData the data to search
     * @param minValue The minimum value under which we ignore
     * @param multiplier A number to convert the interval number to a time
     * @return Integer index of element where condition was first met
     */
    private fun timeTilRain(intervalData: ArrayList<IntervalData>, minValue: Double, multiplier: Int): Int {
        var timeTilInterval = -1
        val rainInterval =
            periodWhenIntensityExceeded(intervalData, minValue)
        if (rainInterval >= 0) {
            timeTilInterval = (rainInterval + 1) * multiplier
        }
        return timeTilInterval
    }


}