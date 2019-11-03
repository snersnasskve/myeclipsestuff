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
        var cAlpha = 0.5f
        var fAlpha = 0.5f
        if (view.id == R.id.celcius_button) {
            cAlpha = 1.0f
            this.convertToCelsius()
        }
        else if (view.id == R.id.fahrenheit_button) {
            fAlpha = 1.0f
            this.convertToFahrenheit()
        }

        celciusButton.alpha = cAlpha
        fahrButton.alpha = fAlpha


    }

  private fun convertToCelsius()
    {
        val textEdit = this.findViewById<EditText>(R.id.edit_temperature)
        val fahrValue = textEdit.text.toString().toFloat()
        val celValue = (fahrValue - 32 / 1.8)
        val resultText = this.findViewById<TextView>(R.id.label_temperature)
        resultText.text = "$celValue deg C"

    }

    private fun convertToFahrenheit()
    {
        val textEdit = this.findViewById<EditText>(R.id.edit_temperature)
        val celsiusValue = textEdit.text.toString().toFloat()
        val fahrValue = (celsiusValue * 1.8) + 32
        val resultText = this.findViewById<TextView>(R.id.label_temperature)
        resultText.text = "$fahrValue deg F"

    }
}

