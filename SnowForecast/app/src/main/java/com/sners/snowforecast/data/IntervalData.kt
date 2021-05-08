package com.sners.snowforecast.data

import org.json.JSONObject
import org.json.JSONException
import java.util.ArrayList

open class IntervalData {
    var precipIntensity = 0f
        protected set
    var precipProbability: Float = 0f
        protected set

    /**
     * *  @property time Time as read from the json
     */
    var time: String? = null
        protected set
    /**
     *  @property windSpeed Wind speed in metres per second usually
     */
    var windSpeed = 0.0
        protected set

    /**
     *  @property windGusts Wind gust speed in metres per second usually
     */
    var windGusts = 0f
      protected set

    var temperature = 0f
        protected set
    var tempFeelsLike = 0f
        protected set
    var weatherWords = ArrayList<String>()
        protected set

    protected fun jsonValueFor(fieldName: String?, jsonData: JSONObject): String? {
        var result: String? = null
        result = try {
            jsonData.getString(fieldName)
        } catch (e: JSONException) {
            //e.printStackTrace();
            null
        }
        return result
    }


}