package com.sds.study.arduinoproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.MAP;
import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.RUNNING;

public class GPSMapAsync2 extends AsyncTask<String, Void, List> {
    String TAG;
    RunFragment runFragment;
    MapFragment mapFragment;

    SQLiteDatabase db;

    TextView txt_date;
    TextView txt_time;
    TextView txt_lat;
    TextView txt_lng;
    TextView txt_per;

    PolylineOptions polyOptions;
    LatLng position;
    Location lastLocation;
    //  SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    ArrayList<LatLng> list;
    String sql;
    String sql2;
    public GPSMapAsync2(MainActivity mainActivity) {
        TAG = this.getClass().getName();

        runFragment = (RunFragment)(((ScreenSlidePagerAdapter)mainActivity.mPagerAdapter).fragments[RUNNING]);
        mapFragment=(MapFragment)(((ScreenSlidePagerAdapter)mainActivity.mPagerAdapter).fragments[MAP]);
        lastLocation = runFragment.lastLocation;


    }
//헤더파일 만들어서 보내는거
    protected void onPreExecute() {
        Log.d(TAG, "on Pre Execute..  ");
        super.onPreExecute();
        list = new ArrayList<LatLng>();
        db = runFragment.db;

        sql2="select * from position order by position_id desc";
    }

    protected List doInBackground(String... params) {
        Log.d(TAG, "do in background ...");

        Log.d(TAG, "db :   " + db);
        Cursor cursor=db.rawQuery(sql2, null);
        if (cursor.moveToNext()) {
            LatLng point=null;
            Double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
            Double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));
            point = new LatLng(lat,lng);
            Log.d(TAG, " db select  lat: " + cursor.getString(cursor.getColumnIndex("lat")));
            Log.d(TAG, " db  lng :   " + cursor.getString(cursor.getColumnIndex("lng")));

            list.add(point);

        }
        return list;
    }
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(List listt) {

        Log.d(TAG, "on post Execute");

        /*for (int i = 0; i < list.size(); i++) {
            polyOptions = MapFragment.polylineOptions;
            LatLng position = null;
            position = (LatLng) list.get(i);
            polyOptions.add(position);

        }

        //polyOptions = MapFragment.polylineOptions;
        //polyOptions.add(position);
        mapFragment.mMap.addPolyline(polyOptions);*/
        //mapFragment.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));


        polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(list);
        mapFragment.mMap.addPolyline(polyOptions);
    }

}
