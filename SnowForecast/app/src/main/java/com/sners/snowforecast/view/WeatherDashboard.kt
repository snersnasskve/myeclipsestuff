package com.sners.snowforecast.view


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.sners.snowforecast.ForecastMainActivity
import com.sners.snowforecast.R
import com.sners.snowforecast.weather.WeatherIconGallery
import kotlin.math.roundToInt

/**
 * A class representing the Dashboard view
 */
class WeatherDashboard : Activity() {

    /**
     * @property ivDashSummary Weather Summary - image view
     */
    private var ivDashSummary: ImageView? = null

    /**
     * @property ivDashSummary Weather Summary - text
     */
    private var tvDashSummary: TextView? = null

    /**
     * @property tvDashPrecip Precipitation
     */
    private var tvDashPrecip: TextView? = null

    /**
     * @property tvDashTemperature Temperature
     */
    private var tvDashTemperature: TextView? = null

    /**
     * @property tvDashWind Wind speed
     */
    private var tvDashWind: TextView? = null

    /**
     * @property tvDashTimeTilSunset Time til sunset
     */
    private var tvDashTimeTilSunset: TextView? = null

    /**
     * @property tvDashTimeTilPrecip Time til next precipitation
     */
    private var tvDashTimeTilPrecip: TextView? = null

    /**
     * @property hsvDashActivityIcons Activity icons scroll view
     */
    private var hsvDashActivityIcons: HorizontalScrollView? = null

    /**
     * @property llDashIcontainer Linear Layout for the activity icon view
     */
    private var llDashIcontainer: LinearLayout? = null

    /**
     * @property llDashDashboard Linear Layout for the Dashboard - needed for animations
     */
    private var llDashDashboard: LinearLayout? = null

    /**
     * @property weatherData Weather data object
     */
    private var weatherData = ForecastMainActivity.weatherData!!

    /**
     * On Create - Override
     * @param savedInstanceState Saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        ivDashSummary = findViewById<View>(R.id.ivDashSummary) as ImageView
        tvDashSummary = findViewById<View>(R.id.tvDashSummary) as TextView
        tvDashPrecip = findViewById<View>(R.id.tvDashPrecip) as TextView
        tvDashTemperature = findViewById<View>(R.id.tvDashTemperature) as TextView
        tvDashWind = findViewById<View>(R.id.tvDashWind) as TextView
        tvDashTimeTilSunset = findViewById<View>(R.id.tvDashTimeTilSunset) as TextView
        tvDashTimeTilPrecip = findViewById<View>(R.id.tvDashTimeTilPrecip) as TextView
        hsvDashActivityIcons = findViewById<View>(R.id.hsvDashActivityIcons) as HorizontalScrollView
        llDashIcontainer = findViewById<View>(R.id.llDashIcontainer) as LinearLayout
        llDashDashboard = findViewById<View>(R.id.llDashboard) as LinearLayout

        //Animation animExitLeft = AnimationUtils.makeInAnimation(this, false);
        //llDashDashboard.startAnimation(animExitLeft);
        tvDashSummary!!.text = weatherData.headlineSummary
        tvDashTimeTilSunset!!.text = String.format("%s", weatherData.getTimeTilSunsetString())

        //	timeTillPrecip
        tvDashTimeTilPrecip!!.text = weatherData.precipitation!!.timeTilString(false)
        val iconName = weatherData.headlineIcon
        val iconId = resources.getIdentifier(iconName, "drawable", packageName)
        ivDashSummary!!.setImageResource(iconId)
        ivDashSummary!!.contentDescription = iconName
        setWeatherActivityIcons()

        populatePrecipitation()
        populateTemperature()
        populateWind()
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
        llDashIcontainer!!.removeAllViews()
        for (iconName in qualIcons) {
            val iconId = resources.getIdentifier(iconName, "drawable", packageName)
            val convertView = layoutInflater.inflate(R.layout.icon_gallery, null)
            val img = convertView.findViewById<View>(R.id.ivIconGalleryItem) as ImageView
            img.setImageResource(iconId)
            llDashIcontainer!!.addView(convertView)
        }
    }

    /**
     * Populate Precipitation
     */
    private fun populatePrecipitation() {

        tvDashPrecip!!.text = weatherData.precipitation!!.milsPerHourString
    }

    /**
     * Populate Temperature
     */
    private fun populateTemperature() {

        val tempString = weatherData.currently!!.temperature
        tvDashTemperature!!.text = tempString
        val temperatureNum = weatherData.currently!!.temperatureNum.roundToInt()
        val tempColour = when (temperatureNum) {
            in Int.MIN_VALUE..0 -> Color.GRAY
            in 0..10 -> Color.BLUE
            in 10..15 -> Color.BLACK
            in 15..20 -> Color.rgb(254,211,0)  // Yellow
            in 20..25 -> Color.rgb(255,165,0) // Orange
            in 25..30 -> Color.MAGENTA
            in 30..Int.MAX_VALUE -> Color.RED
            else -> Color.YELLOW // This would indicate an error
        }
        tvDashTemperature!!.setTextColor(tempColour.toInt())
    }


    /**
     * Populate Wind
     */
    private fun populateWind() {

        val beaufortValue = weatherData.wind!!.getSpeedBeaufort()
        tvDashWind!!.text = "$beaufortValue"
        val windColour = when (beaufortValue) {
            in 5 .. 7 -> Color.MAGENTA
            in 7 .. Int.MAX_VALUE -> Color.RED
            else -> Color.parseColor("#008000") // Green
        }
        tvDashWind!!.setTextColor(windColour)
    }

    ///////////////////////////////////////////
    //Activity ending events				 //
    ///////////////////////////////////////////
    /**
     * Display Current Weather
     * @param v View representing the current view - comes in from the layout
     */
    fun displayCurrent(v: View?) {

        val animExitLeft = AnimationUtils.makeOutAnimation(this, false)
        llDashDashboard!!.startAnimation(animExitLeft)
        Toast.makeText(
            applicationContext, "Show the weather",
            Toast.LENGTH_SHORT
        ).show()
        val currentIntent = Intent(this@WeatherDashboard, WeatherCurrent::class.java)
        startActivity(currentIntent)
        finish()
    }
}