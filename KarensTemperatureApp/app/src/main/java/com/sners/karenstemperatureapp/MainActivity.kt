package com.sners.karenstemperatureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TemperatureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(TemperatureViewModel::class.java)

        celcius_button.alpha = 1.0f
        fahrenheit_button.alpha = 0.4f


        viewModel.tempCelcius.observe(this, Observer {
            celcius_value.text = it
        })

        viewModel.tempFahrenheit.observe(this, Observer {
            fahrenheit_value.text = it
        })

        seekBar.progress = viewModel.sliderTemp.toInt()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progressChangedValue = 0
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                progressChangedValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                this@MainActivity.viewModel.setTemperature(this.progressChangedValue)

            }
        })

    }

    public fun buttonClicked(view : View)
    {

        var cAlpha = 0.4f
        var fAlpha = 0.4f
        if (view.id == R.id.celcius_button) {
            cAlpha = 1.0f
            viewModel.isCelciusMode = true
        }
        else if (view.id == R.id.fahrenheit_button) {
            fAlpha = 1.0f
            viewModel.isCelciusMode = false

        }

        celcius_button.alpha = cAlpha
        fahrenheit_button.alpha = fAlpha
        }



}

