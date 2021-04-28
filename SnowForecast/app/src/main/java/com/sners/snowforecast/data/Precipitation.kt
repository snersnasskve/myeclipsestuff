package com.sners.snowforecast.data

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
     fun timeTil(toIgnoreLightPrecip: Boolean): Long {

        var minutesTilPrecip: Long = -1
        var minPrecip = 0.002f
        if (toIgnoreLightPrecip) {
            minPrecip = 0.017f
        }
        val precipIntensity: Float = currently.precipIntensityNum
        if (precipIntensity > minPrecip) {
            //	It's raining now
            minutesTilPrecip = 0
        } else {
            //	Check minutely
            if (null != minutely) {
                val rainMinutes: Int =
                    weatherHelper.periodWhenValueExceededPrecipIntensity(minutely.minutelyData, minPrecip.toDouble())
                if (rainMinutes >= 0) {
                    minutesTilPrecip = (rainMinutes + 1).toLong()
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != hourly) {
                    val rainHours: Int =
                        weatherHelper.periodWhenValueExceededPrecipIntensity(hourly.hourlyData, minPrecip.toDouble())
                    if (rainHours >= 0) {
                        minutesTilPrecip = ((rainHours + 1) * 60).toLong()
                    }
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != daily) {
                    val rainDays: Int =
                        weatherHelper.periodWhenValueExceededPrecipIntensity(daily.dailyData, minPrecip.toDouble())
                    minutesTilPrecip = if (rainDays >= 0) {
                        ((rainDays + 1) * 60 * 24).toLong()
                    } else {
                        -1
                    }
                }
            }
        }
        return minutesTilPrecip
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
}