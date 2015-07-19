package com.example.rainforecast2;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.kve.rainforecast2.R;

public class ForecastMainActivity extends FragmentActivity  {

	
	EditText 	etLocationPlaceName;
	TextView 	tvLocation;
	TextView 	tvStatus;
	Spinner  	spFavourites;
	ProgressBar pbReadWeather;

	ArrayList<WeatherLocation> weatherLocations;
	//	For the array adaptor - names only
	ArrayList<String> favourites;

	ArrayAdapter<String> favouritesAdapter;

	
	//	Location
	private static final DecimalFormat locationFormat = new DecimalFormat("0.00000");
	private WeatherLocation currentLocation;
	ForecastLocation forecastLocation;

	ForecastReader	 forecastReader;

	private SharedPreferences lastUsedInfo;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast_main);
		
		lastUsedInfo = getSharedPreferences("weatherinfo", MODE_PRIVATE);
		
		etLocationPlaceName = (EditText) 	findViewById(R.id.etLocationPlaceName);
		tvLocation 			= (TextView) 	findViewById(R.id.tvLocation);
		spFavourites 		= (Spinner)  	findViewById(R.id.spFavourites);
		tvStatus 			= (TextView) 	findViewById(R.id.tvStatus);
		pbReadWeather 		= (ProgressBar) findViewById(R.id.pbReadWeather);
	    pbReadWeather.setVisibility(View.INVISIBLE);

	    populateFavourites(lastUsedInfo.getString("favourites", null));
	        

		//favouritesAdapter = 
	 	//		ArrayAdapter.createFromResource(ForecastMainActivity.this, favourites, );
		favouritesAdapter = new ArrayAdapter<String>(ForecastMainActivity.this,
                android.R.layout.simple_list_item_1,favourites);
		favouritesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spFavourites.setAdapter(favouritesAdapter);
	 	
  	     //	This needs to be called from Android class
        LocationManager locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
  
        forecastLocation = new ForecastLocation(locationMgr);
        forecastReader	 = new ForecastReader();
        
		spFavourites.setOnItemSelectedListener(favouriteItemSelected);

	}

    
    @Override
    protected void onResume() {
    	super.onResume();
    	if (null == currentLocation)
    	{
    		//	Only read it the first time
    		readLocationFromPhone();
    	}
    }
    
	@Override
	protected void onPause() {
		saveFavourites();
		super.onPause();

	}

	
	@Override
	protected void onStop() {
		saveFavourites();
		super.onStop();

	}

	


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.forecast_main, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_favourite) {
        	boolean success = getLocationFromAddress();
        	if (success)
        	{
        		addFavourite();
        	}
               return true;
        }
     	else  if (id == R.id.action_remove_favourite) {
        	boolean success = getLocationFromAddress();
        	if (success)
        	{
        		//forecastMainFragment.spFavourites.doSomthing()
         		deleteFavourite();
         	         	}
     	}
       return super.onOptionsItemSelected(item);
    }

	public void saveFavourites()
	{
		SharedPreferences.Editor prefsEditor = lastUsedInfo.edit();
		prefsEditor.clear();
		prefsEditor.putString("favourites", 	favouritesToString());
		prefsEditor.apply();
		prefsEditor.commit();

	}

	
   
		
	/////////////////////////////////////////////
	//	Location
	/////////////////////////////////////////////
    private void readLocationFromPhone()
    {
    	String statusString = "Searching...";
        pbReadWeather.setVisibility(View.VISIBLE);
         	tvStatus.setText(statusString);

    	//	Get location from phone
    	Location bestLocation = forecastLocation.bestLastKnownLocation();

    	if (null != bestLocation)
    	{
    		currentLocation = new WeatherLocation("Local", "" + bestLocation.getLatitude(), "" + bestLocation.getLongitude());
    		String locationString = locationFormat.format(currentLocation.getLatitude())+ "  :  "+ 
    				locationFormat.format(currentLocation.getLongitude());
    		tvLocation.setText(locationString);
   	  		statusString = "Current Location found";
   	  	    		//	getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
    	}
    	else
    	{
    		statusString = "Please enable GPS";
    	}
     	tvStatus.setText(statusString);
	     pbReadWeather.setVisibility(View.INVISIBLE);
        }

    private boolean getLocationFromAddress()
    {
    	String statusString = "Searching...";
       pbReadWeather.setVisibility(View.VISIBLE);
           	tvStatus.setText(statusString);
    	boolean success = false;

    	if (etLocationPlaceName.getText().toString().length() > 3)
    	{
    		//	Get location from text on screen
    		forecastLocation.locationFromPlace(etLocationPlaceName.getText().toString(), getApplication());
    		if (forecastLocation.isValidLocation())
    		{
     			String locationString = locationFormat.format(forecastLocation.getmLatitude())+ "  :  "+ 
    					locationFormat.format(forecastLocation.getmLongitude());
     	  		tvLocation.setText(locationString);
     	  		statusString = "Location for address found";
     	  		success = true;
       		}
    		else
    		{
    			statusString = "Try later when you have internet";
    		}
    	}
    	else
    	{
    		//	Get location from phone
    		statusString = "Please enter a place name !";
    	}

     	tvStatus.setText(statusString);
	     pbReadWeather.setVisibility(View.INVISIBLE);
          	return success;
   }
    
    
	/////////////////////////////////////////////
	//	Action on Buttons
	/////////////////////////////////////////////

	public void forecastForAddress(View view)
	{
		getLocationFromAddress();
		if (etLocationPlaceName.getText().toString().length() > 3)
		{
			Toast.makeText(getApplicationContext(), "Show forecast for this address", 
				Toast.LENGTH_SHORT).show();
			getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
		}

	}

	public void forecastForLocation(View view)
	{
		Toast.makeText(getApplicationContext(), "Show forecast for this location", 
				Toast.LENGTH_SHORT).show();
		getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());

	}

	public void forecastForFavourite(View view)
	{
		Toast.makeText(getApplicationContext(), "Show forecast for this preset", 
				Toast.LENGTH_SHORT).show();
		getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());

	}

	public void refreshScreen(View view)
	{
		//readLocationFromPhone();
		Toast.makeText(getApplicationContext(), "Re-read loaction and show forecast for this location", 
				Toast.LENGTH_SHORT).show();
		getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
	}

	/////////////////////////////////////////////
	//	Favourites
	/////////////////////////////////////////////

	
	private void populateFavourites(String favouritesString)
	{
		favourites 			= new ArrayList<String> ();
		weatherLocations 	= new ArrayList<WeatherLocation>();
		favourites.add("");
		weatherLocations.add(new WeatherLocation());
		if (null != favouritesString)
		{
			//asList is a disaster, bar is being used as a regex
			List <String> tempLocList = (Arrays.asList(TextUtils.split(favouritesString, "\\|")));
			for (String locStringInst : tempLocList)
			{
				WeatherLocation locInst = new WeatherLocation(locStringInst);
				weatherLocations.	add(locInst);
				favourites.			add(locInst.getName());
			}
		}
	}
	
	OnItemSelectedListener favouriteItemSelected = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			etLocationPlaceName.setText( weatherLocations.get(position).getName());
			if ( weatherLocations.get(position).getName().length() > 3)
			{
				tvLocation.setText("" + weatherLocations.get(position).getLatitude() + ""+ "  :  " + weatherLocations.get(position).getLongitude());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

 		}
 	};

		
	private String favouritesToString()
	{
		String favString = null;
		if (weatherLocations.size() != 0)
		{
			ArrayList <String> weatherStringArray = new ArrayList <String>();
			for (WeatherLocation locInst: weatherLocations)
			{
				if (!locInst.getName().equals(""))
				{
					weatherStringArray.add(locInst.toString());
				}
			}
			favString = TextUtils.join("|", weatherStringArray);
		}
		return favString;
				
	}

	
	private void addFavourite()
	{
		String statusString = "Please enter valid place name";
		String placeName 	= etLocationPlaceName.getText().toString();
		if (placeName.length() > 3)
		{
			if (favourites.contains(placeName))
			{
				statusString = placeName + " is already in your favourites";
			}
			else
			{
			WeatherLocation newLocation = new WeatherLocation(placeName, tvLocation.getText().toString());
			if ("" != newLocation.getName())
			{
				weatherLocations.add(newLocation);
				favouritesAdapter.add(placeName);
				spFavourites.setSelection(favourites.size() - 1);
				statusString = placeName + " has been added";
			}
			else
			{
				statusString = "Get location before adding to favourites";
			}
			}
		}
		tvStatus.setText(statusString);
	}

	private void deleteFavourite()
	{
		String statusString = "Please select the place you want to delete";
		String placeName 	= etLocationPlaceName.getText().toString();
		if (placeName.length() > 3)
		{
			WeatherLocation placeToDelete = null;
			for (WeatherLocation locInst : weatherLocations)
			{
				if (locInst.getName().equals(placeName))
				{
					placeToDelete = locInst;
				}
			}
			if (null != placeToDelete)
			{
				weatherLocations.remove(placeToDelete);
				favouritesAdapter.remove(placeName);
				statusString = placeName + " has been deleted";
				spFavourites.setSelection(0);
			}
		}
		tvStatus.setText(statusString);
	}



	/////////////////////////////////////////////
	//	Read forecast
	/////////////////////////////////////////////

	private void getWeatherData(double mLatitude, double mLongitude) {
	     pbReadWeather.setVisibility(View.VISIBLE);
	         		new ReadWeatherAsyncTask().execute(mLatitude, mLongitude);
	}
	
	 // Use AsyncTask if you need to perform background tasks, but also need
	 // to change components on the GUI. Put the background operations in
	// doInBackground. Put the GUI manipulation code in onPostExecute
	
	private class ReadWeatherAsyncTask extends AsyncTask<Double, Double, String> {

		@Override
		protected String doInBackground(Double... params) {
			String fullResult = forecastReader.readWeatherForecast(params[0], params[1]);
			return fullResult;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			showCurrentWeather(result);
		     pbReadWeather.setVisibility(View.INVISIBLE);
		     		    		}

	}
	 
	private void showCurrentWeather(String weatherJson)
 	{
 		if (null != weatherJson)
 		{
 			Intent currentIntent = new Intent(ForecastMainActivity.this, CurrentWeather.class);
 			currentIntent.putExtra("jsonData", weatherJson);
 			startActivity(currentIntent);
 		}
 		else 
 		{
 		   	tvLocation.setText("Failed to read weather info for this location.");
 		}
 	}

}
