package com.sners.snowforecast.data


import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A class for managing daily weather conditions
 * It takes the raw json and converts it into a data structure
 * @param dailyArray The raw current data coming in from the API call
 */
class Daily(dailyArray: JSONArray) {

    /**
     *  @property dailyData Array of data objects
     */
    val dailyData: ArrayList<IntervalData> = ArrayList()

    /**
     *  @property today A pointer to the first object of the daily array
     */
    private var today: DailyData?

    /**
     *  @property icon String indicating which icon to use
     */
    val icon: String? = null

    /**
     *  @property currDateString Date string as retrieved from today data object
     */
    var currDateString: String? = null

    /**
     *  @property tempMin Max temperature as retrieved from today data object
     */
    val tempMin: Float
        get() = today!!.tempMin

    /**
     *  @property tempMax Max temperature as retrieved from today data object
     */
    val tempMax: Float
        get() = today!!.tempMax

    /*
  Constructor
   */
    init {
        today = null

        try {
            for (intervalCounter in 0..dailyArray.length() - 1) {
                val dataInst = DailyData(dailyArray.getJSONObject(intervalCounter))
                dailyData.add(dataInst)

            }
            if (dailyData.size > 0) {
                val today = dailyData[0] as DailyData
                currDateString = today.date
            }
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        if (dailyData.size > 0) {
            today = dailyData[0] as DailyData
        }
    }
}