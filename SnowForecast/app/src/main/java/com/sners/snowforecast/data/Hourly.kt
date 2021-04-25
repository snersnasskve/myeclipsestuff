package com.sners.snowforecast.data

import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A class for managing hourly weather conditions
 * It takes the raw json and converts it into a data structure
 * @param hourlyArray The raw current data coming in from the API call
 */
class Hourly(hourlyArray: JSONArray) {

    /**
     *  @property hourlyData Array of data objects
     */
    var hourlyData: ArrayList<IntervalData>

    /**
     *  @property numPointsToPlot Number of points to plot in hour graphs
     */
    val numPointsToPlot = 48

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
     * Get max precip
     * @return Maximimum precip for the period
     */
    fun getMaxPrecip(): Float {
        if (maxPrecip < 0.0f) {
            //	only calculate it once
            for (hourCounter in 0..numPointsToPlot - 1) {
                val precip = hourlyData[hourCounter].getPrecipIntensity()
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

        maxPrecip = -1.0f
        hourlyData = ArrayList()
        try {
            summary = "Please implement me"
            icon = "clear day"
            var beforeNow = true
            val rightNow = Calendar.getInstance()
            val hourNow = rightNow[Calendar.HOUR_OF_DAY]
            for (intervalCounter in 0..hourlyArray.length() - 1) {
                val dataInst = HourlyData(hourlyArray.getJSONObject(intervalCounter))
                //  This API returns hours in the past, it starts at 0am today
                //  We only want from the current hour onwards
                if (beforeNow) {
                    val hourString = dataInst.getTime().subSequence(0, 2) as String
                    val hourInst = Integer.parseInt(hourString)
                    if (hourInst >= hourNow) {
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
}