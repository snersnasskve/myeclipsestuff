package com.kve.rainforecast4.view;



import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kve.rainforecast4.*;
import com.kve.rainforecast4.weatherview.*;

public class ForecastTabHolder extends FragmentActivity {

//http://androidsolution4u.blogspot.in/2013/04/tabnvigation-like-google-play-store-app.html
	
	
	//create object of FragmentPagerAdapter
    SectionsPagerAdapter mSectionsPagerAdapter;

    //viewpager to display pages
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_pager);
        
 
        // Create the adapter that will return a fragment for each of the five
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

		//setRetainInstance(true);

    }

    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // set the string passed from the service to the original intent
        setIntent(intent);

    }
    
    
    
    /**
     * A  FragmentPagerAdapter that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	public SparseArray<Fragment> pageMap;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            if (null == pageMap)
            {
            	pageMap = new SparseArray<Fragment>();
            }
       }

        //	Gota make a map of pages
        //	Gota figure out why Hourly and Minutely exclusive
        //	Gota ignore pages that aren't needed
        //	Gota retain stuff for rotation
        //	Gota remove that menu stuff from pages
        @Override
        public Fragment getItem(int position) {
        	
        	// getItem is called to instantiate the fragment for the given page.
        	// Return a DummySectionFragment (defined as a static inner class
        	// below) with the page number as its lone argument.

        	Fragment fragment = pageMap.get(position);


        	if (null == fragment)
        	{
        		if (0 == position)
        		{
        			fragment = new WeatherDashboardFrag();
        		}
        		else if (1 == position)
        		{
        			fragment = new WeatherCurrentFrag();
        		}
        		else if (2 == position)
        		{
        			fragment = new MinutelyPrecipFrag();
        		}
        		else if (3 == position)
        		{
        			fragment = new HourlyPrecipFrag();
        		}
        		else if (4 == position)
        		{
        			fragment = new WeatherAlertFrag();
        		}
        		else
        		{
        			//	We should never get here
        			fragment = new WeatherDashboardFrag();
        		}
        		pageMap.put(position, fragment );
        	}
        	return fragment;

        }


        @Override
        public int getCount() {
        	// Show 5 total pages.
        	return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch (position) {
            case 0:
                return "Dashboard";
            case 1:
                return "Details";
            case 2:
                return "Minutely";
            case 3:
                return "Hourly";
            case 4:
                return "Alerts";
            }
            return null;
        }
    }

   

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	
	
	
}
