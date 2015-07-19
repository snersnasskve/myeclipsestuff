package com.kve.rainforecast4.weatherview;

import com.kve.rainforecast4.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherPagerAdapter extends PagerAdapter {

	private SparseArray<View> views = new SparseArray<View>();
	 private Context context;
	
	  
	 public WeatherPagerAdapter(Context context) {
		 
		 this.context = context;
	    }

	  
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		   View view = (View)object;
		    ((ViewPager) container).removeView(view);
		    views.remove(position);
		    view = null;	}

	//	So I need to figure out how to add a single page - then I can work out the rest later
//http://stackoverflow.com/questions/9106740/how-to-update-textview-in-pageradapter-after-using-inflater-inside-instantiatei
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
	    LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   View thisPage = inflater.inflate(R.layout.dashboard, null);
        ((ViewPager) container).addView(thisPage, 0);
    		    ((ViewPager) container).addView(thisPage);
		    views.put(position, thisPage);
		    return thisPage;
		    }

	@Override
	public CharSequence getPageTitle(int position) {
		return "Item " + (position + 1);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
