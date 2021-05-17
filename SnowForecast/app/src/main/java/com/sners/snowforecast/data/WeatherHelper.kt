package com.sners.snowforecast.data

import com.sners.snowforecast.data.WeatherConstants
import com.sners.snowforecast.data.WeatherHelper
import java.util.*

class WeatherHelper {
    //  For displaying temperatures
    var knownWeatherWords = arrayOf(
        "Snow", "snow", "Rain", "rain", "Drizzle", "drizzle",
        "Flurry", "flurry", "Hail", "hail", "Storm", "storm"
    )

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    //	Format Strings
    ////////////////////////////////////////////////////////////////////////////////
    fun fahrenheitToCelsius(fahrenheit: String): Double {
        val fahr = fahrenheit.toDouble()
        return (fahr - 32) * 5 / 9
    }

    ////////////////////////////////////////////////////////////////////////////////
    fun precipIntensityToMilsFormatted(inchesString: String): String {
        val mils = precipIntensityToMils(inchesString)
        return String.format("%.1g%s", mils, WeatherConstants.MM_HR)
    }

    ////////////////////////////////////////////////////////////////////////////////
    fun precipIntensityToMils(inchesString: String): Float {
        val inches = inchesString.toFloat()
        return inches * 2.54f
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    fun probabilityToPercent(prob: Float): String {
        val perc = if (prob <= 1) prob * 100.0 else prob.toDouble()
        return String.format("%.1f %s", perc, WeatherConstants.PERCENT)
    }

    ////////////////////////////////////////////////////////////////////////////////
    fun formatTime(totalMinutes: Long): String {
        var hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return if (hours > 24) {
            val days = hours / 24
            hours = hours % 24
            String.format("%d days, %2d:%02d", days, hours, mins)
        } else {
            String.format("%d:%02d", hours, mins)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    fun weatherWordsFromString(anyString: String): ArrayList<String> {
        val foundWords = ArrayList<String>()
        for (wCounter in knownWeatherWords.indices) {
            if (anyString.contains(knownWeatherWords[wCounter])) {
                foundWords.add(knownWeatherWords[wCounter].toLowerCase(Locale.ENGLISH))
            }
        }
        return foundWords
    }

    ////////////////////////////////////////////////////////////////////////////////
    //	SummaryForWeatherCode
    ////////////////////////////////////////////////////////////////////////////////
    fun summaryForWeatherCode(weatherCode: Int): String? {
        var weatherWord: String? = "Unidentified Weather"
        if (weatherCodes.containsKey(weatherCode)) {
            weatherWord = weatherCodes[weatherCode]
        }
        return weatherWord
    }

    fun iconForWeatherCode(weatherCode: Int): String? {
        var weatherWord: String? = "clear_day"
        if (iconCodes.containsKey(weatherCode)) {
            weatherWord = iconCodes[weatherCode]
        }
        return weatherWord
    }

    companion object {
        private val weatherCodes = HashMap<Int, String>()
        private val iconCodes = HashMap<Int, String>()
        private val precipTypes = HashMap<Int, String>()
    }
}