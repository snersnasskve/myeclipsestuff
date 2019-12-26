package com.sners.karenstemperatureapp

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TemperatureViewModel : ViewModel(), LifecycleObserver {

    private val _tempCelcius = MutableLiveData<String>()
    val tempCelcius: LiveData<String>
        get() = _tempCelcius

    private val _tempFahrenheit = MutableLiveData<String>()
    val tempFahrenheit: LiveData<String>
        get() = _tempFahrenheit

    private var minTemp: Int = 0
    private var sliderTemp: Double

    var progressTick : Int
    get() = sliderTemp.toInt() - minTemp

    private var isCelciusMode = true


    init {
        _tempCelcius.value = celciusString(20.0)
        _tempFahrenheit.value = fahrenheitString( convertToFahrenheit(20.0))
        minTemp = 5
        sliderTemp = 20.0
        progressTick = (sliderTemp - minTemp).toInt()
    }

    ////////////////////////////////////////////////////////////////
    fun setTemperature(newTemp: Int) {
        sliderTemp = (newTemp + minTemp).toDouble()
        if (isCelciusMode) {
            _tempCelcius.value = celciusString(sliderTemp.toDouble())
            _tempFahrenheit.value = fahrenheitString(convertToFahrenheit(sliderTemp.toDouble()))
        } else {
            _tempCelcius.value = celciusString(convertToCelsius(sliderTemp.toDouble()))
            _tempFahrenheit.value = fahrenheitString(sliderTemp.toDouble())
        }

        if (sliderTemp <= minTemp)
        {
            minTemp -= 15
        }
        ////////////////////////////////////////////////////////////////
        else if (sliderTemp >= minTemp + 25)
        {
            minTemp += 15
        }
        this.progressTick = (sliderTemp - minTemp).toInt()
    }

    ////////////////////////////////////////////////////////////////
fun setThermometerScale(isCelcius : Boolean)
    {
        if ((isCelcius && this.isCelciusMode) || (!isCelcius && !this.isCelciusMode))
        {
            //  No Change, do nothing
        }
        else if (isCelcius)
        {
            this.sliderTemp = convertToCelsius(this.sliderTemp)
            this.minTemp = convertToCelsius(this.minTemp.toDouble()).toInt()

        }
        else
        {
            this.sliderTemp = convertToFahrenheit(this.sliderTemp)
            this.minTemp = convertToFahrenheit(this.minTemp.toDouble()).toInt()

        }
        while (sliderTemp <= minTemp) {
            minTemp -= 15
        }
        while (sliderTemp >= minTemp + 25) {
            minTemp += 15
        }
        this.isCelciusMode = isCelcius
    }
    ////////////////////////////////////////////////////////////////
    private fun convertToCelsius(fahrValue: Double): Double {

        return (fahrValue - 32.0) / 1.8
    }

    ////////////////////////////////////////////////////////////////
    private fun convertToFahrenheit(celsiusValue: Double): Double {

        return (celsiusValue * 1.8) + 32.0
    }

    ////////////////////////////////////////////////////////////////
    private fun celciusString(celsiusValue: Double) : String {
        val rounded = ((celsiusValue * 10).toInt().toDouble())/10.0F
        return "$rounded ° C"
    }

    ////////////////////////////////////////////////////////////////
    private fun fahrenheitString(fahrValue: Double) : String {
        val rounded = ((fahrValue * 10).toInt().toDouble())/10.0F
        return "$rounded ° F"
    }


}