package com.sners.snowforecast.location;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

public class ForecastLocation {
	
	
	LocationManager locationMgr;
	
	private static final long ONE_MIN = 1000 * 60;
	private static final long FIVE_MIN = ONE_MIN * 5;
	private static final float MIN_LAST_READ_ACCURACY = 5000.0f;
	
	private float minAccuracy 	= MIN_LAST_READ_ACCURACY;
	private float minTime		= FIVE_MIN;

	private double mLatitude 		= 0;
	private double mLongitude 		= 0;
	private boolean	validLocation 	= false;

	public ForecastLocation(LocationManager locationMgr)
	{
		this.locationMgr = locationMgr;
	}
	
	public double getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public boolean isValidLocation() {
		return validLocation;
	}

	public void setValidLocation(boolean validLocation) {
		this.validLocation = validLocation;
	}

	/*
	 * Location class
	 * android:permission ACCESS_COARSE_LOCATION	cell tower or wifi
	 * android:permission ACCESS_FINE_LOCATION		cell tower or wifi or GPS
	 * 
	 */

	public Location bestLastKnownLocation() {

 		Location bestResult = null;
 		float bestAccuracy = Float.MAX_VALUE;
 		long bestTime = Long.MIN_VALUE;

 		List<String> matchingProviders = locationMgr.getAllProviders();

 		for (String provider : matchingProviders) {

 			Location location = locationMgr.getLastKnownLocation(provider);

 			if (location != null) {

 				float accuracy = location.getAccuracy();
 				long time = location.getTime();

 				if (accuracy < bestAccuracy) {

 					bestResult = location;
 					bestAccuracy = accuracy;
 					bestTime = time;

 				}
 			}
 		}

 		// Return best reading or null
 		if (bestAccuracy > minAccuracy || bestTime < minTime) {
			this.setmLatitude(0);
			this.setmLongitude(0);
 			return null;
 		} else {
 			this.setmLatitude(bestResult.getLatitude());
			this.setmLongitude(bestResult.getLongitude());
			return bestResult;
 		}
 	}
 	

	// Json location from address
	//	https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
	// XML location from address
	//	https://maps.googleapis.com/maps/api/geocode/xml?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
	/*
	 * status
	 * 
    "OK" indicates that no errors occurred; the address was successfully parsed and at least one geocode was returned.
    "ZERO_RESULTS" indicates that the geocode was successful but returned no results. This may occur if the geocoder was passed a non-existent address.
    "OVER_QUERY_LIMIT" indicates that you are over your quota.
    "REQUEST_DENIED" indicates that your request was denied.
    "INVALID_REQUEST" generally indicates that the query (address, components or latlng) is missing.
    "UNKNOWN_ERROR" indicates that the request could not be processed due to a server error. The request may succeed if you try again.

	 */
	
	//	api key for google locations
	//	AIzaSyDJbwty38gHjuWWKXLVw85C2XmV7wJPrU4
	/*
	public Location locationForAddress(String place)
	{
	HttpClient httpclient = new DefaultHttpClient();   
	String result = "";
	ResponseHandler<String> handler = new BasicResponseHandler();   
	byte[] utf8s;
	        utf8s = txtSearch.getText().toString().getBytes("UTF-8");
	        String place = new String(utf8s, "UTF-8"); 
	        place = place.trim().replace(" ", "+");

	        HttpGet request = new HttpGet("http://maps.google.com/maps/geo?q="+place+"&output=xml&oe=utf8");

	    result = httpclient.execute(request, handler);
	    String coordonees = result.substring(result.indexOf("<coordinates>"), result.indexOf("</coordinates>"));
	    
	    return  new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
	}
	
	
	public Location locationForAddress2(String NameString)
	{
		String UrlCity = "http://maps.googleapis.com/maps/api/geocode/json?address=" + NameString + "&sensor=false";

    JsonObjectRequest stateReq = new JsonObjectRequest(Request.Method.GET, UrlState, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            JSONObject location;
            try {
                // Get JSON Array called "results" and then get the 0th
                // complete object as JSON
                location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                // Get the value of the attribute whose name is
                // "formatted_string"
                stateLocation = new LatLng(location.getDouble("lat"), location.getDouble("lng"));
                // System.out.println(stateLocation.toString());
            } catch (JSONException e1) {
                e1.printStackTrace();

            }
        }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Error.Response", error.toString());
        }
    });
    // add it to the RequestQueue
    reqQueue.add(stateReq);
	}
	*/
	//////crashing here
	public void locationFromPlace(String locationName, Context context)
	{
		//	I don't understand why it only errors when not plugged in
		//	http://stackoverflow.com/questions/15711499/get-latitude-and-longitude-with-geocoder-and-android-google-maps-api-v2
		//	Uses do in background, but has extra stuff so not doing it today
		this.setValidLocation(false);
		Geocoder myLocation = new Geocoder(context);
		int maxResults = 1;
	    try {
			List<Address> addressList = myLocation.getFromLocationName( locationName,  maxResults);
			if (!addressList.isEmpty() &&  addressList.get(0).hasLatitude() && addressList.get(0).hasLongitude())
			{
				this.setmLatitude(addressList.get(0).getLatitude());
				this.setmLongitude(addressList.get(0).getLongitude());
				this.setValidLocation(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
