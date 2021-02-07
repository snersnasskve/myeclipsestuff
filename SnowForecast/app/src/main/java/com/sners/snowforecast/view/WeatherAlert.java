package com.sners.snowforecast.view;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sners.snowforecast.*;
import com.sners.snowforecast.data.*;

public class WeatherAlert extends Activity {

	TableLayout tlAlertTable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.alert_scroll);
		tlAlertTable 		= (TableLayout) findViewById(R.id.tlAlertTable);

		addAlertsToScrollView();

	}

	
	private void addAlertsToScrollView()
	{
		ArrayList <AlertData> alerts = ForecastMainActivity.weatherData.getAlertsData();
		if (0 != alerts.size())
		{
			tlAlertTable.removeAllViews();
			for (int alertCounter = 0 ; alertCounter < alerts.size() ; alertCounter++)
			{
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
				View newWalkRow = inflater.inflate(R.layout.alert_item, null);
				//	TextViews
				TextView tvTitle 		= (TextView) newWalkRow.findViewById(R.id.tvAlertTitle);
				TextView tvDescription 	= (TextView) newWalkRow.findViewById(R.id.tvAlertDescription);
				TextView tvlink 		= (TextView) newWalkRow.findViewById(R.id.tvAlertLink);
				tvTitle.		setText(alerts.get(alertCounter).getTitle());
				tvDescription.	setText(alerts.get(alertCounter).getDescription());
				tvlink.			setText(alerts.get(alertCounter).getUri());

				Button btnAlertUri =  (Button) 	newWalkRow.findViewById(R.id.btnAlertUri );
				btnAlertUri.setOnClickListener(goToWebPageListener);
				tlAlertTable.addView(newWalkRow, alertCounter);
			}
		}
	}
	
	public OnClickListener goToWebPageListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//	Close keyboard if it's open
			LinearLayout llAlertItem	= (LinearLayout) v.getParent();
			TextView tvAlertLink 		= (TextView) llAlertItem.findViewById(R.id.tvAlertLink);
			String linkValue 			= tvAlertLink.getText().toString();
	
			//	Send intent to internet page
			Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkValue));
			startActivity(websiteIntent);
		}
	};
	


}
