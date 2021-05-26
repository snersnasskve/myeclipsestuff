package com.sners.snowforecast.weather

object WeatherConstants {
    const val CLOUD_COVER = "cloudcover"
    const val DATA = "data"
    const val DEGREES_C: String = "°C"
    const val DESCRIPTION = "description"
    const val DEW_POINT = "dew"
    const val NONE_FORECAST = "None forecast"
    const val NOW = "Now"
    const val PRECIP_INTENSITY = "precip"
    const val PRECIP_PROBABILITY = "precipprob"
    const val PRECIP_TYPE = "preciptype"
    const val PRECIP_TYPE_RAIN = "rain"
    const val PRECIP_TYPE_SNOW = "snow"

    const val MM_HR = " mm/hr"
    const val PERCENT = " %"
    const val HUMIDITY = "humidity"
    const val SOLAR_RADIATION = "solarradiation"
    const val SEVERE_RISK = "severerisk"

    //  Time keywords
    const val MINUTELY = "minutely"
    const val HOURLY = "hours"
    const val CURRENTLY = "currentConditions"
    const val INTERVALS = "intervals"
    const val DAILY = "days"
    const val SUNSET_TIME = "sunset"
    const val SUNRISE_TIME = "sunrise"
    const val TITLE = "title"
    const val EXPIRES = "expires"
    const val TEMPERATURE = "temp"
    const val TEMP_MAX = "tempmax"
    const val TEMP_MIN = "tempmin"
    const val TEMP_FEELS_LIKE = "feelslike"
    const val TIME_LOCAL = "timestamp_local"
    const val TIME = "datetime"
    const val URI = "uri"

    const val WEATHER_CODE = "weatherCode"
    const val WIND_SPEED = "windspeed"
    const val WIND_GUST = "windgust"
    const val WIND_DIRECTION= "winddir"
    const val NORTH = "N"
    const val SOUTH = "S"
    const val WEST = "W"
    const val EAST = "E"
    const val MS_TO_MPH_CONVERSION = 2.236936
    const val KM_TO_MPH_CONVERSION = 0.6213712
    const val CONDITIONS = "conditions"
    const val ICON = "icon"

    //  Graph constants
    const val HOURLY_NUM_POINTS_TO_PLOT = 48
    const val MINUTELY_NUM_POINTS_TO_PLOT = 60

}