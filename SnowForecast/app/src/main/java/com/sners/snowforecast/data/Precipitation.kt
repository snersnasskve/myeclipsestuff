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
        val minPrecip = if (toIgnoreLightPrecip) minPrecipMedium else minPrecipLight

        val precipIntensity: Float = currently.precipIntensityNum
        if (precipIntensity > minPrecip) {
            //	It's raining now
            minutesTilPrecip = 0
        } else {
            //	Check minutely
            if (null != minutely) {
                val rainMinutes =
                    periodWhenIntensityExceeded(minutely.minutelyData,minPrecip)
                if (rainMinutes >= 0) {
                    minutesTilPrecip = (rainMinutes + 1)
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != hourly) {
                    val rainHours: Int =
                        periodWhenIntensityExceeded(hourly.hourlyData, minPrecip)
                    if (rainHours >= 0) {
                        minutesTilPrecip = ((rainHours + 1) * 60)
                    }
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != daily) {
                    val rainDays: Int =
                        periodWhenIntensityExceeded(daily.dailyData, minPrecip)
                    minutesTilPrecip = if (rainDays >= 0) {
                        ((rainDays + 1) * 60 * 24)
                    } else {
                        -1
                    }
                }
            }
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


}