package com.sners.snowforecast.apicall

import com.sners.snowforecast.ForecastMainActivity
import com.sners.snowforecast.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

// https://github.com/visualcrossing/WeatherApi/blob/master/Java/com/visualcrossing/weather/samples/TimelineApiSample.java
/**
 * An object responsible for building the Visual Crossing url, sending and getting the results
 * sign up for a free api key at https://www.visualcrossing.com/weather/weather-data-services
 */
object VisualCrossingReader {

    /*
     * timelineRequest - Requests Timeline Weather API data using native classes such as HttpURLConnection
     *
     */
    @JvmStatic
    @Throws(Exception::class)
    fun timelineRequest(latitude: Double?, longitude: Double?, apiKey: String) {
        //val apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        val apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        val unitGroup = "metric"  //us,metric,uk
        val method = "GET" // GET OR POST


        //Build the URL pieces
        val requestBuilder = StringBuilder(apiEndPoint)
        requestBuilder.append(String.format("%f,%f", latitude, longitude))
        //  Start date
        requestBuilder.append(String.format("/%s", getDateString(0)))
        //  End date
        requestBuilder.append(String.format("/%s", getDateString(8)))

        //Build the parameters to send via GET or POST
        val paramBuilder = StringBuilder()
        paramBuilder.append("unitGroup=").append(unitGroup)
        paramBuilder.append("&").append("key=").append(apiKey)


        //for GET requests, add the parameters to the request
        if ("GET" == method) {
            requestBuilder.append("?").append(paramBuilder)
        }

        //set up the connection
        val url = URL(requestBuilder.toString())
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"

        //check the response code and set up the reader for the appropriate stream
        val responseCode = conn.responseCode
        val isSuccess = responseCode == 200
        val response = StringBuffer()
        BufferedReader(InputStreamReader(if (isSuccess) conn.inputStream else conn.errorStream)).use { `in` ->

            //read the response
            var inputLine: String?
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
        }
        if (!isSuccess) {
            System.out.printf("Bad response status code:%d, %s%n", responseCode, response.toString())
            return
        }

        //pass the string response to be parsed and used
        //parseTimelineJson(response.toString());
        ForecastMainActivity.rawHourly = response.toString()
    }

    private fun getDateString(offsetTime: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, offsetTime)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(cal.time)
    }

}