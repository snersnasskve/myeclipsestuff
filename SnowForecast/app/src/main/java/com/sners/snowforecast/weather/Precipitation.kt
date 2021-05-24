package com.sners.snowforecast.weather

import com.sners.snowforecast.data.*

//precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour)
//	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all).
//	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation,
//	0.002 in./hr. corresponds to very light precipitation,
//	0.017 in./hr. corresponds to light precipitation,
//	0.1 in./hr. corresponds to moderate precipitation,
//	and 0.4 in./hr. corresponds to heavy precipitation.

/**
 * Everything about Precipitation
 * @param daily daily weather data
 * @param hourly hourly weather data
 * @param minutely minutely weather data
 * @param currently current weather data
 */
class Precipitation(private val daily: Daily?, private val hourly: Hourly?,
                    private val minutely: Minutely?, private val currently: Currently
) {

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

    /**
     *  @property milsPerHour Mils per hour
     */
    val milsPerHour : Float
            get() {
                val mils = maxOf(currently.precipIntensity, hourly!!.hourlyData[0].precipIntensity)
                return mils
            }

    /**
     *  @property milsPerHour Mils per hour String
     */
    val milsPerHourString : String
        get() {
            return "$milsPerHour ${WeatherConstants.MM_HR}"
        }

    /**
     * Time til Precipitation
     * @param toIgnoreLightPrecip boolean
     * @return Minutes til precipitation
     */
    private fun timeTil(toIgnoreLightPrecip: Boolean): Long {

        val minPrecip = if (toIgnoreLightPrecip) minPrecipMedium else minPrecipLight

        //  Case = currently -> 0
        //  Case = minutely -> minutesTil
        //  Case = hourly -> hoursTil * 60
        //  Case = daily -> daysTil * 60 * 24
        val minutesTilPrecip : Int = when {
            //  It's raining now
            currently.precipIntensityNum > minPrecip -> 0
            //  Check if it will rain in minutes
            null != minutely && timeTilRain(minutely.minutelyData, minPrecip, 1) >=0 ->
                timeTilRain(minutely.minutelyData, minPrecip, 1)
            //  Check if it will rain in hours
            null != hourly && timeTilRain(hourly.hourlyData, minPrecip, 1) >=0 ->
                timeTilRain(hourly.hourlyData, minPrecip, 60)
            //  Check if it will rain in days
            null != daily && timeTilRain(daily.dailyData, minPrecip, 1) >=0 ->
                timeTilRain(daily.dailyData, minPrecip, 60 * 24)
            //  No rain forecast
            else -> -1
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
        val timeTilNum = timeTil(toIgnoreLightPrecip)
        //  If it's going to rain in 1 minute, count it as raining now
        if (timeTilNum > 1) {
            timeTilString = weatherHelper.formatTime(timeTilNum)
        } else if (timeTilNum >= 0) {
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
        //  -1 means not found -> fully aligns with my needs
        return intervalData.indexOfFirst { it.precipIntensity > minValue }
    }

    /**
     * Time til Rain
     * @param intervalData the data to search
     * @param minValue The minimum value under which we ignore
     * @param multiplier A number to convert the interval number to a time
     * @return Integer index of element where condition was first met
     */
    private fun timeTilRain(intervalData: ArrayList<IntervalData>, minValue: Double, multiplier: Int): Int {

        val rainInterval = periodWhenIntensityExceeded(intervalData, minValue)
        return if (rainInterval >= 0)  (rainInterval + 1) * multiplier else -1
    }

    /**
     * Time til PrecipType
     * @param precipType the weather word to search for
     * @return Integer index of element where condition was first met
     */
    fun timeTilPrecipTypeString(precipType: String): String? {

      val timeTil = timeTilPrecipType(precipType)
        var timeTilString: String? = WeatherConstants.NONE_FORECAST
        if (timeTil > 0) {
            timeTilString = weatherHelper.formatTime(timeTil.toLong())
        } else if (timeTil == 0) {
            timeTilString = WeatherConstants.NOW
        }
        return timeTilString
    }

    /**
     * Time til PrecipType
     * @param precipType the weather word to search for
     * @return Integer index of element where condition was first met
     */
    fun timeTilPrecipType(precipType: String):Int {

        val minutesTilPrecip : Int = when {
            //  It's raining now
            currently.weatherWords.contains(precipType) -> 0
            //  Check if it will rain in minutes
            null != minutely && timeTilWeatherWordFound(minutely.minutelyData, precipType, 1) >=0 ->
                timeTilWeatherWordFound(minutely.minutelyData, precipType, 1)
            //  Check if it will rain in hours
            null != hourly && timeTilWeatherWordFound(hourly.hourlyData, precipType, 1) >=0 ->
                timeTilWeatherWordFound(hourly.hourlyData, precipType, 60)
            //  Check if it will rain in days
            null != daily && timeTilWeatherWordFound(daily.dailyData, precipType, 1) >=0 ->
                timeTilWeatherWordFound(daily.dailyData, precipType, 60 * 24)
            //  No rain forecast
            else -> -1
        }

        return minutesTilPrecip
    }

    /**
     * Period when Weather Word Found
     * @param intervalData the data to search
     * @param precipType the weather word to search for
     * @param multiplier * number of minutes to multiply by
     * @return Integer index of element where condition was first met
     */
    private fun timeTilWeatherWordFound(intervalData: ArrayList<IntervalData>, precipType: String, multiplier: Int): Int {
        //  -1 means not found -> fully aligns with my needs
        val periodFound = intervalData.indexOfFirst { it.weatherWords.contains(precipType) }
        return if (periodFound == -1) -1 else periodFound * multiplier
    }


}