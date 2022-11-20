package com.sners.snowforecast.apicall

import kotlin.Throws
import com.sners.snowforecast.ForecastMainActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 * An object responsible for building the WeatherBit url, sending and getting the results
 */
object WeatherBitReader {
    /*
     * timelineRequest - Requests Timeline Weather API data using native classes such as HttpURLConnection
     * @param latitude Target latitude
     * @param longitude Target longitude
     * @param apiKey API key from the secrets xml
   */
    @JvmStatic
    @Throws(Exception::class)
    fun timelineRequest(latitude: Double, longitude: Double, apiKey: String?) {
        val apiEndPoint = "https://api.weatherbit.io/v2.0/forecast/minutely"

        val method = "GET" // GET OR POST


        //Build the URL pieces
        val requestBuilder = StringBuilder(apiEndPoint)


        //Build the parameters to send via GET or POST
        val paramBuilder = StringBuilder()
        paramBuilder.append(String.format("lat=%f&lon=%f", latitude, longitude))
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

        //  This needs to go back to a callback, I know - but not doing it yet
        ForecastMainActivity.rawMinutely = response.toString()
    }
}