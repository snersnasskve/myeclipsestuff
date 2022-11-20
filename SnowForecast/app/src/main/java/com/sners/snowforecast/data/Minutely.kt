package com.sners.snowforecast.data

import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A class for managing minutely weather conditions
 * It takes the raw json and converts it into a data structure
 * @param minutelyArray The raw current data coming in from the API call
 */
class Minutely(minutelyArray: JSONArray) {

    /**
     *  @property minutelyData Array of data objects
     */
    val minutelyData: ArrayList<IntervalData>

    /**
     *  @property maxPrecip Max precipitation over all the graphable hours
     */
    private var maxPrecip: Float

    /**
     *  @property currentMinute The first minute available
     */
    private val currentMinute: MinutelyData?
        get() {
            if (minutelyData.count() > 0) {
                return minutelyData[0] as MinutelyData
            } else {
                return null
            }
        }

    /**
     * localTime Time for current minute
     */
    var localDate : Date? = null
        private set

    /**
     * utcDate Time for current minute
     */
    var utcDate : Date? = null
        private set

    /**
     * currentDate Time for current minute
     */
    var currentDate : Date? = null
        private set

    /**
     * currentDateUnix Unix Time for current minute
     */
    var currentDateUnix : Long = 0
        private set


    /**
     * Get max precip
     * @return Maximimum precip for the period
     */
    fun getMaxPrecip(): Float {
        if (maxPrecip < 0.0f) {
            //	only calculate it once
            for (minute in minutelyData) {
                val precip = minute.precipIntensity
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
        minutelyData = ArrayList()
        try {
            for (intervalCounter in 0..minutelyArray.length() - 1) {
                val minuteObj = minutelyArray.getJSONObject(intervalCounter)
                val dataInst = MinutelyData(minuteObj)
                minutelyData.add(dataInst)

            }

            if (null != currentMinute) {
                currentDateUnix = currentMinute!!.unixTime ?: 0
                currentDate = Date(currentDateUnix  * 1000L)
            }

        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }



}