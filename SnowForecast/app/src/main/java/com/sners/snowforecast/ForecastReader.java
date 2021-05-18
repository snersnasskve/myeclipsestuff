package com.sners.snowforecast;


import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.sners.snowforecast.weather.WeatherConstants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ForecastReader {
    /*
     *  * Now going to try Climacell because they have probability
     * 1gYIV8J35T6IfmIStb0zT0FyfxChvN8c
     *https://www.c-sharpcorner.com/article/getting-started-with-climacell-weather-api/
     * https://towardsdatascience.com/building-production-level-weather-visualizer-app-in-a-day-e360a68116c7
     *
     * c# is nearly the same as java ... maybe!
     */

    //	sunsetTime is not allowed for 1m or 1h timesteps


    final private static List<String> minutelyFields = Arrays.asList(WeatherConstants.HUMIDITY,
            WeatherConstants.TEMPERATURE, WeatherConstants.PRECIP_INTENSITY,
            WeatherConstants.PRECIP_PROBABILITY, WeatherConstants.PRECIP_TYPE,
            WeatherConstants.CLOUD_COVER,
            WeatherConstants.WEATHER_CODE, WeatherConstants.WIND_SPEED);
    final private static List<String> dailyFields = Arrays.asList(WeatherConstants.HUMIDITY,
            WeatherConstants.TEMPERATURE, WeatherConstants.PRECIP_INTENSITY,
            WeatherConstants.PRECIP_PROBABILITY, WeatherConstants.PRECIP_TYPE,
            WeatherConstants.CLOUD_COVER,
            WeatherConstants.WEATHER_CODE, WeatherConstants.WIND_SPEED,
            WeatherConstants.SUNRISE_TIME, WeatherConstants.SUNSET_TIME);
    //private static String fields  = "humidity,temperature,weatherCode,precipitationIntensity,
    // precipitationProbability,precipitationType,sunsetTime,cloudCover,windSpeed";
    private static String urlTemplate = "{url}{period}?lat={lat}&lon={lon}&unit_system={unit}&start_time=now&fields={fields}&apikey={api_key}";
    //	private static String urlTemplateRealtime = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m,1h,1d";
    private static String urlTemplateRealtime = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m,1h,1d";
    private static String hourUrlTemplate = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1h,1d";
    private static String minuteUrlTemplate = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&startTime=START&endTime=END&fields=FIELD_NAME&timesteps=1m";


    private static String apiKey = "1gYIV8J35T6IfmIStb0zT0FyfxChvN8c";

    //private String jsonData;

    public ForecastReader() {

    }

    public String readWeatherForecast(Double latitude, Double longitude, String timePeriod) {

        ForecastMainActivity.weatherData = null;
        //jsonData = null;
        String startDate = getFormattedDate(0);
        String endDate = getFormattedDate(1);
        String forecastUrl = hourUrlTemplate;
        if (timePeriod.equals("1m")) {
            forecastUrl = minuteUrlTemplate;
            forecastUrl = forecastUrl.replace("FIELD_NAME", "" + fieldsToString(minutelyFields));
            forecastUrl = forecastUrl.replace("START", startDate);
            forecastUrl = forecastUrl.replace("END", endDate);
        }
        else {
            forecastUrl = hourUrlTemplate;
            forecastUrl = forecastUrl.replace("FIELD_NAME", "" + fieldsToString(dailyFields));

        }
        forecastUrl = forecastUrl.replace("LAT", "" + latitude);
        forecastUrl = forecastUrl.replace("LONG", "" + longitude);


        forecastUrl = forecastUrl + "&apikey=" + apiKey;


        //DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        //HttpGet httpGet				= new HttpGet(forecastUrl);

        String result = null;

        // New
        URL url = null;
        try {
            url = new URL(forecastUrl);
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    // so this is mine below
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }
                    result = sBuilder.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                    result = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = null;
        }


        if (null != result) {
            Log.i("jsonData", result);
            //	Set up the data weather object
            if (timePeriod.equals("1m")) {
                ForecastMainActivity.rawMinutely = result;
            }
            else {
                ForecastMainActivity.rawHourly = result;
            }
        } else {
            Log.i("jsonData", "null");
        }
        //jsonData = result;
        return result;
    }    //		readWeatherForecast

    //	Thank you to kind internet person for supplying a basic function
    //		Which really should be part of any language
    String fieldsToString(List<String> reqFields) {

        StringBuilder sbString = new StringBuilder("");

        //iterate through ArrayList
        for (String fieldName : reqFields) {

            //append ArrayList element followed by comma
            sbString.append(fieldName).append(",");
        }

        //convert StringBuffer to String
        String strList = sbString.toString();

        //remove last comma from String if you want
        if (strList.length() > 0)
            strList = strList.substring(0, strList.length() - 1);

        return strList;

    }

    private String getFormattedDate(Integer offsetHours) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
// remove next line if you're always using the current time.
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR, offsetHours);
        Date adjustedDate = cal.getTime();
        TimeZone tz = cal.getTimeZone();

        DateTimeFormatter df = DateTimeFormatter.ISO_ZONED_DATE_TIME;

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String isoDateString = sdf.format(adjustedDate);
        String urlEncodedDate = "";
        try {
            urlEncodedDate = URLEncoder.encode(isoDateString, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // just send an empty string. Summats up
        }
        return urlEncodedDate;
    }
}

