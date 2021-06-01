package com.sners.snowforecast.weather

import com.sners.snowforecast.data.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * A class to abstract the data from the view
 * This is kind of a controller class despite being called data
 * The word data is here in order to tell callers to come here for data
 *
 * @param rawMinutely The raw minutely data coming in from the API call.
 * @param rawHourly The raw hourly data coming in from the API call.
 * @constructor Turn the raw data into proper data classes.
 */
class WeatherData(rawMinutely: String, rawHourly: String) {

    /**
     * @property currently Info about Current weather
     */
    var currently: Currently? = null
        private set

    /**
     * @property minutely Info about minutely weather
     */
    var minutely: Minutely? = null
        private set

    val minutelyData: ArrayList<IntervalData>
        get() = minutely!!.minutelyData

    /**
     * @property hourly  Info about hourly weather
     */
    var hourly: Hourly? = null
        private set

    val hourlyData: ArrayList<IntervalData>
        get() = hourly!!.hourlyData

    /**
     * @property daily  Info about daily weather
     */
    private var daily: Daily? = null


    /**
     * @property alerts Info about alerts
     */
    var alerts: Alert? = null
        private set

    val alertsData: ArrayList<AlertData>?
        get() = alerts?.getAlertData()

    /**
     * @property wind Wind object - with all the wind info
     */
    var wind: Wind? = null

    /**
     * @property precipitation Precipitation with all the precipitation info
     */
    var precipitation: Precipitation? = null

    /**
     * @property weatherHelper Weather helper class
     */
    private val weatherHelper = WeatherHelper()

    /**
     * @property headlineSummary topline weather summary
     */
    val headlineSummary: String
        get() = currently?.headline ?: "Unknown weather"

    /**
     * @property headlineIcon name of weather icon to use
     */
    var headlineIcon: String? = null
        private set

    /**
     * @property minutesTilSunset Minutes til sunset - calculate once
     */
    private var minutesTilSunset: Long = -1

    init {

        setUpDataArrays(rawMinutely, rawHourly)

        //	Replace with night time icon if available
        headlineIcon = (currently?.icon ?: "").replace("-", "_").toLowerCase(Locale.ROOT)
        if (headlineIcon.equals("clear") || headlineIcon.equals("partly_cloudy")) {
            headlineIcon = if (headlineIcon!!.contains("_day") && isDayTime()) {
                headlineIcon.toString() + "_day"
            } else {
                headlineIcon.toString() + "_night"
            }
        }
        wind = Wind(hourly!!.hourlyData, currently!!)
        precipitation = Precipitation(daily, hourly, minutely, currently!!)
    }

    //  This stuff changes with every api
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Set up data arrays
     * @param rawMinutely Minutely data as received from the api
     * @param rawDaily Daily data as received from the api
     */
    private fun setUpDataArrays(rawMinutely: String?, rawDaily: String?) {

        //  Minutely is independent
        val jsonObjMinutely: JSONObject?
        try {
            jsonObjMinutely = JSONObject(rawMinutely ?: "")
            val minutelyTimelinesArray = jsonObjMinutely.getJSONArray(WeatherConstants.DATA)
            minutely = Minutely(minutelyTimelinesArray)
        } catch (e: JSONException) {
            //  We have no minutely data
            e.printStackTrace()
        }
        try {
            val jsonObjDaily = JSONObject(rawDaily ?: "")
            val dailyTimelineArray = jsonObjDaily.getJSONArray(WeatherConstants.DAILY)
            //  Unpack the hourly data from the within the daily Json ana make a separate array
            val hourlyTimelineArray = JSONArray()
            for (dCounter in 0 until dailyTimelineArray.length()) {
                val thisDay = dailyTimelineArray.getJSONObject(dCounter)
                val thisDayHours = thisDay.getJSONArray(WeatherConstants.HOURLY)
                for (hCounter in 0 until thisDayHours.length()) {
                    val thisHour = thisDayHours.getJSONObject(hCounter)
                    hourlyTimelineArray.put(thisHour)
                }
            }
            hourly = Hourly(hourlyTimelineArray)
            daily = Daily(dailyTimelineArray)
            currently = Currently(
                jsonObjDaily.getJSONObject(WeatherConstants.CURRENTLY),
                daily!!.currDateString!!
            )
            //  Currently needs the sunrise and sunset, which currently is not picking up
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    /**
     * Is Day Time
     * @return boolean
     */
    fun isDayTime(): Boolean {

        return currently!!.isDayTime
    }

    /**
     * Get time to sunset as string
     * @return formatted time till sunset
     */
    fun getTimeTilSunsetString(): String {

        return weatherHelper.formatTime(getTimeTilSunset())
    }

    /**
     * Get time to sunset with time now
     * @return formatted time till sunset with time now in brackets
     */
    fun getTimeTilSunsetWithTimeNow(): String {

        return String.format("%s (now: %s)",
            weatherHelper.formatTime(getTimeTilSunset()),
            currently!!.timeAsHms)
    }

    /**
     * Get time to sunset as number
     * @return minutes til sunset
     */
    fun getTimeTilSunset(): Long {

        //	If it's night this will be negative
        minutesTilSunset = currently!!.timeTilSunset
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
            wordFound = minutely!!.minutelyData.any { it.weatherWords.contains(weatherWord) }

        } else if (timeKeyWord == WeatherConstants.HOURLY) {
            wordFound = hourly!!.hourlyData.any { it.weatherWords.contains(weatherWord) }
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
            currently!!.tempFeelsLike,
            WeatherConstants.DEGREES_C,
            daily!!.tempMin,
            daily!!.tempMax
        )
    }

    /**
     * Get moisture summary
     * @return A string containing moisture information
     */
    fun getMoistureSummary(): String {

        return String.format(
            "H:%.0f / D:%.0f / %.0f",
            currently!!.humidity,
            currently!!.dewPoint,
            currently!!.cloudCover
        )
    }
}
