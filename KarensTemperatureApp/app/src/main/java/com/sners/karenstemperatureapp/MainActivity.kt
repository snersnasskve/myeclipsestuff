package com.sners.karenstemperatureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    public fun buttonClicked(view : View)
    {
        val celciusButton = findViewById<Button>(R.id.celcius_button)
        val fahrButton = findViewById<Button>(R.id.fahrenheit_button)
        val tempToConvert = findViewById<EditText>(R.id.edit_temperature)
        val resultLabel = this.findViewById<TextView>(R.id.label_temperature)

        var cAlpha = 0.4f
        var fAlpha = 0.4f
        if (view.id == R.id.celcius_button) {
            cAlpha = 1.0f
            resultLabel.text = this.convertToFahrenheit(tempToConvert.text.toString())
        }
        else if (view.id == R.id.fahrenheit_button) {
            fAlpha = 1.0f
            resultLabel.text = this.convertToCelsius(tempToConvert.text.toString())
        }

        celciusButton.alpha = cAlpha
        fahrButton.alpha = fAlpha


    }

  private fun convertToCelsius(fahrStringValue: String) : String
    {
        var resultString = ""
        if (fahrStringValue != "") {
            val fahrValue = fahrStringValue.toFloat()
            val celValue = (fahrValue - 32) / 1.8
            resultString = "%.1f ° C".format(celValue)
        }
        return resultString
    }

    private fun convertToFahrenheit(celStringValue: String) : String
    {
        var resultString = ""
        if (celStringValue != "") {
            val celsiusValue = celStringValue.toFloat()
            val fahrValue = (celsiusValue * 1.8) + 32
            resultString =  "%.1f ° F".format(fahrValue)
        }
        return resultString
    }
}

