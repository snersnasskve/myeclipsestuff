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

     /**
     * Data Contains Weather word
     * @return bool indicating whether it contains the word
     */
    fun dataContainsWeatherword(weatherWord: String?, timeKeyWord: String): Boolean {
        var wordFound = false
        if (timeKeyWord == WeatherConstants.MINUTELY) {
            wordFound = minutely.minutelyData.any { it.weatherWords.contains(weatherWord) }

        } else if (timeKeyWord == WeatherConstants.HOURLY) {
            wordFound = hourly.hourlyData.any { it.weatherWords.contains(weatherWord) }
        }
        return wordFound
    }

    /**
     * Get temperature summary
     * @return A string containing temperature information
     */
    fun getTemperatureSummary(): String {
        return String.format(
            "F/L %.1f%s ( %.1f : %.1f )",
            currently.tempFeelsLike,
            WeatherConstants.DEGREES_C,
            daily.tempMin,
            daily.tempMax
        )
    }

}
