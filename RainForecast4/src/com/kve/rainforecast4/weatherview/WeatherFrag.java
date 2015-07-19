package com.kve.rainforecast4.weatherview;

import java.util.ArrayList;

import com.kve.rainforecast4.ForecastMainActivity;
import com.kve.rainforecast4.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class WeatherFrag extends Fragment {

	Activity parentActivity;

	public WeatherFrag() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActivity = activity;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		setHasOptionsMenu(false);
	}


	//	Common to the overview pages
	//	Stuff that doesn't do well with concurrency et al in multiple classes
	protected String getWeatherIconName()
	{
		String	iconName = ForecastMainActivity.weatherData.getHeadlineIcon().replaceAll("-", "_");
		return iconName;
	}

	protected int getWeatherIconId(String iconName)
	{
		int iconId 	= 	getResources().getIdentifier(iconName, "drawable", parentActivity.getPackageName());
		return iconId;
	}

	protected void setWeatherActivityIcons() {

		WeatherIconGallery iconGallery = new WeatherIconGallery();
		ArrayList <String> qualIcons = iconGallery.getWeatherActivityIcons();


		inflateWeatherActivityIcons(qualIcons);
	}


	protected void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		//	Implement me
	}

	protected View convertViewForIcon(String iconName) {
		LayoutInflater layoutInflater = (LayoutInflater) parentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		int iconId = getResources().getIdentifier(iconName, "drawable", parentActivity.getPackageName());
			View convertView = layoutInflater.inflate(R.layout.icon_gallery, null);

			final ImageView img = (ImageView) convertView.findViewById(R.id.ivIconGalleryItem);
			img.setImageResource(iconId);

		return convertView;
	}


}



