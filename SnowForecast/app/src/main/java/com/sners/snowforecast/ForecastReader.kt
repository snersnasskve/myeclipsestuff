package com.sners.snowforecast

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.sners.snowforecast.weather.WeatherConstants
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastReader  //private String jsonData;
{
    fun readWeatherForecast(latitude: Double, longitude: Double, timePeriod: String): String? {
        ForecastMainActivity.weatherData = null
        //jsonData = null;
        val startDate = getFormattedDate(0)
        val endDate = getFormattedDate(1)
        var forecastUrl = if (timePeriod == "1m") {getMinutelyUrl(startDate, endDate)
        } else {
            getHourlyUrl()
        }
        forecastUrl = forecastUrl.replace("LAT", "" + latitude)
        forecastUrl = forecastUrl.replace("LONG", "" + longitude)
        forecastUrl = forecastUrl + "&apikey=" + apiKey


        //DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        //HttpGet httpGet				= new HttpGet(forecastUrl);
        var result: String? = null

        // New
        var url: URL? = null
        try {
            url = URL(forecastUrl)
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                try {
                    val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)

                    // so this is mine below
                    val bReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"), 8)
                    val sBuilder = StringBuilder()
                    var line: String? = null
                    while (bReader.readLine().also { line = it } != null) {
                        sBuilder.append(
                            """
    $line
    
    """.trimIndent()
                        )
                    }
                    result = sBuilder.toString()
                } catch (e: IOException) {
                    e.printStackTrace()
                    result = null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                result = null
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            result = null
        }
        if (null != result) {
            Log.i("jsonData", result)
            //	Set up the data weather object
            if (timePeriod == "1m") {
                ForecastMainActivity.rawMinutely = result
            } else {
                ForecastMainActivity.rawHourly = result
            }
        } else {
            Log.i("jsonData", "null")
        }
        //jsonData = result;
        return result
    } //		readWeatherForecast

    /**
     * Get Hourly URL
     * @return URL populated with hourly specific info
     */
    private fun getHourlyUrl(): String {
        var forecastUrl = hourUrlTemplate.replace("FIELD_NAME", "" +
                fieldsToString(dailyFields))
        return forecastUrl
    }

    /**
     * Get Minutely URL
     * @param v View representing the current view - comes in from the layout
     * @return URL populated with minutely specific info
     */
    private fun getMinutelyUrl(
        startDate: String,
        endDate: String
    ): String {
        var forecastUrl = minuteUrlTemplate.replace("FIELD_NAME", "" +
                fieldsToString(minutelyFields))
        .replace("START", startDate)
        .replace("END", endDate)
        return forecastUrl
    }

    //	Thank you to kind internet person for supplying a basic function
    //		Which really should be part of any language
    fun fieldsToString(reqFields: List<String?>): String {
        val sbString = StringBuilder("")

        //iterate through ArrayList
        for (fieldName in reqFields) {

            //append ArrayList element followed by comma
            sbString.append(fieldName).append(",")
        }

        //convert StringBuffer to String
        var strList = sbString.toString()

        //remove last comma from String if you want
        if (strList.length > 0) strList = strList.substring(0, strList.length - 1)
        return strList
    }

    private fun getFormattedDate(offsetHours: Int): String {
        val currentDate = Date()
        val cal = Calendar.getInstance()
        // remove next line if you're always using the current time.
        cal.time = currentDate
        cal.add(Calendar.HOUR, offsetHours)
        val adjustedDate = cal.time
        val tz = cal.timeZone
        val df = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val sdf: SimpleDateFormat
        sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        val isoDateString = sdf.format(adjustedDate)
        var urlEncodedDate = ""
        try {
            urlEncodedDate = URLEncoder.encode(isoDateString, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            // just send an empty string. Summats up
        }
        return urlEncodedDate
    }

    companion object {
        /*
     *  * Now going to try Climacell because they have probability
     * 1gYIV8J35T6IfmIStb0zT0FyfxChvN8c
     *https://www.c-sharpcorner.com/article/getting-started-with-climacell-weather-api/
     * https://towardsdatascience.com/building-production-level-weather-visualizer-app-in-a-day-e360a68116c7
     *
     * c# is nearly the same as java ... maybe!
     */
        //	sunsetTime is not allowed for 1m or 1h timesteps
        private val minutelyFields = Arrays.asList(
            WeatherConstants.HUMIDITY,
            WeatherConstants.TEMPERATURE, WeatherConstants.PRECIP_INTENSITY,
            WeatherConstants.PRECIP_PROBABILITY, WeatherConstants.PRECIP_TYPE,
            WeatherConstants.CLOUD_COVER,
            WeatherConstants.WEATHER_CODE, WeatherConstants.WIND_SPEED
        )
        private val dailyFields = Arrays.asList(
            WeatherConstants.HUMIDITY,
            WeatherConstants.TEMPERATURE, WeatherConstants.PRECIP_INTENSITY,
            WeatherConstants.PRECIP_PROBABILITY, WeatherConstants.PRECIP_TYPE,
            WeatherConstants.CLOUD_COVER,
            WeatherConstants.WEATHER_CODE, WeatherConstants.WIND_SPEED,
            WeatherConstants.SUNRISE_TIME, WeatherConstants.SUNSET_TIME
        )

        //private static String fields  = "humidity,temperature,weatherCode,precipitationIntensity,
        // precipitationProbability,precipitationType,sunsetTime,cloudCover,windSpeed";
        private const val urlTemplate =
            "{url}{period}?lat={lat}&lon={lon}&unit_system={unit}&start_time=now&fields={fields}&apikey={api_key}"

        //	private static String urlTemplateRealtime = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m,1h,1d";
        private const val urlTemplateRealtime =
            "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m,1h,1d"
        private const val hourUrlTemplate =
            "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1h,1d"
        private const val minuteUrlTemplate =
            "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&startTime=START&endTime=END&fields=FIELD_NAME&timesteps=1m"
        private const val apiKey = "1gYIV8J35T6IfmIStb0zT0FyfxChvN8c"
    }
}