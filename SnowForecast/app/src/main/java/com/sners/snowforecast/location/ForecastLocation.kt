package com.sners.snowforecast.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import java.io.IOException

/**
 * A class to get location
 *
 * @param locationMgr Location manager from Android
 * @param mContext Application context.
 */
class ForecastLocation(var locationMgr: LocationManager, var mContext: Context) {

    /**
     * @property minAccuracy Minimum accuracy we can accept as valid
     */
    private val minAccuracy = MIN_LAST_READ_ACCURACY

    /**
     * @property minTime Minimum time for last location read that we can accept as valid
     */
    private val minTime = FIVE_MIN.toFloat()

    /**
     * @property mLatitude Latitude
     */
    private var mLatitude = 0.0
    fun getmLongitude(): Double {
        return mLongitude
    }

    fun setmLongitude(mLongitude: Double) {
        this.mLongitude = mLongitude
    }

    /**
     * @property mLongitude Longitude
     */
    private var mLongitude = 0.0
    fun getmLatitude(): Double {
        return mLatitude
    }
    fun setmLatitude(mLatitude: Double) {
        this.mLatitude = mLatitude
    }

    /**
     * @property isValidLocation Is Valid Location bool
     */
    var isValidLocation = false






    /*
     * Location class
     * android:permission ACCESS_COARSE_LOCATION	cell tower or wifi
     * android:permission ACCESS_FINE_LOCATION		cell tower or wifi or GPS
     *
     */
    fun bestLastKnownLocation(): Location? {
        var bestResult: Location? = null
        var bestAccuracy = Float.MAX_VALUE
        var bestTime = Long.MIN_VALUE
        val matchingProviders = locationMgr.allProviders
        var location: Location? = null
        for (provider in matchingProviders) {
            if (ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                location = null
            } else {
                location = locationMgr.getLastKnownLocation(provider)
                if (location != null) {
                    val accuracy = location.accuracy
                    val time = location.time
                    if (accuracy < bestAccuracy) {
                        bestResult = location
                        bestAccuracy = accuracy
                        bestTime = time
                    }
                }
            }
        }

        // Return best reading or null
        return if (bestAccuracy > minAccuracy || bestTime < minTime || location == null) {
            setmLatitude(0.0)
            setmLongitude(0.0)
            null
        } else {
            setmLatitude(bestResult!!.latitude)
            setmLongitude(bestResult.longitude)
            bestResult
        }
    }


    fun locationFromPlace(locationName: String?, context: Context?) {

        isValidLocation = false
        val myLocation = Geocoder(context)
        val maxResults = 1
        try {
            val addressList = myLocation.getFromLocationName(locationName, maxResults)
            if (!addressList.isEmpty() && addressList[0].hasLatitude() && addressList[0].hasLongitude()) {
                setmLatitude(addressList[0].latitude)
                setmLongitude(addressList[0].longitude)
                isValidLocation = true
            }
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    companion object {
        private const val ONE_MIN = (1000 * 60).toLong()
        private const val FIVE_MIN = ONE_MIN * 5
        private const val MIN_LAST_READ_ACCURACY = 5000.0f
    }
}