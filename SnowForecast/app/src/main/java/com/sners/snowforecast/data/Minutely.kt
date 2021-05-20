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
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}