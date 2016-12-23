package com.sds.study.arduinoproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by student on 2016-12-13.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    final static int MAIN = 2;
    final static int SUB = 3;
    final static int EXERCISE = 4;
    final static int RUNNING = 1;
    final static int MAP = 0;
    Fragment[] fragments;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[5];
        fragments[MAIN] = new MainFragment();
        fragments[SUB] = new SubFragment();
        fragments[EXERCISE] = new ExerciseFragment();
        fragments[RUNNING] = new RunFragment();
        fragments[MAP] = new MapFragment();
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
