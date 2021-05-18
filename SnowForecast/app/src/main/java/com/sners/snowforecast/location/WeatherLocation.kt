package com.sners.snowforecast.location

import android.text.TextUtils
import java.lang.NumberFormatException
import java.util.*

/**
 * A class representing a location, for storing in the menu
 * I'm not messing with this class until I have set up my menus
 */
class WeatherLocation {
    var name: String? = null
        private set
    private var latitudeStr: String? = null
    private var longitudeStr: String? = null
    var latitude: Double? = null
        private set
    var longitude: Double? = null
        private set

    //	No info
    constructor() {
        initEmptyValues()
    }

    //	All info
    constructor(aName: String, aLatitude: String, aLongitude: String) {
        initWithValues(aName, aLatitude, aLongitude)
    }

    //	Name and combined Lat and Long
    constructor(aName: String, aLatLong: String?) {
        val latLongParts = TextUtils.split(aLatLong, " : ")
        if (latLongParts.size == 2) {
            initWithValues(aName, latLongParts[0], latLongParts[1])
        } else {
            initEmptyValues()
        }
    }

    //	All info combined as per toString()
    constructor(locationInfoString: String?) {
        val locationParts = Arrays.asList(*TextUtils.split(locationInfoString, ":"))
        if (locationParts.size != 3) {
            initEmptyValues()
        } else {
            initWithValues(
                locationParts[0],
                locationParts[1].replace("latitude=", ""),
                locationParts[2].replace("longitude=", "")
            )
        }
    }

    private fun initEmptyValues() {
        setName("")
        setLatitude("")
        setLongitude("")
    }

    private fun initWithValues(aName: String, aLatitude: String, aLongitude: String) {
        setName(aName)
        setLatitude(aLatitude)
        setLongitude(aLongitude)
    }

    private fun setName(name: String) {
        this.name = name
    }

    private fun setLatitude(latitude: String) {
        latitudeStr = latitude
        try {
            this.latitude = latitudeStr!!.toDouble()
        } catch (e: NumberFormatException) {
            // TODO Auto-generated catch block
            this.latitude = 0.0
        }
    }

    private fun setLongitude(longitude: String) {
        longitudeStr = longitude
        try {
            this.longitude = longitudeStr!!.toDouble()
        } catch (e: NumberFormatException) {
            // TODO Auto-generated catch block
            this.longitude = 0.0
        }
    }

    override fun toString(): String {
        return "$name:latitude=$latitude:longitude=$longitude"
    }
}