package com.ramblerswalks3.sners.ramblerswalk3;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

public class RamblersStatePagerAdapter extends FragmentStatePagerAdapter {

    public final List<Fragment> mFragmentList = new ArrayList();
    public final List<String> mFragmentTitleList = new ArrayList();

    public  RamblersStatePagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    private void addFragment(Fragment fragment, String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
