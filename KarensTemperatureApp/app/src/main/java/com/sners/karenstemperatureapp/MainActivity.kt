package com.sners.karenstemperatureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    public fun buttonClicked(view : View)
    {
//        val celciusButton = findViewById<Button>(R.id.celcius_button)
//        val fahrButton = findViewById<Button>(R.id.fahrenheit_button)
//        val tempToConvert = findViewById<EditText>(R.id.edit_temperature)
//        val resultLabel = this.findViewById<TextView>(R.id.label_temperature)



        //    fahrenheit_value.text = this.convertToFahrenheit(celcius_value.text.toString())



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

