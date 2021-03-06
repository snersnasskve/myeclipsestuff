package com.kve.xmascardlist_v3;

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


/**
 * Created by PC on 27/11/2017.
 */

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.addPreferencesFromResource(R.xml.filter_prefs);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
