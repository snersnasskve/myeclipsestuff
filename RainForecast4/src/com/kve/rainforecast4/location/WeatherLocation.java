package com.kve.rainforecast4.location;

import java.util.Arrays;
import java.util.List;

import android.text.TextUtils;

public class WeatherLocation {

	private String name;
	private String latitudeStr;
	private String longitudeStr;
	private Double latitude;
	private Double longitude;

	//	No info
	public WeatherLocation()
	{
		initEmptyValues();
	}
	
	//	All info
	public WeatherLocation(String aName, String aLatitude, String aLongitude)
	{
		initWithValues(aName, aLatitude, aLongitude);
	}
	
	//	Name and combined Lat and Long
	public WeatherLocation(String aName, String aLatLong)
	{
		String[] latLongParts = TextUtils.split(aLatLong, " : ");
		if (latLongParts.length == 2)
		{
			initWithValues(aName, latLongParts[0], latLongParts[1]);
		}
		else
		{
			initEmptyValues();
		}
	}
	
	//	All info combined as per toString()
	public WeatherLocation(String locationInfoString)
	{
		List <String> locationParts = Arrays.asList(TextUtils.split(locationInfoString, ":"));
		if (locationParts.size() != 3)
		{
			initEmptyValues();
		}
		else
		{
			initWithValues(locationParts.get(0),
					locationParts.get(1).replace("latitude=", ""),
					locationParts.get(2).replace("longitude=", ""));
		}
	}

	
	private void initEmptyValues()
	{
		setName("");
		setLatitude("");
		setLongitude("");
	}

	private void initWithValues(String aName, String aLatitude, String aLongitude)
	{
		setName(aName);
		setLatitude(aLatitude);
		setLongitude(aLongitude);
	}

	
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public Double getLatitude() {
		return latitude;
	}
	private void setLatitude(String latitude) {
		this.latitudeStr = latitude;
		try {
			this.latitude = Double.parseDouble(latitudeStr);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			this.latitude = 0.0;
		}
	}
	public Double getLongitude() {
		return longitude;
	}
	private void setLongitude(String longitude) {
		this.longitudeStr = longitude;
		try {
			this.longitude = Double.parseDouble(longitudeStr);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			this.longitude = 0.0;
		}
	}

	public String toString()
	{
		String weatherLocString = name + ":latitude="+ latitude + ":longitude=" + longitude;
		return weatherLocString;
	}
}
