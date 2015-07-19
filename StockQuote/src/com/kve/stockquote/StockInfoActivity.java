package com.kve.stockquote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class StockInfoActivity extends Activity {

	private static final String TAG = "STOCK_INFO_ACTIVITY";
	
	private TextView tvSqName;
	private TextView tvSqYearLow;
	private TextView tvSqYearHigh;
	private TextView tvSqDaysLow;
	private TextView tvSqDaysHigh;
	private TextView tvSqLastPrice;
	private TextView tvSqChange;
	private TextView tvSqDailyRange;
	
	static final String KEY_ITEM 		= "Stock";
	static final String KEY_NAME 		= "Symbol";
	static final String KEY_YEAR_LOW 	= "P-E";
	static final String KEY_YEAR_HIGH 	= "Earns";
	static final String KEY_DAYS_LOW 	= "Low";
	static final String KEY_DAYS_HIGH 	= "High";
	static final String KEY_LAST_PRICE 	= "Last";
	static final String KEY_CHANGE 		= "Change";
	static final String KEY_DAILY_RANGE	= "AnnRange";

	private String sqName 		= "";
	private String sqYearLow	= "";
	private String sqYearHigh	= "";
	private String sqDaysLow	= "";
	private String sqDaysHigh	= "";
	private String sqLastPrice	= "";
	private String sqChange		= "";
	private String sqDailyRange	= "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_info);
		
		tvSqName 		= (TextView) findViewById(R.id.tvSqName);
		tvSqYearLow 	= (TextView) findViewById(R.id.tvSqYearLow);
		tvSqYearHigh 	= (TextView) findViewById(R.id.tvSqYearHigh);
		tvSqDaysLow 	= (TextView) findViewById(R.id.tvSqDaysLow);
		tvSqDaysHigh	= (TextView) findViewById(R.id.tvSqDaysHigh);
		tvSqLastPrice 	= (TextView) findViewById(R.id.tvSqLastPrice);
		tvSqChange 		= (TextView) findViewById(R.id.tvSqChange);
		tvSqDailyRange 	= (TextView) findViewById(R.id.tvSqDailyRange);
		
		Intent intent = getIntent();
		String stockName = intent.getStringExtra(StockMainActivity.STOCK_SYMBOL);
		
		Log.d(TAG, "Before URL Creation");
		new MyAsyncTask().execute(stockName);
		Log.d(TAG, "After URL Creation");
		}
	
	
	
	
	 
	 public String getXMLObsoleteMaybe(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return result;
	   }

private class MyAsyncTask extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... args) {
		String result = getXML(args[0]);
				return null;
	}
	
	 public String getXML(String stockName) {
			// Create a new HttpClient and Post Header
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPost httppost = new HttpPost("http://www.webservicex.net/stockquote.asmx");
			 String result = "";
			 
			 try {
			     // Add your data
			     List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			     nameValuePairs.add(new BasicNameValuePair("symbol", stockName));
			     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			     // Execute HTTP Post Request
			     HttpResponse response = httpclient.execute(httppost);
			     
			     HttpEntity entity = response.getEntity();
			     if (entity != null) {
			         InputStream instream = entity.getContent();

			         result = convertStreamToString(instream);
			         Log.i("Read from server", result);
			     }

			 } catch (ClientProtocolException e) {
				 Log.d(TAG, "ClientProtocolException", e);

			 } catch (IOException e) {
				 Log.d(TAG, "IOException", e);
			}
		      return result;
		   }

		 
		 String convertStreamToString(java.io.InputStream is) {

			 java.util.Scanner s;
			 String result = "";
			 try {
				 s = new java.util.Scanner(is).useDelimiter("\\A");
				 result = s.hasNext() ? s.next() : "";
				 s.close();
			 } catch (Exception e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }

			 return result;
		 }
		 
	
}
	 
}


