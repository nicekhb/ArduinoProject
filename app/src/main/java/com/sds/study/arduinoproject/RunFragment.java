package com.sds.study.arduinoproject;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  RunFragment extends Fragment implements LocationListener {
    String TAG;
    LocationManager locationManager;
    private static final long DISTANCE_REFRESH_GPS = 10;//10m
    private static final long MINUTE_REFRESH_GPS = 1000 * 3 * 1;//3초
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Location lastLocation;
    static LatLng lastPosition;
    String locationProvider;
    GPSMapAsync gmAsync;
    String date;
    String time;
    String lat;
    String lng;
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    TextView txt_total;
    TextView txt_date;
    TextView txt_time;
    TextView txt_lat;
    TextView txt_lng;
    TextView txt_per;
    boolean setGPS = false;
    private static final int GPS_REQUESET_CODE = 2000;
    private static final int LOCATION_REQUEST_CODE = 3000;
    MyDBHelper dbHelper;
    SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getName();
        View view = inflater.inflate(R.layout.fragment_movement, null);

        txt_total = (TextView) view.findViewById(R.id.txt_total);
        txt_date = (TextView) view.findViewById(R.id.txt_date);
        txt_time = (TextView) view.findViewById(R.id.txt_time);
        txt_lat = (TextView) view.findViewById(R.id.txt_lat);
        txt_lng = (TextView) view.findViewById(R.id.txt_lng);
        txt_per = (TextView) view.findViewById(R.id.txt_per);

        Context main = getContext();
        Log.d(TAG, "MainActivity     :  " + (MainActivity) getContext());
        gmAsync = new GPSMapAsync((MainActivity) getContext());

      //db선언
        dbHelper = new MyDBHelper(main,"running.lite",null,1);
        db = dbHelper.getWritableDatabase();

        locationManager = (LocationManager) main.getSystemService(main.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(main, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(main, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINUTE_REFRESH_GPS, DISTANCE_REFRESH_GPS, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINUTE_REFRESH_GPS, DISTANCE_REFRESH_GPS, this);
        locationProvider = LocationManager.GPS_PROVIDER;

        lastLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastLocation != null) {
            Long currentTime = System.currentTimeMillis();
            Log.d(TAG, "currentTIme??   " + currentTime.toString());
            Date today = new Date(currentTime);
            String now = format.format(today);
            String[] day = now.split(" ");
            date = day[0];
            time = day[1];
            Log.d(TAG, day[0].toString());
            Log.d(TAG, "lat=" + lastLocation.getLatitude());
            Log.d(TAG, "lng=" + lastLocation.getLongitude());
            lat = Double.toString(lastLocation.getLatitude());
            lng = Double.toString(lastLocation.getLongitude());
            lastPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

            txt_date.setText(date);
            txt_time.setText(time);
            txt_lat.setText(lat);
            txt_lng.setText(lng);

        } else {
            Log.d(TAG, "last Location not Found");

        }
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "llocation changed");
        Long current = System.currentTimeMillis();
        String currTime = format.format(current);
        String[] now = currTime.split(" ");
        date = now[0];
        time = now[1];
        lat = Double.toString(location.getLatitude());
        lng = Double.toString(location.getLongitude());
        txt_date.setText(date);
        txt_time.setText(time);
        txt_lat.setText(lat);
        txt_lng.setText(lng);

        Log.d(TAG, "lat:  " + lat + ", lng:  " + lng);
        Log.d(TAG, "gmAsync   :    " + gmAsync);
        gmAsync.execute( lat, lng);
    }

 /*   //GPS 활성화를 위한 다이얼로그 보여주기
    private void showGPSDisabledAlert(){
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setMessage("GPS 를 활성화 하시겠습니까?").setCancelable(false).setPositiveButton("SETTING", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent,GPS_REQUESET_CODE);
            }
        });
        alert.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.cancel();
            }
        });
        AlertDialog ad = alert.create();
        alert.show();
    }
    //GPS 활성화 결과처리*/

/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_REQUESET_CODE:
                if (locationManager == null) {
                    locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                }
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    setGPS=true;
                }
        }

    }*/

    public void showMsg(String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title).setMessage(msg).show();
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
