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

    private var minTemp: Int
    var sliderTemp: Double


    init {
        _tempCelcius.value = celciusString(20.0)
        _tempFahrenheit.value = fahrenheitString( convertToFahrenheit(20.0))
        minTemp = 5
        sliderTemp = 20.0
    }

    private fun convertToCelsius(fahrValue: Double): Double {

        return (fahrValue - 32.0) / 1.8
    }

    private fun convertToFahrenheit(celsiusValue: Double): Double {

        return (celsiusValue * 1.8) + 32.0
    }

    private fun celciusString(celsiusValue: Double) : String {
        return "${celsiusValue.toInt()} ° C"
    }

    private fun fahrenheitString(fahrValue: Double) : String {
        val rounded = ((fahrValue * 10).toInt().toDouble())/10.0F
        return "$rounded ° F"
    }

    fun setTemperature(newTemp: Int) {
        sliderTemp = newTemp.toDouble()
        _tempCelcius.value = celciusString(newTemp.toDouble())
        _tempFahrenheit.value = fahrenheitString( convertToFahrenheit(newTemp.toDouble()))    }
}