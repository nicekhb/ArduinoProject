package com.sds.study.arduinoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.container);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_service) {
            startService();
            return true;
        }else if (id == R.id.action_stopservice) {
            stopService();
            Snackbar.make(getWindow().getDecorView().getRootView(), "안돼더라고,,앱을 완전 종료해라", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        }else if (id == android.R.id.home) {
            mPager.setCurrentItem(0);
            setBackButton(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBackButton(boolean isShow){
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
        getSupportActionBar().setDisplayShowHomeEnabled(isShow);
    }

    public void onClick(View view) {
        mPager.setCurrentItem(1);
        setBackButton(true);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void startService(){
        Intent intent = new Intent(this, AlertService.class);
        startService(intent);
    }

    public void stopService(){
        Intent intent = new Intent(this, AlertService.class);
        stopService(intent);
    }
}
