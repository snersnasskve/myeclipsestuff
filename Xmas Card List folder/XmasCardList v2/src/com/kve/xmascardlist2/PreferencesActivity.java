package com.kve.xmascardlist2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreferencesActivity extends PreferenceActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		showPreferencesFragment(savedInstanceState);
	}

	private void showPreferencesFragment(Bundle savedInstanceState) {
		if (null == savedInstanceState)
		{
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			Fragment fragment = new FilterPrefsFragment();
			transaction.replace(android.R.id.content, fragment);
			transaction.commit();
		}
	}
	
	public static class FilterPrefsFragment extends PreferenceFragment {
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			Log.d("F", "I'm attached to an activity - I have a context!");
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			this.addPreferencesFromResource(R.xml.filter_prefs);
			return super.onCreateView(inflater, container, savedInstanceState);
		}
	}
}
