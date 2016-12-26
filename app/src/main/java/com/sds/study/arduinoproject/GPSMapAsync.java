package com.sds.study.arduinoproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.MAP;
import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.RUNNING;

public class GPSMapAsync extends AsyncTask<String, Void, List> {
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
    public GPSMapAsync(MainActivity mainActivity) {
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
        sql="insert into position(lat,lng) values(?,?)";
        sql2="select * from position order by position_id desc";
    }

    protected List doInBackground(String... params) {
        Log.d(TAG, "do in background ...");

        Log.d(TAG, "db :   " + db);
        Log.d(TAG, "lat :  " + params[0] + "  lng   :   " + params[1]);
        db.execSQL(sql, new String[]{params[0], params[1]});
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

    protected void onPostExecute(List list) {

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
    }


    public void setFragmentData(String date,String time, String lat, String lng){
        txt_date = runFragment.txt_date;
        txt_time = runFragment.txt_time;
        txt_lat = runFragment.txt_lat;
        txt_lng = runFragment.txt_lng;
        txt_per = runFragment.txt_per;

        txt_date.setText(date);
        txt_time.setText(time);
        txt_lat.setText(lat);
        txt_lng.setText(lng);

    }
}
