package com.sners.snowforecast.apicall

import com.sners.snowforecast.ForecastMainActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// https://github.com/visualcrossing/WeatherApi/blob/master/Java/com/visualcrossing/weather/samples/TimelineApiSample.java
object VisualCrossingReader {

    private const val YOUR_API_KEY = "ZXC2R4HVTLM93GKJTCSSF7L32"

    /*
     * timelineRequest - Requests Timeline Weather API data using native classes such as HttpURLConnection
     *
     */
    @JvmStatic
    @Throws(Exception::class)
    fun timelineRequest(latitude: Double?, longitude: Double?) {
        val apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        val unitGroup = "metric" //us,metric,uk
        val apiKey =
            YOUR_API_KEY //sign up for a free api key at https://www.visualcrossing.com/weather/weather-data-services
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

    private fun parseTimelineJson(rawResult: String?) {
        if (rawResult == null || rawResult.isEmpty()) {
            System.out.printf("No raw data%n")
            return
        }
        try {
            val timelineResponse = JSONObject(rawResult)
            val zoneId = ZoneId.of(timelineResponse.getString("timezone"))
            System.out.printf("Weather data for: %s%n", timelineResponse.getString("resolvedAddress"))
            val values = timelineResponse.getJSONArray("days")
            System.out.printf("Date\tMaxTemp\tMinTemp\tPrecip\tSource%n")
            for (i in 0 until values.length()) {
                val dayValue = values.getJSONObject(i)
                val datetime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId)
                val maxtemp = dayValue.getDouble("tempmax")
                val mintemp = dayValue.getDouble("tempmin")
                val pop = dayValue.getDouble("precip")
                val source = dayValue.getString("source")
                System.out.printf(
                    "%s\t%.1f\t%.1f\t%.1f\t%s%n",
                    datetime.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    maxtemp,
                    mintemp,
                    pop,
                    source
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}