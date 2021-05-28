package com.sners.snowforecast.weather

import com.sners.snowforecast.ForecastMainActivity

//  Too many magic numbers and strings


class WeatherIconGallery {

    //  How to path by reference

    private var weatherData = ForecastMainActivity.weatherData

    //	Call Json will it rain currently if not, add Washing icon
    val weatherActivityIcons: ArrayList<String>
        get() {
            //	Call Json will it rain currently if not, add Washing icon
            val qualIcons = ArrayList<String>()
            if (okToBraai()) {
                qualIcons.add("campfire")
            }
            if (okToGoHiking()) {
                qualIcons.add("hiking")
            }
            if (okToHangWashing()) {
                qualIcons.add("washing")
            }
            if (okToUseUmbrella()) {
                qualIcons.add("umbrella")
            }
            if (okToWearHat()) {
                qualIcons.add("hat")
            }
            if (okToFlyKite()) {
                qualIcons.add("kite")
            }
            if (okToSnow()) {
                qualIcons.add("heavy_snow")
            }
            if (okToScoot()) {
                qualIcons.add("scooter")
            }
            if (alertsFound()) {
                qualIcons.add("alert")
            }
            return qualIcons
        }

    ///////////////////////////////////////////
    //	Weather Activity Checkers			 //
    ///////////////////////////////////////////
    //https://blog.metservice.com/washingweather
    /*
    rain - low probability
solar radiation - the higher the better
humidity - the lower the better - maybe below 60
--
Dew point less than temp
Cloud cover less than 60
Temp greater than 5
Hours till sunset more than 3
Wind less than gale unless u wanna share ur bookies with the neighbours
     */
    /**
     * OK to hang washing
     * @return bool indicating whether it contains the word
     */
    private fun okToHangWashing(): Boolean {
        var isItOK = false
        if (weatherData.isDayTime() &&
            weatherData.getTimeTilSunset() > WASHING_TIME_TIL_SUNSET &&
            weatherData.currently!!.temperatureNum > weatherData.currently!!.dewPoint &&
            weatherData.precipitation!!.noRainForTheHour()
        ) {
            isItOK = true
        }
        return isItOK
    }

    /**
     * OK to go hiking
     * @return bool indicating whether it contains the word
     */
    private fun okToGoHiking(): Boolean {
        var isItOK = false
        if (weatherData.isDayTime() &&
            !weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_RAIN,
                WeatherConstants.HOURLY
            ) &&
            weatherData.getTimeTilSunset() > HIKING_TIME_TIL_SUNSET
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  Low prob of rain as well
    //  Next 4 hours
    /**
     * OK to use umbrella
     * @return bool indicating whether it contains the word
     */
    private fun okToUseUmbrella(): Boolean {
        var isItOK = false
        if (weatherData.precipitation!!.noRainForTheHour() == false) {
            isItOK = true
        }
        return isItOK
    }

    /**
     * OK to snow
     * @return bool indicating whether it contains the word
     */
    private fun okToSnow(): Boolean {
        var isItOK = false
        if (weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_SNOW,
                WeatherConstants.MINUTELY
            ) ||
            weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_SNOW,
                WeatherConstants.HOURLY
            )
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  Not raining
    //  After lunch time - what time is lunch time
    //      or including lunch
    //  Look out for guti = rain very low but not
    /**
     * OK to have braai
     * @return bool indicating whether it contains the word
     */
    private fun okToBraai(): Boolean {
        var isItOK = false
        // if it is not going to rain in the next hour
        if (!weatherData.precipitation!!.noRainForTheHour() &&
            weatherData.currently!!.timeSinceSunrise > BRAAI_TIME_SINCE_SUNRISE
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  This should take UV cover into account
    /**
     * OK to wear a hat
     * @return bool indicating whether it contains the word
     */
    private fun okToWearHat(): Boolean {
        var isItOK = false
        val temperature = weatherData.currently!!.temperatureNum
        if (weatherData.isDayTime() && temperature > HAT_TEMPERATURE) {
            isItOK = true
        }
        return isItOK
    }

    //  Don't want to fly kite if it is raining
    /**
     * OK to fly a kite
     * @return bool indicating whether it contains the word
     */
    private fun okToFlyKite(): Boolean {
        var isItOK = false
        val windSpeed = weatherData.wind!!.getSpeedBeaufort().toFloat()
        if (weatherData.isDayTime() && windSpeed > 3 && windSpeed < 8 &&
            weatherData.currently!!.temperatureNum > 5
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  Don't want to fly kite if it is raining
    /**
     * OK to scoot
     * @return bool indicating whether it is not raining
     */
    private fun okToScoot(): Boolean {
        var isItOK = false
        if (!weatherData.precipitation!!.noRainForTheHour() &&
            weatherData.currently!!.temperatureNum >= 4
        ) {
            isItOK = true
        }
        return isItOK
    }

    /**
     * Alerts found
     * @return bool indicating whether it contains the word
     */
    private fun alertsFound(): Boolean {
        var isItOK = false
        if (null != weatherData.alerts) {
            isItOK = true
        }
        return isItOK
    }

    companion object {

        const val BRAAI_TIME_SINCE_SUNRISE = 180

        const val HAT_TEMPERATURE = 20

        const val HIKING_TIME_TIL_SUNSET = 120


        const val WASHING_TIME_TIL_SUNSET = 60

    }
}


