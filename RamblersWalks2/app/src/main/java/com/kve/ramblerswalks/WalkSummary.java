package com.kve.ramblerswalks;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WalkSummary extends Activity {
	
	private TableLayout		tlWalkInfo;

	Map<String,String> requestedInfo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.walk_info);
		
		tlWalkInfo 		= (TableLayout) findViewById(R.id.tlWalkInfo);

		if (savedInstanceState == null) {

			Toast.makeText(getApplicationContext(), "Looking for walks", 
					Toast.LENGTH_LONG).show();

			//	Unpack the intent 
			requestedInfo = unpackIntent();

			//	For each weekday, build the url
			//	Send off a task to do the reading
			findWalks(requestedInfo);
			//	Call back to populate the walks
		}
		else
		{
			addWalksToScrollView();
		}


	}

	private Map<String,String> unpackIntent()
	{
		Map<String,String> requestedInfo = new HashMap<String, String>();
		Intent receivedIntent = getIntent();
		
		requestedInfo.put("PostCode", receivedIntent.getStringExtra("PostCode"));
		requestedInfo.put("Distance", receivedIntent.getStringExtra("Distance"));
		requestedInfo.put("StartDay", receivedIntent.getStringExtra("StartDay"));
		requestedInfo.put("EndDay", receivedIntent.getStringExtra("EndDay"));
		requestedInfo.put("StartMonthNo", receivedIntent.getStringExtra("StartMonthNo"));
		requestedInfo.put("EndMonthNo", receivedIntent.getStringExtra("EndMonthNo"));
		requestedInfo.put("Weekdays", receivedIntent.getStringExtra("Weekdays"));
		requestedInfo.put("ButtonPressed", receivedIntent.getStringExtra("ButtonPressed"));
		
		return requestedInfo;
	}
	
private void findWalks(Map<String,String> requestedInfo){
		
		if (null == RamblersMain.walks)
		{
			RamblersMain.walks = new ArrayList<WalkInfo>();
		}
		else
		{
			RamblersMain.walks.clear();
		}
		
		RamblersUrlBuilder ramblersBuilder = new RamblersUrlBuilder(requestedInfo);
	
		new UrlReaderAsync().execute(ramblersBuilder.getUrlList());
		
	}
	
private void addWalksToScrollView()
{
	if (0 == RamblersMain.walks.size())
	{
		alertNoWalksFound();
	}
	else
	{
		ArrayList<WalkInfo> filteredWalks = filterWalks();
		//	So need to filter out the walks we don't need as call gives us everything
		tlWalkInfo.removeAllViews();
		//	Sort Walks
		//	In walk info, get evening bool
		for (int walkCounter = 0 ; walkCounter < filteredWalks.size() ; walkCounter++)
		{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
			View newWalkRow = inflater.inflate(R.layout.walk_item, null);
			//	TextViews
			TextView tvTitle 		= (TextView) newWalkRow.findViewById(R.id.tvWalkTitle);
			TextView tvDescription 	= (TextView) newWalkRow.findViewById(R.id.tvWalkDescription);
			TextView tvlink 		= (TextView) newWalkRow.findViewById(R.id.tvWalkLink);
			tvTitle.		setText(filteredWalks.get(walkCounter).getTitle());
			tvDescription.	setText(filteredWalks.get(walkCounter).getDescription());
			tvlink.			setText(filteredWalks.get(walkCounter).getLink());

			Button btnGoToWalk =  (Button) 	newWalkRow.findViewById(R.id.btnGoToWalk );
			btnGoToWalk.setOnClickListener(goToWebPageListener);
			tlWalkInfo.addView(newWalkRow, walkCounter);
		}
	}
}


private ArrayList<WalkInfo> filterWalks()
{
	ArrayList<WalkInfo>  filteredWalks = new ArrayList<WalkInfo> ();
	
	for (WalkInfo walkInst : RamblersMain.walks)
	{
		if (walkInst.distanceInBounds(requestedInfo.get("Distance")))
		{
			filteredWalks.add(walkInst);
		}
	}
	return filteredWalks;
}

	public OnClickListener goToWebPageListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//	Close keyboard if it's open
			LinearLayout llWalkRow		= (LinearLayout) v.getParent();
			TextView tvWalkLink 	= (TextView) llWalkRow.findViewById(R.id.tvWalkLink);
			String linkValue 		= tvWalkLink.getText().toString();
	
			//	Send intent to internet page
			Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkValue));
			startActivity(websiteIntent);
		}
	};
	
	private void alertNoWalksFound()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(WalkSummary.this);
		builder.setTitle(R.string.no_walks_found_title);
		builder.setPositiveButton(R.string.ok, null);
		builder.setMessage(R.string.no_walks_found_message);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	

	private class UrlReaderAsync extends AsyncTask<ArrayList<String>, String, String>{

		@Override
		protected String doInBackground(ArrayList<String>... params) {
			ArrayList<String> urlList = params[0];
			ReadWalksJson readWalksJson = new ReadWalksJson();
			//	There is only one in the current permutation of the automation
			readWalksJson.readWalksUrl(urlList.get(0));
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//Collections.sort(RamblersMain.walks)	;
			addWalksToScrollView();
		}

	}

}



