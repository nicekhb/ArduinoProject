package com.sds.study.arduinoproject;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by student on 2016-12-09.
 */

public class AlertService extends Service implements View.OnTouchListener{
    private View view;
    private WindowManager manager;
    WindowManager.LayoutParams mParams;

    private float mTouchX, mTouchY;
    private int mViewX, mViewY;

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.alert_floating, null);

        /*WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);*/

        view.setOnTouchListener(this);

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.TOP | Gravity.LEFT;

        manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        manager.addView(view, mParams);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchX = motionEvent.getRawX();
                mTouchY = motionEvent.getRawY();
                mViewX = mParams.x;
                mViewY = mParams.y;

                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                int x = (int) (motionEvent.getRawX() - mTouchX);
                int y = (int) (motionEvent.getRawY() - mTouchY);

                mParams.x = mViewX + x;
                mParams.y = mViewY + y;

                manager.updateViewLayout(view, mParams);

                break;
        }

        return true;
    }
}
