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
            if (alertsFound()) {
                qualIcons.add("alert")
            }
            return qualIcons
        }

    ///////////////////////////////////////////
    //	Weather Activity Checkers			 //
    ///////////////////////////////////////////
    //https://blog.metservice.com/washingweather
    /**
     * OK to hang washing
     * @return bool indicating whether it contains the word
     */
    private fun okToHangWashing(): Boolean {
        var isItOK = false
        if (ForecastMainActivity.weatherData.isDayTime() &&
            ForecastMainActivity.weatherData.getTimeTilSunset() > 60 &&
            ForecastMainActivity.weatherData.currently!!.temperatureNum > 5 &&
            !ForecastMainActivity.weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_RAIN, WeatherConstants.MINUTELY
            )
        ) {
            isItOK = true
        }
        return isItOK
    }

    private fun okToGoHiking(): Boolean {
        var isItOK = false
        if (ForecastMainActivity.weatherData.isDayTime() &&
            !ForecastMainActivity.weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_RAIN,
                WeatherConstants.HOURLY
            ) &&
            ForecastMainActivity.weatherData.getTimeTilSunset() > 120
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  Low prob of rain as well
    //  Next 4 hours
    private fun okToUseUmbrella(): Boolean {
        var isItOK = false
        if (ForecastMainActivity.weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_RAIN,
                WeatherConstants.MINUTELY
            )
        ) {
            isItOK = true
        }
        return isItOK
    }

    private fun okToSnow(): Boolean {
        var isItOK = false
        if (ForecastMainActivity.weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_SNOW,
                WeatherConstants.MINUTELY
            ) ||
            ForecastMainActivity.weatherData.dataContainsWeatherword(
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
    private fun okToBraai(): Boolean {
        var isItOK = false
        // if it is not going to rain in the next hour
        if (!ForecastMainActivity.weatherData.dataContainsWeatherword(
                WeatherConstants.PRECIP_TYPE_RAIN,
                WeatherConstants.MINUTELY
            )
        ) {
            isItOK = true
        }
        return isItOK
    }

    //  This should take UV cover into account
    private fun okToWearHat(): Boolean {
        var isItOK = false
        val temperature = ForecastMainActivity.weatherData.currently!!.temperatureNum
        if (ForecastMainActivity.weatherData.isDayTime() && temperature > 20) {
            isItOK = true
        }
        return isItOK
    }

    //  Don't want to fly kite if it is raining
    private fun okToFlyKite(): Boolean {
        var isItOK = false
        val windSpeed = ForecastMainActivity.weatherData.wind!!.getSpeedBeaufort().toFloat()
        if (ForecastMainActivity.weatherData.isDayTime() && windSpeed > 3 && windSpeed < 8 &&
            ForecastMainActivity.weatherData.currently!!.temperatureNum > 5
        ) {
            isItOK = true
        }
        return isItOK
    }

    private fun alertsFound(): Boolean {
        var isItOK = false
        if (null != ForecastMainActivity.weatherData.alerts) {
            isItOK = true
        }
        return isItOK
    }
}