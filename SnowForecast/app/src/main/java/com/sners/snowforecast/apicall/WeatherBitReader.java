package com.sners.snowforecast.apicall;

import com.sners.snowforecast.ForecastMainActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherBitReader {
    public static final String YOUR_API_KEY = "15097933a2e2423b881887d155d7c2c3";

    /*
     * timelineRequest - Requests Timeline Weather API data using native classes such as HttpURLConnection
     *
     */
    public static void timelineRequest(Double latitude, Double longitude) throws Exception {
        String apiEndPoint = "https://api.weatherbit.io/v2.0/forecast/minutely";

        String apiKey = YOUR_API_KEY; //sign up for a free api key at https://www.visualcrossing.com/weather/weather-data-services


        String method = "GET"; // GET OR POST


        //Build the URL pieces
        StringBuilder requestBuilder = new StringBuilder(apiEndPoint);


        //Build the parameters to send via GET or POST
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append(String.format("lat=%f&lon=%f", latitude, longitude));
        paramBuilder.append("&").append("key=").append(apiKey);


        //for GET requests, add the parameters to the request
        if ("GET".equals(method)) {
            requestBuilder.append("?").append(paramBuilder);
        }

        //set up the connection
        URL url = new URL(requestBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        //check the response code and set up the reader for the appropriate stream
        int responseCode = conn.getResponseCode();
        boolean isSuccess = responseCode == 200;
        StringBuffer response = new StringBuffer();
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(isSuccess ? conn.getInputStream() : conn.getErrorStream()))
        ) {

            //read the response
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        if (!isSuccess) {
            System.out.printf("Bad response status code:%d, %s%n", responseCode, response.toString());


            return;
        }

        //pass the string response to be parsed and used
        //parseTimelineJson(response.toString());
        ForecastMainActivity.rawMinutely = response.toString();

    }

}
