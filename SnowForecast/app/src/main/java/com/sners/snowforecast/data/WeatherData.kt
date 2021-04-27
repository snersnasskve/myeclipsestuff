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

    /**
     *  @property windSpeed Wind speed - reads from data object as appropriate
     */
    private val windSpeed : Float

        get() {
            var wind = currently.windSpeed
            if (wind < 1) {
                wind = hourlyData[0].windSpeed.toFloat()
            }
            return wind
        }

    /**
     *  @property windGusts Wind speed - reads from data object as appropriate
     */
    private val windGusts : Float
        get() {
            var gusts = currently.windGusts
            if (gusts < 1) {
                gusts = hourlyData[0].windSpeed.toFloat()
            }
            return gusts
        }


    /**
     * Get the wind details
     * @return Wind details string
     */
    fun getWindDetails() : String {

        //  eg 1.2 mph (gusts: 4 mph)
        return "$windSpeed mph (gusts: $windGusts)"
    }

    /**
     * Get the wind speed as mph
     * @return Wind converted to mph
     */
    private fun getWindSpeedMph(): Double {
        return windSpeed * WeatherConstants.KM_TO_MPH_CONVERSION
    }

    /**
     * Get the wind speed as Beaufort
     * @return Wind converted to Beaufort number
     */
    fun getWindSpeedBeaufort(): Float {
        return weatherHelper.windSpeedToBeaufort(getWindSpeedMph()).toFloat()
    }

    /**
     * Get the wind speed as Beaufort string
     * @return Wind converted to Beaufort string
     */
     fun getWindSpeedBeaufortString(): String {
        return "${getWindSpeedBeaufort()}"
    }

    /**
     * Get the wind speed as mph string
     * @return Wind converted to mph string
     */
     fun getWindSpeedMphString(): String {
        return java.lang.String.format("%.1f mph", getWindSpeedMph())
    }

}
