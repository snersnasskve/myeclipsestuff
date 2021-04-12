package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WeatherDataBase {


    private Currently currently;
    private Minutely minutely;
    private Hourly hourly;
    private Daily daily;
    private Alert alert;

    private final WeatherHelper weatherHelper = new WeatherHelper();

    private String headlineSummary = "";
    private String headlineIcon;
    private long minutesTilSunset;



    enum TimePeriod {
        MINUTELY,
        HOURLY,
        DAILY,
        UNKNOWN
    }

    ;

    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public WeatherDataBase(String rawMinutely, String rawHourly) {

        setUpDataArrays(rawMinutely, rawHourly);

        //  Get some basic data extracted
        headlineSummary = currently.getHeadline();
        //	Replace with night time icon if available
        headlineIcon = currently.getIcon().replaceAll("-", "_").toLowerCase();
        if (headlineIcon.equals("clear") || headlineIcon.equals("partly_cloudy")) {
            if (headlineIcon.contains("_day") && isDayTime()) {
                headlineIcon = headlineIcon + "_day";
            } else {
                headlineIcon = headlineIcon + "_night";
            }
        }


        minutesTilSunset = -1;

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

    public String getHeadlineSummary() {
        return headlineSummary;
    }

    public String getHeadlineIcon() {
        return headlineIcon;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //	Daily stuff  --  Can't go into daily as need to compare against currently
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Long getTimeTilSunset() {

        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html

        //	If it's night this will be negative
        minutesTilSunset = currently.getTimeTilSunset();
        if (minutesTilSunset < 0) {
            minutesTilSunset = 0;
        }

        return minutesTilSunset;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getTimeTilSunsetString() {

        return weatherHelper.formatTime(getTimeTilSunset());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isDayTime() {
        return currently.isDayTime();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////
//	Daily stuff  --  Can't go into daily as need to compare against currently
////////////////////////////////////////////////////////////////////////////////////////////////////////


    //precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour)
    //	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all).
    //	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation,
    //	0.002 in./hr. corresponds to very light precipitation,
    //	0.017 in./hr. corresponds to light precipitation,
    //	0.1 in./hr. corresponds to moderate precipitation,
    //	and 0.4 in./hr. corresponds to heavy precipitation.
    public long timeTilPrecip(boolean toIgnoreLightPrecip) {
        long minutesTilPrecip = -1;

        float minPrecip = 0.002f;
        if (toIgnoreLightPrecip) {
            minPrecip = 0.017f;
        }

        float precipIntensity = currently.getPrecipIntensityNum();

        if (precipIntensity > minPrecip) {
            //	It's raining now
            minutesTilPrecip = 0;
        } else {
            //	Check minutely
            if (null != minutely) {
                int rainMinutes = weatherHelper.periodWhenValueExceededPrecipIntensity(minutely.getMinutelyData(), minPrecip);
                if (rainMinutes >= 0) {
                    minutesTilPrecip = (rainMinutes + 1);
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != hourly) {
                    int rainHours = weatherHelper.periodWhenValueExceededPrecipIntensity(hourly.getHourlyData(), minPrecip);
                    if (rainHours >= 0) {
                        minutesTilPrecip = ((rainHours + 1) * 60);
                    }
                }
            }
            if (minutesTilPrecip < 1) {
                //	Check hourly
                if (null != daily) {
                    int rainDays = weatherHelper.periodWhenValueExceededPrecipIntensity(daily.getDailyData(), minPrecip);
                    if (rainDays >= 0) {
                        minutesTilPrecip = ((rainDays + 1) * 60 * 24);
                    } else {
                        minutesTilPrecip = -1;
                    }
                }
            }
        }


        return minutesTilPrecip;
    }

    public String timeTilPrecipString(boolean toIgnoreLightPrecip) {
        String timeTilString = WeatherConstants.NONE_FORECAST;
        long timeTil = timeTilPrecip(toIgnoreLightPrecip);
        if (timeTil > 0) {
            timeTilString = weatherHelper.formatTime(timeTil);
        } else if (timeTil == 0) {
            timeTilString = WeatherConstants.NOW;
        }

        return timeTilString;
    }


    //	tiemKeyWord: "daily", "hourly", "minutely", "currently"
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean dataContainsWeatherword(String weatherWord, String timeKeyWord) {
        boolean wordFound = false;

        if (timeKeyWord.equals(WeatherConstants.MINUTELY)) {
            for (IntervalData minute : minutely.getMinutelyData()) {
                if (minute.getWeatherWords().contains(timeKeyWord)) {
                    wordFound = true;
                    break;
                }
            }
        } else if (timeKeyWord.equals(WeatherConstants.HOURLY)) {
            for (IntervalData hour : hourly.getHourlyData()) {
                if (hour.getWeatherWords().contains(timeKeyWord)) {
                    wordFound = true;
                    break;
                }
            }
        }
        return wordFound;
    }

    ////////////////////////////////////////////////////////////////////////////////
    public String timeTilPrecipTypeString(String precipType) {
        long timeTil = -1;

        ArrayList<IntervalData> minutely = getMinutelyData();
        if (null != minutely) {
            for (int minCounter = 0; minCounter < minutely.size(); minCounter++) {
                if (minutely.get(minCounter).getWeatherWords().contains(precipType) ) {
                    timeTil = minCounter;
                    break;
                }
            }
        }

        if (0 > timeTil && null != hourly) {
            ArrayList<IntervalData> hourlyData = hourly.getHourlyData();
            for (int minCounter = 0; minCounter < hourlyData.size(); minCounter++) {
                if (hourlyData.get(minCounter).getWeatherWords().contains(precipType) ) {
                    timeTil = (minCounter * 60);
                }
            }
        }

        if (0 > timeTil && null != daily) {
            ArrayList<IntervalData> dailyData = daily.getDailyData();
            for (int minCounter = 0; minCounter < dailyData.size(); minCounter++) {
                if (dailyData.get(minCounter).getWeatherWords().contains(precipType)) {
                    timeTil = (minCounter * 60 * 60);
                }
            }
        }

        String timeTilString = WeatherConstants.NONE_FORECAST;

        if (timeTil > 0) {
            timeTilString = weatherHelper.formatTime(timeTil);
        } else if (timeTil == 0) {
            timeTilString = WeatherConstants.NOW;
        }
        return timeTilString;
    }

    ////////////////////////////////////////////////////////////////////////////////
    public String getTemperatureSummary() {
        String tempSummary = String.format("F/L %.1f%s ( %.1f : %.1f )",
                currently. getTempFeelsLike(),
                WeatherConstants.DEGREES_C,
                daily.getTempMin(),
                daily.getTempMax()
                );
        return tempSummary;
    }

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  WIND SPEED
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double getWindSpeedMph() {
        double windSpeed = currently.getWindSpeed();
        if (windSpeed < 1) {
            if (hourly.getHourlyData().size() > 0) {
                windSpeed = hourly.getHourlyData().get(0).getWindSpeed();
            }
        }
        return (windSpeed * WeatherConstants.KM_TO_MPH_CONVERSION);
    }

    public float getWindSpeedBeaufort() {
        return weatherHelper.windSpeedToBeaufort(getWindSpeedMph());
    }

    public String getWindSpeedBeaufortString() {
        return String.format("%s", weatherHelper.windSpeedToBeaufort(getWindSpeedMph()));
    }

    public String getWindSpeedMphString() {
        return String.format("%.1f mph", getWindSpeedMph());
    }



}
