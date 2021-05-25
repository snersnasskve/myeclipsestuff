package com.sners.snowforecast.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.sners.snowforecast.ForecastMainActivity
import com.sners.snowforecast.R
import com.sners.snowforecast.weather.WeatherConstants
import com.sners.snowforecast.weather.WeatherIconGallery

/**
 * A class representing the Current weather view
 */
class WeatherCurrent : Activity() {
    //	Please I want time until precipitation
    /**
     * @property ivCurIcon Current weather icon - image view
     */
    private var ivCurIcon: ImageView? = null

    /**
     * @property tvCurSummary Weather Summary - image view
     */
    private var tvCurSummary: TextView? = null

    /**
     * @property tvCurPrecipIntensity Precipitation
     */
    private var tvCurPrecipIntensity: TextView? = null

    /**
     * @property tvCurPrecipProbability Precipitation probability
     */
    private var tvCurMoisture: TextView? = null

    /**
     * @property tvCurTemperature Temperature
     */
    private var tvCurTemperature: TextView? = null

    /**
     * @property tvCurWind Wind speed
     */
    private var tvCurWind: TextView? = null

    /**
     * @property tvCurTimeTilSunset Time til sunset
     */
    private var tvCurTimeTilSunset: TextView? = null

    /**
     * @property tvCurTimeTilPrecip Time til next precipitation
     */
    private var tvCurTimeTilPrecip: TextView? = null

    /**
     * @property tvCurTimeTilSnow Time til next snow
     */
    private var tvCurTimeTilSnow: TextView? = null

    /**
     * @property tvCurAlertString Alerts
     */
    private var tvCurAlertString: TextView? = null

    /**
     * @property hsvCurActivityIcons Activity icons scroll view
     */
    private var hsvCurActivityIcons: HorizontalScrollView? = null

    /**
     * @property llCurIcontainer Linear Layout for the activity icon view
     */
    private var llCurIcontainer: LinearLayout? = null

    /**
     * @property llCurrently Linear Layout for the Current weather - needed for animations
     */
    private var llCurrently: LinearLayout? = null

    /**
     * @property weatherData Weather data object
     */
    private var weatherData = ForecastMainActivity.weatherData

    /**
     * On Create - Override
     * @param savedInstanceState Saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currently)
        ivCurIcon = findViewById<View>(R.id.ivCurIcon) as ImageView
        tvCurSummary = findViewById<View>(R.id.tvCurSummary) as TextView
        tvCurPrecipIntensity = findViewById<View>(R.id.tvCurPrecipIntensity) as TextView
        tvCurMoisture = findViewById<View>(R.id.tvCurMoisture) as TextView
        tvCurTemperature = findViewById<View>(R.id.tvCurTemperature) as TextView
        tvCurWind = findViewById<View>(R.id.tvCurWind) as TextView
        tvCurTimeTilSunset = findViewById<View>(R.id.tvCurTimeTilSunset) as TextView
        tvCurTimeTilPrecip = findViewById<View>(R.id.tvCurTimeTilPrecip) as TextView
        tvCurTimeTilSnow = findViewById<View>(R.id.tvCurTimeTilSnow) as TextView
        tvCurAlertString = findViewById<View>(R.id.tvCurAlertString) as TextView
        hsvCurActivityIcons = findViewById<View>(R.id.hsvCurActivityIcons) as HorizontalScrollView
        llCurIcontainer = findViewById<View>(R.id.llCurIcontainer) as LinearLayout
        llCurrently = findViewById<View>(R.id.llCurrently) as LinearLayout

        //Animation animExitLeft = AnimationUtils.makeInAnimation(this, true);
        //llCurrently.startAnimation(animExitLeft);
        tvCurSummary!!.text = weatherData.headlineSummary
        tvCurPrecipIntensity!!.text = weatherData.precipitation!!.milsPerHourString
        tvCurMoisture!!.text = weatherData.currently!!.cloudCoverString
        tvCurTemperature!!.text = weatherData.getTemperatureSummary()
        tvCurWind!!.text = weatherData.wind!!.getDetails()
        tvCurTimeTilSunset!!.text = weatherData.getTimeTilSunsetString()

        //	timeTillPrecip
        tvCurTimeTilPrecip!!.text = ForecastMainActivity.weatherData.precipitation!!.timeTilString(false)
        tvCurTimeTilSnow!!.text =
            ForecastMainActivity.weatherData.precipitation!!.timeTilPrecipTypeString(WeatherConstants.PRECIP_TYPE_SNOW)
        var alertHeadline: String? = "None"
        if (null != ForecastMainActivity.weatherData.alerts) {
            alertHeadline = ForecastMainActivity.weatherData.alerts!!.alertSummary
        }
        tvCurAlertString!!.text = alertHeadline
        val iconName = weatherData.headlineIcon
        val iconId = resources.getIdentifier(iconName, "drawable", packageName)
        ivCurIcon!!.setImageResource(iconId)
        ivCurIcon!!.contentDescription = iconName
        setWeatherActivityIcons()
    }

    /**
     * Set up weather activity icons
     */
    private fun setWeatherActivityIcons() {
        val iconGallery = WeatherIconGallery()
        val qualIcons = iconGallery.weatherActivityIcons
        inflateWeatherActivityIcons(qualIcons)
    }

    /**
     * Inflate weather activity icons
     * @param qualIcons Icons we want to show
     */
    private fun inflateWeatherActivityIcons(qualIcons: ArrayList<String>) {
        val layoutInflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        llCurIcontainer!!.removeAllViews()
        for (iconName in qualIcons) {
            val iconId = resources.getIdentifier(iconName, "drawable", packageName)
            val convertView = layoutInflater.inflate(R.layout.icon_gallery, null)
            val img = convertView.findViewById<View>(R.id.ivIconGalleryItem) as ImageView
            img.setImageResource(iconId)
            llCurIcontainer!!.addView(convertView)
        }
    }

    ///////////////////////////////////////////
    //Activity ending events				 //
    ///////////////////////////////////////////
    /**
     * Display Current Weather
     * @param v View representing the current view - comes in from the layout
     */
    fun displayDashboard(v: View?) {
        val nextActivityIntent: Intent
        val toastMessage: String
        if (null != weatherData.minutely && weatherData.minutely!!.getMaxPrecip() > 0.0f) {
            nextActivityIntent = Intent(this@WeatherCurrent, MinutelyPrecipChart::class.java)
            toastMessage = "Show Minutely graphs"
        } else if (null != ForecastMainActivity.weatherData.hourly && ForecastMainActivity.weatherData.hourly!!.getMaxPrecip() > 0.0f) {
            nextActivityIntent = Intent(this@WeatherCurrent, HourlyPrecipChart::class.java)
            toastMessage = "Show Hourly graphs"
        } else if (null != ForecastMainActivity.weatherData.alerts) {
            nextActivityIntent = Intent(this@WeatherCurrent, WeatherAlert::class.java)
            toastMessage = "Show Alerts"
        } else {
            nextActivityIntent = Intent(this@WeatherCurrent, WeatherDashboard::class.java)
            toastMessage = "Show the Dashboard"
        }
        val animExitLeft = AnimationUtils.makeOutAnimation(this, true)
        llCurrently!!.startAnimation(animExitLeft)
        Toast.makeText(
            applicationContext, toastMessage,
            Toast.LENGTH_SHORT
        ).show()
        startActivity(nextActivityIntent)
        finish()
    }
}