package com.sners.snowforecast.data

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import com.sners.snowforecast.weather.WeatherConstants
import com.sners.snowforecast.weather.WeatherHelper

import org.json.JSONObject

import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit

/*
This class is used for managing data about the current weather
 */
/**
 * A class for accessing current conditions
 * It takes the raw json and converts it into a data structure
 * @param currentJson The raw current data coming in from the API call
 * @param currDateString This is not found in the currently data - needed for sunrise sunset calcs
 */
class Currently(
    currentJson: JSONObject?,
    var currDateString: String
) {

    /**
     *  @property sunriseTime Sunrise time after conversion to Date
     */
    private var sunriseTime: Date? = null

    /**
     *  @property sunsetTime Sunset time after conversion to Date
     */
    private var sunsetTime: Date? = null

    /**
     *  @property time Time used for calculating time ranges throughout
     */
    private var time: Date

    /**
     *  @property weatherHelper A class for common weather functions
     */
    private val weatherHelper: WeatherHelper

    /**
     *  @property currentlyData The data object containing current weather conditions
     */
    private val currentlyData: CurrentlyData

    /*
   Constructor
    */
    init {
        weatherHelper = WeatherHelper()
        //  Set up the data object
        currentlyData = CurrentlyData(currentJson!!)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        //  Make the dates from the separate date and time fields
        val sunriseString = "$currDateString ${currentlyData.getSunriseTime()}"
        val sunsetString = "$currDateString ${currentlyData.getSunsetTime()}"
        time = Date()
        try {
            sunriseTime = dateFormat.parse(sunriseString)
            sunsetTime = dateFormat.parse(sunsetString)
        } catch (e: ParseException) {
            e.printStackTrace()
            //	No date info;
        }
    }

    /**
     *  @property icon The weather word to use as an icon
     */
    val icon = currentlyData.icon

    /**
     *  @property precipIntensityNum Precipitatation as a number
     */
    val precipIntensityNum = currentlyData.precipIntensity

    /**
     *  @property precipIntensity Precipitatation as a formatteed string
     */
    val precipIntensity: String
        get() = "${currentlyData.precipIntensity}${WeatherConstants.MM_HR}"

    /**
     *  @property precipProbability Precipitation probability
     */
    val precipProbability = currentlyData.precipProbability

    /**
     *  @property precipProbabilityString Precipitation probability as a formatted string
     */
    val precipProbabilityString: String
        get() = weatherHelper.probabilityToPercent(precipProbability)

    /**
     *  @property temperature Temperature as a formatted string
     */
    val temperature: String
        get() = "${currentlyData.temperature}${WeatherConstants.DEGREES_C}"

    /**
     *  @property temperature Temperature as a number
     */
    val temperatureNum = currentlyData.temperature

    /**
     *  @property tempFeelsLike Feels-like Temperature
     */
    val tempFeelsLike = currentlyData.tempFeelsLike


    /**
     *  @property windSpeed Wind speed
     */
    val windSpeed
        get() = currentlyData.windSpeed * WeatherConstants.KM_TO_MPH_CONVERSION

    /**
     *  @property windGusts Wind gusts
     */
    val windGusts
        get() = currentlyData.windGusts * WeatherConstants.KM_TO_MPH_CONVERSION

    /**
     *  @property windDir Wind direction
     */
    val windDir = currentlyData.windDir


    /**
     *  @property weatherWords Array of words describing the weather
     */
    val weatherWords = currentlyData.weatherWords


    /**
     *  @property headline A weather phrase describing current conditions
     */
    val headline = currentlyData.headline


    /**
     *  @property timeTilSunset Time till sunset in minutes - less than 0 means it is after sunset
     */
    val timeTilSunset: Long
        get() {
            val diffInMillies = sunsetTime!!.time - time.time
            return TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)
        }

    /**
     *  @property isDayTime A bool indicating whether it is currently between sunrise and sunset
     */
    val isDayTime: Boolean
        get() {
            //  Greater than 0 means the date is after the parameter
            return (time.compareTo(sunriseTime) > 0 &&
                    //  Less then 0 means the date is before the parameter
                    time.compareTo(sunsetTime) < 0)
        }


}