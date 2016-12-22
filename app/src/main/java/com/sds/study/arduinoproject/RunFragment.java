package com.sds.study.arduinoproject;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.SimpleDateFormat;

public class RunFragment extends Fragment implements LocationListener {
    LocationManager locationManager;
    private static final long DISTANCE_REFRESH_GPS = 10;//10m
    private static final long MINUTE_REFRESH_GPS = 1000 * 3 * 1;//3초
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Location lastLocation;
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

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movement, null);

        txt_total = (TextView) view.findViewById(R.id.txt_total);
        txt_date = (TextView) view.findViewById(R.id.txt_date);
        txt_time = (TextView) view.findViewById(R.id.txt_time);
        txt_lat = (TextView) view.findViewById(R.id.txt_lat);
        txt_lng = (TextView) view.findViewById(R.id.txt_lng);
        txt_per = (TextView) view.findViewById(R.id.txt_per);

        Context main = getContext();

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

        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        Long current = System.currentTimeMillis();
        String currTime = format.format(current);
        String[] now = currTime.split(" ");
        date = now[0];
        time = now[1];
        lat = Double.toString(location.getLatitude());
        lng = Double.toString(location.getLongitude());
        Log.d("JSON", "lat:"+lat+", lng:"+lng);

        gmAsync.execute(date, time, lat, lng);
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
