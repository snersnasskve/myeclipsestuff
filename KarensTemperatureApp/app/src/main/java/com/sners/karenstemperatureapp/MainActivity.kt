package com.sners.karenstemperatureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

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
        }
        else if (view.id == R.id.fahrenheit_button) {
            fAlpha = 1.0f
        }

        celciusButton.alpha = cAlpha
        fahrButton.alpha = fAlpha


    }

}

