package com.sners.snowforecast.data

import com.sners.snowforecast.weather.WeatherConstants
import com.sners.snowforecast.weather.WeatherHelper
import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A class for managing hourly weather conditions
 * It takes the raw json and converts it into a data structure
 * @param hourlyArray The raw current data coming in from the API call
 * @param currentDateUnix The reference date used for working out when is now
 */
class Hourly(hourlyArray: JSONArray, val currentDateUnix : Long) {

    /**
     *  @property hourlyData Array of data objects
     */
    var hourlyData: ArrayList<IntervalData>

    /**
     *  @property summary Weather summary for the hour
     */
    var summary: String? = null

    /**
     *  @property icon String indicating which icon to use
     */
    var icon: String? = null

    /**
     *  @property maxPrecip Max precipitation over all the graphable hours
     */
    private var maxPrecip: Float

    /**
     *  @property weatherHelper A class for common weather functions
     */
    private val weatherHelper: WeatherHelper

    /**
     * Get max precip
     * @return Maximimum precip for the period
     */
    fun getMaxPrecip(): Float {
        if (maxPrecip < 0.0f) {
            //	only calculate it once
            for (hourCounter in 0..WeatherConstants.HOURLY_NUM_POINTS_TO_PLOT - 1) {
                val precip = hourlyData[hourCounter].precipIntensity
                if (precip > maxPrecip) {
                    maxPrecip = precip
                }
            }
        }
        return maxPrecip
    }

    /*
  Constructor
   */
    init {
        weatherHelper = WeatherHelper()
        maxPrecip = -1.0f
        hourlyData = ArrayList()
        try {
            summary = "Please implement me"
            icon = "clear day"
            var beforeNow = true

            for (intervalCounter in 0..hourlyArray.length() - 1) {
                val dataInst = HourlyData(hourlyArray.getJSONObject(intervalCounter))
                //  This API returns hours in the past, it starts at 0am today
                //  We only want from the current hour onwards
                if (beforeNow) {

                    if (dataInst.timeUnix ?: Long.MAX_VALUE >= currentDateUnix) {
                        beforeNow = false
                    }
                }
                if (!beforeNow) {
                    hourlyData.add(dataInst)
                }
            }
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    /**
     *  @property tempFeelsLike Feels-like Temperature
     */
    val tempFeelsLike = hourlyData[0].tempFeelsLike

    /**
     *  @property humidity Humidity
     */
    val humidity: Float
        get() = hourlyData[0].humidity

    /**
     *  @property cloudCover Cloud cover as a formatted string
     */
    val cloudCoverString: String
        get() = weatherHelper.probabilityToPercent(cloudCover)

    /**
     *  @property dewPoint Dew point temperature as a number
     */
    val dewPoint = hourlyData[0].dewPoint

    /**
     *  @property cloudCover Cloud Cover
     */
    val cloudCover = hourlyData[0].cloudCover

}