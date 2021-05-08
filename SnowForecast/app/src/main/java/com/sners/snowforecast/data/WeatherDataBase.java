package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WeatherDataBase {


    private Currently currently;
    private Minutely minutely;
    private Hourly hourly;
    protected Daily daily;
    private Alert alert;

    public Wind wind;

    public Precipitation precipitation;


    protected final WeatherHelper weatherHelper = new WeatherHelper();

    protected String headlineIcon;




    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public WeatherDataBase(String rawMinutely, String rawHourly) {

        setUpDataArrays(rawMinutely, rawHourly);



        //	Replace with night time icon if available
        headlineIcon = currently.getIcon().replaceAll("-", "_").toLowerCase();
        if (headlineIcon.equals("clear") || headlineIcon.equals("partly_cloudy")) {
            if (headlineIcon.contains("_day") && isDayTime()) {
                headlineIcon = headlineIcon + "_day";
            } else {
                headlineIcon = headlineIcon + "_night";
            }
        }

        wind = new Wind(hourly.getHourlyData(), currently);
        precipitation = new Precipitation(daily, hourly, minutely, currently);

    }

    //  This stuff changes with every api
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    void setUpDataArrays(String rawMinutely, String rawDaily) {
        //  Minutely is independent
        JSONObject jsonObjMinutely = null;
        try {
            jsonObjMinutely = new JSONObject(rawMinutely);
            JSONArray minutelyTimelinesArray = jsonObjMinutely.getJSONArray(WeatherConstants.DATA);
            minutely = new Minutely(minutelyTimelinesArray);
        } catch (JSONException e) {
            //  We have no minutely data
            e.printStackTrace();
        }

        try {
            JSONObject jsonObjDaily = new JSONObject(rawDaily);
            JSONArray dailyTimelineArray = jsonObjDaily.getJSONArray(WeatherConstants.DAILY);
            // There must be a oneliner way of doing this, but I don't know it
            JSONArray hourlyTimelineArray = new JSONArray();
            for (int dCounter = 0; dCounter < dailyTimelineArray.length(); dCounter++) {
                JSONObject thisDay = dailyTimelineArray.getJSONObject(dCounter);
                JSONArray thisDayHours = thisDay.getJSONArray(WeatherConstants.HOURLY);
                for (int hCounter = 0; hCounter < thisDayHours.length(); hCounter++) {
                    JSONObject thisHour = thisDayHours.getJSONObject(hCounter);
                    hourlyTimelineArray.put(thisHour);
                }
            }
            hourly = new Hourly(hourlyTimelineArray);
            daily = new Daily(dailyTimelineArray);
            currently = new Currently(jsonObjDaily.getJSONObject(WeatherConstants.CURRENTLY),
            daily.getCurrDateString());
            //  Currently needs the sunrise and sunset, which currently is not picking up

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isDayTime() {
        return currently.isDayTime();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////
//	Daily stuff  --  Can't go into daily as need to compare against currently
////////////////////////////////////////////////////////////////////////////////////////////////////////











    //	Getters
    public Currently getCurrently() {
        return currently;
    }

    public ArrayList<IntervalData> getMinutelyData() {
        if (null == minutely) {
            return null;
        } else {
            return minutely.getMinutelyData();
        }
    }


    public Minutely getMinutely() {
        return minutely;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<IntervalData> getHourlyData() {
        if (null == hourly) {
            return null;
        } else {
            return hourly.getHourlyData();
        }
    }



    public Hourly getHourly() {
        return hourly;
    }

    public Alert getAlerts() {
        return alert;
    }

    public ArrayList<AlertData> getAlertsData() {
        return alert.getAlertData();
    }




}
