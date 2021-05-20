package com.sners.snowforecast.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.sners.snowforecast.ForecastMainActivity
import com.sners.snowforecast.R
import com.sners.snowforecast.weather.WeatherConstants

/**
 * A class representing the Minutely graphs view
 */
class MinutelyPrecipChart : WeatherPrecipChart() {

    /**
     * On Create - Override
     * @param savedInstanceState Saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.precip_chart)
        ivPcIntensity = findViewById<View>(R.id.ivPcIntensity) as ImageView
        ivPcProbability = findViewById<View>(R.id.ivPcProbability) as ImageView
        tvPcIntensity = findViewById<View>(R.id.tvPcIntensity) as TextView
        precipPrefix = "Minutely Precip: "
        val minutely = ForecastMainActivity.weatherData.minutelyData
        var maxPrecip: Float = ForecastMainActivity.weatherData.minutely!!.getMaxPrecip()
        maxPrecip = setTitleAndColours(maxPrecip)
        drawPrecipGraph(minutely, maxPrecip, WeatherConstants.MINUTELY_NUM_POINTS_TO_PLOT)
        drawProbabilityGraph(minutely, WeatherConstants.MINUTELY_NUM_POINTS_TO_PLOT)
    }

    /**
     * Display Current Weather
     * @param v View representing the current view - comes in from the layout
     */
    fun displayDashboard(v: View?) {
        //Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
        //llCurrently.startAnimation(animExitLeft);
        val nextActivityIntent: Intent
        val toastMessage: String
        if (null != ForecastMainActivity.weatherData.hourly && ForecastMainActivity.weatherData.hourly!!.getMaxPrecip() > 0.0f) {
            nextActivityIntent = Intent(this@MinutelyPrecipChart, HourlyPrecipChart::class.java)
            toastMessage = "Show Hourly graphs"
        } else {
            nextActivityIntent = Intent(this@MinutelyPrecipChart, WeatherDashboard::class.java)
            toastMessage = "Show the Dashboard"
        }
        val animExitLeft = AnimationUtils.makeOutAnimation(this, true)
        Toast.makeText(
            applicationContext, toastMessage,
            Toast.LENGTH_SHORT
        ).show()
        startActivity(nextActivityIntent)
        finish()
    }
}