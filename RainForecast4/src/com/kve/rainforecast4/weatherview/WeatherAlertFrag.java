package com.kve.rainforecast4.weatherview;

import java.util.ArrayList;

import com.kve.rainforecast4.*;
import com.kve.rainforecast4.data.*;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class WeatherAlertFrag extends Fragment {

	Activity parentActivity;

	TableLayout tlAlertTable;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.alert_scroll, container, false);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tlAlertTable 		= (TableLayout) getActivity().findViewById(R.id.tlAlertTable);

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
				 LayoutInflater layoutInflater = (LayoutInflater) parentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View newWalkRow = layoutInflater.inflate(R.layout.alert_item, null);
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
