
package com.sners.snowforecast.data

import com.sners.snowforecast.weather.WeatherConstants
import com.sners.snowforecast.weather.WeatherHelper
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

/*
This class is used for managing data about the current weather
 */
/**
 * A class for accessing current conditions
 * It takes the raw json and converts it into a data structure
 * @param currentJson The raw current data coming in from the API call
 * @param currDateUnix This is not found in the currently data - needed for sunrise sunset calcs
 */
class Currently(
    currentJson: JSONObject?,
    private var currDateUnix: Long,
    val weatherTimeZone: SimpleTimeZone
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
    private var time: Date = Date()

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
       // val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale(weatherTimeZone.toString()))
        //  Make the dates from the separate date and time fields
        sunriseTime = Date((currentlyData.sunriseEpoch?: 0) * 1000L)
        sunsetTime = Date((currentlyData.sunsetEpoch?: 0) * 1000L)

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
    val precipIntensity: Float
        get() = currentlyData.precipIntensity





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
     *  @property windSpeed Wind speed
     */
    val windSpeed
        get() = currentlyData.windSpeed

    /**
     *  @property windGusts Wind gusts
     */
    val windGusts
        get() = currentlyData.windGusts

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


    private var _timeTilSunset : Long? = null
    /**
     *  @property timeTilSunset Time till sunset in minutes - less than 0 means it is after sunset
     */
    val timeTilSunset: Long
        get() {
            if (null == _timeTilSunset) {
                val diffInMillies = sunsetTime!!.time - time.time
                _timeTilSunset = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)
            }
            return _timeTilSunset!!
        }

    private var _timeSinceSunrise : Long? = null   /**
     *  @property timeSinceSunrise Time since sunrise in minutes - less than 0 means it is before sunrise
     */
    val timeSinceSunrise: Long
        get() {
            if (null == _timeSinceSunrise) {
                val diffInMillies = time.time - sunriseTime!!.time
                _timeSinceSunrise = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)
            }
            return _timeSinceSunrise!!
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