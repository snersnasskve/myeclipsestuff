package com.sners.snowforecast.data

import org.json.JSONObject
import org.json.JSONException
import java.util.ArrayList

//  You can only inherit from an open class
/**
 * An abstract class for weather data classes
 */
 abstract class IntervalData {
    /**
     * *  @property precipIntensity Precipitation as read from the json
     */
    var precipIntensity = 0f
        protected set

    /**
     * *  @property precipProbability Precipitation probability as read from the json
     */
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

    /**
     *  @property windDir Wind direction
     */
    var windDir = -1f
        protected set

    /**
     *  @property temperature Temperature Celcius
     */
    var temperature = 0f
        protected set

    /**
     *  @property tempFeelsLike Feels like temperature Celcius
     */
    var tempFeelsLike = 0f
        protected set

    /**
     *  @property weatherWords Weather words found in the json
     */
    var weatherWords = ArrayList<String>()
        protected set

    /**
     *  Json value for
     *  @param fieldName Json field name
     *  @param jsonData The json object where data should be foud
     *  @return result as string
     */
    protected fun jsonValueFor(fieldName: String, jsonData: JSONObject): String? {
        val result = try {
            jsonData.getString(fieldName)
        } catch (e: JSONException) {
            //e.printStackTrace();
            null
        }
        return result
    }

    /**
     *  Json value for
     *  @param fieldName Json field name
     *  @param jsonData The json object where data should be found
     *  @return result as optional Float
     */
    protected fun jsonFloatValueFor(fieldName: String, jsonData: JSONObject): Float? {
        var result : Float? = null
        if (!jsonData.isNull(fieldName)) {
            result = jsonData.getDouble(fieldName).toFloat()
        }
        return result
    }

}