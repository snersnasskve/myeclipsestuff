package com.sners.snowforecast

import android.content.Intent
import android.content.SharedPreferences
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.sners.snowforecast.apicall.VisualCrossingReader.timelineRequest
import com.sners.snowforecast.apicall.WeatherBitReader
import com.sners.snowforecast.location.ForecastLocation
import com.sners.snowforecast.location.WeatherLocation
import com.sners.snowforecast.weather.WeatherData
import java.text.DecimalFormat
import java.util.*

class ForecastMainActivity : FragmentActivity() {
    var etLocationPlaceName: EditText? = null
    var tvLocation: TextView? = null
    var tvStatus: TextView? = null
    var spFavourites: Spinner? = null
    var pbReadWeather: ProgressBar? = null
    var weatherLocations: ArrayList<WeatherLocation>? = null

    //	For the array adaptor - names only
    var favourites: ArrayList<String?>? = null
    var favouritesAdapter: ArrayAdapter<String?>? = null
    private var currentLocation: WeatherLocation? = null
    private var favouriteLocation: WeatherLocation? = null
    var forecastLocation: ForecastLocation? = null
    private var lastUsedInfo: SharedPreferences? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_main)
        lastUsedInfo = getSharedPreferences("weatherinfo", MODE_PRIVATE)
        etLocationPlaceName = findViewById<View>(R.id.etLocationPlaceName) as EditText
        tvLocation = findViewById<View>(R.id.tvLocation) as TextView
        spFavourites = findViewById<View>(R.id.spFavourites) as Spinner
        tvStatus = findViewById<View>(R.id.tvStatus) as TextView
        pbReadWeather = findViewById<View>(R.id.pbReadWeather) as ProgressBar
        pbReadWeather!!.visibility = View.INVISIBLE
        populateFavourites(lastUsedInfo?.getString("favourites", null))


        //favouritesAdapter =
        //		ArrayAdapter.createFromResource(ForecastMainActivity.this, favourites, );
        favouritesAdapter = ArrayAdapter(
            this@ForecastMainActivity,
            android.R.layout.simple_list_item_1, favourites!!
        )
        favouritesAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFavourites!!.adapter = favouritesAdapter

        //	This needs to be called from Android class
        val locationMgr = getSystemService(LOCATION_SERVICE) as LocationManager
        forecastLocation = ForecastLocation(locationMgr, this@ForecastMainActivity)
        spFavourites!!.onItemSelectedListener = favouriteItemSelected
    }

    override fun onResume() {
        super.onResume()
        if (null == currentLocation) {
            //	Only read it the first time
            readLocationFromPhone()
        }
    }

    override fun onPause() {
        saveFavourites()
        super.onPause()
    }

    override fun onStop() {
        saveFavourites()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.forecast_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_add_favourite) {
            val success = locationFromAddress
            if (success) {
                addFavourite()
            }
            return true
        } else if (id == R.id.action_remove_favourite) {
            val success = locationFromAddress
            if (success) {
                //forecastMainFragment.spFavourites.doSomthing()
                deleteFavourite()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveFavourites() {
        val prefsEditor = lastUsedInfo!!.edit()
        prefsEditor.clear()
        prefsEditor.putString("favourites", favouritesToString())
        prefsEditor.apply()
        prefsEditor.commit()
    }

    /////////////////////////////////////////////
    //	Location
    /////////////////////////////////////////////
    private fun readLocationFromPhone() {
        var statusString = "Searching..."
        pbReadWeather!!.visibility = View.VISIBLE
        tvStatus!!.text = statusString

        //	Get location from phone
        val bestLocation = forecastLocation!!.bestLastKnownLocation()
        if (null != bestLocation) {
            currentLocation = WeatherLocation("Local", "" + bestLocation.latitude, "" + bestLocation.longitude)
            val locationString = locationFormat.format(currentLocation!!.latitude) + "  :  " +
                    locationFormat.format(currentLocation!!.longitude)
            tvLocation!!.text = locationString
            statusString = "Current Location found"
            //	getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
        } else {
            statusString = "Please enable GPS"
        }
        tvStatus!!.text = statusString
        pbReadWeather!!.visibility = View.INVISIBLE
    }//	Get location from phone

    //	Get location from text on screen
    private val locationFromAddress: Boolean
        private get() {
            var statusString = "Searching..."
            pbReadWeather!!.visibility = View.VISIBLE
            tvStatus!!.text = statusString
            var success = false
            if (etLocationPlaceName!!.text.toString().length > 3) {
                //	Get location from text on screen
                forecastLocation!!.locationFromPlace(etLocationPlaceName!!.text.toString(), application)
                if (forecastLocation!!.isValidLocation) {
                    val locationString = locationFormat.format(
                        forecastLocation!!.mLatitude
                    ) + "  :  " +
                            locationFormat.format(forecastLocation!!.mLongitude)
                    tvLocation!!.text = locationString
                    statusString = "Location for address found"
                    success = true
                } else {
                    statusString = "Try later when you have internet"
                }
            } else {
                //	Get location from phone
                statusString = "Please enter a place name !"
            }
            tvStatus!!.text = statusString
            pbReadWeather!!.visibility = View.INVISIBLE
            return success
        }

    /////////////////////////////////////////////
    //	Action on Buttons
    /////////////////////////////////////////////
    fun forecastForAddress(view: View?) {
        if (etLocationPlaceName!!.text.toString().length > 3) {
            locationFromAddress
            Toast.makeText(
                applicationContext, "Show forecast for this address",
                Toast.LENGTH_SHORT
            ).show()
            Log.i(TAG, "forecastForAddress - Leaving main thread")
            getWeatherData(forecastLocation!!.mLatitude, forecastLocation!!.mLongitude)
        }
    }

    fun forecastForLocation(view: View?) {
        etLocationPlaceName!!.setText("")
        Toast.makeText(
            applicationContext, "Show forecast for this location",
            Toast.LENGTH_SHORT
        ).show()
        Log.i(TAG, "forecastForLocation - Leaving main thread")
        getWeatherData(currentLocation!!.latitude!!, currentLocation!!.longitude!!)
    }

    fun forecastForFavourite(view: View?) {
        if (favouriteLocation != null) {
            Toast.makeText(
                applicationContext, "Show forecast for this preset",
                Toast.LENGTH_SHORT
            ).show()
            Log.i(TAG, "forecastForFavourite - Leaving main thread")
            getWeatherData(favouriteLocation!!.latitude!!, favouriteLocation!!.longitude!!)
        }
    }

    fun refreshScreen(view: View?) {
        //readLocationFromPhone();
        Toast.makeText(
            applicationContext, "Re-read loaction and show forecast for this location",
            Toast.LENGTH_SHORT
        ).show()
        if (null != currentLocation) {
            Log.i(TAG, "refreshScreen - Leaving main thread")
            getWeatherData(currentLocation!!.latitude!!, currentLocation!!.longitude!!)
        } else {
            tvStatus!!.text = "Location not found. Please enable GPS."
            Toast.makeText(
                applicationContext, "Location not found. Please enable GPS.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /////////////////////////////////////////////
    //	Favourites
    /////////////////////////////////////////////
    private fun populateFavourites(favouritesString: String?) {
        favourites = ArrayList()
        weatherLocations = ArrayList()
        favourites!!.add("")
        weatherLocations!!.add(WeatherLocation())
        if (null != favouritesString) {
            //asList is a disaster, bar is being used as a regex
            val tempLocList = Arrays.asList(*TextUtils.split(favouritesString, "\\|"))
            for (locStringInst in tempLocList) {
                val locInst = WeatherLocation(locStringInst)
                weatherLocations!!.add(locInst)
                favourites!!.add(locInst.name)
            }
        }
    }

    var favouriteItemSelected: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?, view: View,
            position: Int, id: Long
        ) {
            etLocationPlaceName!!.setText(weatherLocations!![position].name)
            if (weatherLocations!![position].name!!.length > 3) {
                tvLocation!!.text =
                    "" + weatherLocations!![position].latitude + "" + "  :  " + weatherLocations!![position].longitude
                favouriteLocation = weatherLocations!![position]
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // TODO Auto-generated method stub
        }
    }

    private fun favouritesToString(): String? {
        var favString: String? = null
        if (weatherLocations!!.size != 0) {
            val weatherStringArray = ArrayList<String?>()
            for (locInst in weatherLocations!!) {
                if (locInst.name != "") {
                    weatherStringArray.add(locInst.toString())
                }
            }
            favString = TextUtils.join("|", weatherStringArray)
        }
        return favString
    }

    private fun addFavourite() {
        var statusString = "Please enter valid place name"
        val placeName = etLocationPlaceName!!.text.toString()
        if (placeName.length > 3) {
            statusString = if (favourites!!.contains(placeName)) {
                "$placeName is already in your favourites"
            } else {
                val newLocation = WeatherLocation(placeName, tvLocation!!.text.toString())
                if ("" !== newLocation.name) {
                    weatherLocations!!.add(newLocation)
                    favouritesAdapter!!.add(placeName)
                    spFavourites!!.setSelection(favourites!!.size - 1)
                    "$placeName has been added"
                } else {
                    "Get location before adding to favourites"
                }
            }
        }
        tvStatus!!.text = statusString
    }

    private fun deleteFavourite() {
        var statusString = "Please select the place you want to delete"
        val placeName = etLocationPlaceName!!.text.toString()
        if (placeName.length > 3) {
            var placeToDelete: WeatherLocation? = null
            for (locInst in weatherLocations!!) {
                if (locInst.name == placeName) {
                    placeToDelete = locInst
                }
            }
            if (null != placeToDelete) {
                weatherLocations!!.remove(placeToDelete)
                favouritesAdapter!!.remove(placeName)
                statusString = "$placeName has been deleted"
                spFavourites!!.setSelection(0)
            }
        }
        tvStatus!!.text = statusString
    }

    /////////////////////////////////////////////
    //	Read forecast
    /////////////////////////////////////////////
    private fun getWeatherData(mLatitude: Double, mLongitude: Double) {
        pbReadWeather!!.visibility = View.VISIBLE
        ReadWeatherAsyncTask().execute(mLatitude, mLongitude)
    }

    // Use AsyncTask if you need to perform background tasks, but also need
    // to change components on the GUI. Put the background operations in
    // doInBackground. Put the GUI manipulation code in onPostExecute
    private inner class ReadWeatherAsyncTask : AsyncTask<Double?, Double?, String>() {
        protected override fun doInBackground(vararg params: Double?): String? {
            var errorMessage = ""
            try {
                //  Please make a string resource file called secrets.
                //  Your api key should do in there
                val apiKey = resources.getString(R.string.visual_crossing_api_key)
                timelineRequest(params[0]!!, params[1]!!, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Unable to read weather - please try later when you have internet"
            }
            try {
                //  Please make a string resource file called secrets.
                //  Your api key should do in there
                val apiKey = resources.getString(R.string.weather_bit_api_key)
                WeatherBitReader.timelineRequest(params[0]!!, params[1]!!, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Unable to read weather - please try later when you have internet"
            }
            if ("" === errorMessage) {
                weatherData = WeatherData(rawMinutely, rawHourly)
            }
            return errorMessage
        }

        override fun onPostExecute(result: String) {
            showCurrentWeather(result)
            pbReadWeather!!.visibility = View.INVISIBLE
        }
    }

    private fun showCurrentWeather(weatherJson: String?) {
        if (null != weatherJson && "" === weatherJson) {
            Log.i(TAG, "showCurrentWeather - rejoined main thread")
            //Intent currentIntent = new Intent(ForecastMainActivity.this, WeatherDashboard.class);
            val currentIntent = Intent()
            currentIntent.setClassName(this@ForecastMainActivity, "com.sners.snowforecast.view.WeatherDashboard")
            startActivity(currentIntent)
        } else {
            tvStatus!!.text = "Reading weather failed - try later when you have internet."
        }
    }

    companion object {
        var TAG = "ForecastMainActivity"

        //	Location
        private val locationFormat = DecimalFormat("0.00000")
        @JvmField
        var weatherData //	Data needs to be omnipresent
                : WeatherData? = null
        var rawMinutely = ""
        var rawHourly = ""
    }
}