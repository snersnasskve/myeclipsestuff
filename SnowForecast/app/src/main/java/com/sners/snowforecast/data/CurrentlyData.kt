package com.sners.snowforecast.data

import org.json.JSONException
import org.json.JSONObject
import java.util.*

/*
This class is a convenience summary.
The data it uses comes from minutely, but it calculates things once
 */
/**
 * A data class of current conditions
 * It takes the raw json and converts it into a data structure
 * @param currentJson The raw current data coming in from the API call
 */
class CurrentlyData(currentJson: JSONObject) {
    /**
     *  @property headline Top level summary
     */
    private var headline: String = "Not found"

    /**
     *  @property icon Name of the icon that represents current conditions
     */
    private var icon: String = ""

    /**
     *  @property precipIntensity Expected rain in mm/h usually
     */
    private var precipIntensity = 0f

    /**
     *  @property precipProbability Probability of rain 0-100
     */
    private var precipProbability = 0f

    /**
     *  @property temperature Temperature in degrees C
     */
    private var temperature = 0f

    /**
     *  @property tempFeelsLike Feels like temperature in degrees C
     */
    private var tempFeelsLike = 0f

    /**
     *  @property windSpeed Wind speed in metres per second usually
     */
    private var windSpeed = 0f

    /**
     *  @property windGusts Wind gust speed in metres per second usually
     */
    private var windGusts = 0f

    /**
     *  @property time Current time as read from the json
     */
    private var time: String? = null

    /**
     *  @property time Sunset time as read from the json
     */
    private var sunsetTime: String? = null

    /**
     *  @property time Sunrise time as read from the json
     */
    private var sunriseTime: String? = null
    private val weatherWords: ArrayList<String>? = null

    /*
  Constructor
   */
    init {
        try {
            /* For each property, unpack the Json string as appropriate */
            this.headline = currentJson.getString(WeatherConstants.CONDITIONS)
            icon = currentJson.getString(WeatherConstants.ICON)
            //  Times
            time = currentJson.getString(WeatherConstants.TIME)
            sunriseTime = currentJson.getString(WeatherConstants.SUNRISE_TIME)
            sunsetTime = currentJson.getString(WeatherConstants.SUNSET_TIME)
            //  Temperature
            temperature = currentJson.getDouble(WeatherConstants.TEMPERATURE).toFloat()
            tempFeelsLike = currentJson.getDouble(WeatherConstants.TEMP_FEELS_LIKE).toFloat()
            //  Precipitation
            precipIntensity = currentJson.getDouble(WeatherConstants.PRECIP_INTENSITY).toFloat()
            if (precipIntensity > 0) {
                //	This is null if no precipIntensity
                precipProbability = currentJson.getDouble(WeatherConstants.PRECIP_PROBABILITY).toFloat()
            }
            if (precipIntensity > 0 && precipProbability > 0) {
                val weatherWordsJson = currentJson.getJSONArray(WeatherConstants.PRECIP_TYPE)
                for (wordCounter in 0..weatherWordsJson.length() - 1) {
                    val precipType = weatherWordsJson.getString(wordCounter)
                    weatherWords!!.add(precipType)
                }
            }
            //  Wind
            windSpeed = currentJson.getDouble(WeatherConstants.WIND_SPEED).toFloat()
            windGusts = currentJson.getDouble(WeatherConstants.WIND_GUSTS).toFloat()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * Getters
     */
    fun getSunriseTime() : String {
        return if (null == this.sunriseTime) {
            ""
        } else {
            sunriseTime as String
        }
    }

    fun getSunsetTime() : String {
        return if (null == this.sunsetTime) {
            ""
        } else {
            sunsetTime as String
        }
    }

    fun getHeadline() : String {
        return headline
    }
    fun getIcon() : String {
        return icon
    }

    fun getPrecipIntensity() : Float {
        return precipIntensity
    }

    fun getPrecipProbability() : Float {
        return precipProbability
    }

    fun getTemperature() : Float {
        return temperature
    }

    fun getTempFeelsLike() : Float {
        return tempFeelsLike
    }

    fun getWindSpeed() : Float {
        return windSpeed
    }

    fun getWindGusts() : Float {
        return windGusts
    }
}

