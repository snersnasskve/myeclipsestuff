package com.kve.rainforecast1;

import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

// http://www.newthinktank.com/2013/07/android-development-15/
//	14 mintues

/*
 * https://api.forecast.io/forecast/a9e65eeef8627d4f2cb554e39234ee6f/37.8267,-122.423
 *	https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE
 *	https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE,TIME
 */

public class RainForecastMainActivity extends ActionBarActivity {

	private static final DecimalFormat locationFormat = new DecimalFormat("0.00000");

	private Location currentLocation;
	
	ForecastLocation forecastLocation;
	ForecastReader	 forecastReader;

	TextView tvLocation;
	EditText etLocationPlaceName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_forecast_main);
        
        tvLocation = 			(TextView) findViewById(R.id.tvLocation);
        etLocationPlaceName = 	(EditText) findViewById(R.id.etLocationPlaceName);

        //	This needs to be called from Android class
        LocationManager locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
  
        forecastLocation	= new ForecastLocation(locationMgr);
        forecastReader	 	= new ForecastReader();
        
        updateSummaryScreen();
       
      
    }

   
   //	On resume not used as what would we do if not read and call the next screen - would be irritating

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rain_forecast_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
 // Get the last known location from all providers
 	// return best reading is as accurate as minAccuracy and
 	// was taken no longer then minTime milliseconds ago
    
    private void updateSummaryScreen()
    {
    	String locationString = "Searching...";
		tvLocation.setText(locationString);

    	if (etLocationPlaceName.getText().toString().length() > 3)
    	{
    		//	Get location from text on screen
    		forecastLocation.locationFromPlace(etLocationPlaceName.getText().toString(), getApplication());
    		if (forecastLocation.isValidLocation())
    		{
     			locationString = locationFormat.format(forecastLocation.getmLatitude())+ "  :  "+ 
    					locationFormat.format(forecastLocation.getmLongitude());
     			getWeatherData(forecastLocation.getmLatitude(), forecastLocation.getmLongitude());
     		}
    		else
    		{
    			locationString = "Try later when you have internet";
    		}
    	}
    	else
    	{
    		//	Get location from phone
    		currentLocation = forecastLocation.bestLastKnownLocation();

    		if (null != currentLocation)
    		{
    			locationString = locationFormat.format(currentLocation.getLatitude())+ "  :  "+ 
    					locationFormat.format(currentLocation.getLongitude());
      			getWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
      		     		}
    		else
    		{
    			locationString = "Please enable GPS";
    		}
    	}
    	tvLocation.setText(locationString);
   }

	public void refreshSummary(View view)
 	{
 		updateSummaryScreen();
 	}

 	private void showCurrentWeather(String weatherJson)
 	{
 		if (null != weatherJson)
 		{
 			Intent currentIntent = new Intent(RainForecastMainActivity.this, CurrentWeather.class);
 			currentIntent.putExtra("jsonData", weatherJson);
 			startActivity(currentIntent);
 		}
 		else 
 		{
 		   	tvLocation.setText("Failed to read weather info for this location.");
 		}
 	}
 	
 	private void getWeatherData(double mLatitude, double mLongitude) {
	// TODO Auto-generated method stub
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
		}
 
 	}

}
