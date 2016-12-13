package com.sds.study.arduinoproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by student on 2016-12-13.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[2];
        fragments[0] = new MainFragment();
        fragments[1] = new SubFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
