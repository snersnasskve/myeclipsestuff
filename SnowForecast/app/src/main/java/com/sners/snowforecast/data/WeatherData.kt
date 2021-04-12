package com.sners.snowforecast.data

/**
 * A class to abstract the data from the view
 * This is kind of a controller class despite being called data
 * The word data is here in order to tell callers to come here for data
 *

 * @param rawMinutely The raw minutely data coming in from the API call.
 * @param rawHourly The raw hourly data coming in from the API call.
 * @constructor Turn the raw data into proper data classes.
 */
class WeatherData(rawMinutely: String, rawHourly: String) :
        WeatherDataBase(rawMinutely, rawHourly) {

}