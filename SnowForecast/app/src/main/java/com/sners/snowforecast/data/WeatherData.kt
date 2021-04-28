package com.sners.snowforecast.data

/**
 * A class to abstract the data from the view
 * This is kind of a controller class despite being called data
 * The word data is here in order to tell callers to come here for data
 *
 * @param rawMinutely The raw minutely data coming in from the API call.
 * @param rawHourly The raw hourly data coming in from the API call.
 * @constructor Turn the raw data into proper data classes.
 */
class WeatherData(rawMinutely: String, rawHourly: String) :
        WeatherDataBase(rawMinutely, rawHourly) {

    /**
     * @property minutesTilSunset Minutes til sunset - calculate once
     */
    private var minutesTilSunset: Long = -1



    /**
     * Get the headline summary for the weather pages
     * @return headline from currently data
     */
    fun getHeadlineSummary(): String? {
        return currently.headline
    }

    /**
     * Get the headline icon name
     * @return headlineIcon
     */
    fun getHeadlineIcon(): String? {
        return headlineIcon
    }

    /**
     * Get time to sunset as string
     * @return formatted time till sunset
     */
    fun getTimeTilSunsetString(): String? {
        return weatherHelper.formatTime(getTimeTilSunset())
    }

    /**
     * Get time to sunset as number
     * @return minutes til sunset
     */
    fun getTimeTilSunset(): Long {
      //	If it's night this will be negative
        minutesTilSunset = currently.timeTilSunset
        if (minutesTilSunset < 0) {
            minutesTilSunset = 0
        }
        return minutesTilSunset
    }


}
