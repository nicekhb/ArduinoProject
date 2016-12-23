package com.sds.study.arduinoproject;


import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.RUNNING;

public class GPSMapAsync extends AsyncTask<String, Void, String> {
    String TAG;
    RunFragment runFragment;
    URL url;
    HttpURLConnection con;
    TextView txt_date;
    TextView txt_time;
    TextView txt_lat;
    TextView txt_lng;
    TextView txt_per;

    Polyline polyline;
    PolylineOptions polyOptions;
    Location lastLocation;
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public GPSMapAsync(MainActivity mainActivity) {
        TAG = this.getClass().getName();
       //lastLocation = mainActivity.lastLocation;
       runFragment = (RunFragment)(((ScreenSlidePagerAdapter)mainActivity.mPagerAdapter).fragments[RUNNING]);
       if (lastLocation != null) {
            Long currentTime = System.currentTimeMillis();
            Log.d(TAG,"currentTIme??"+ currentTime.toString());
            Date today = new Date(currentTime);
            String now = format.format(today);
            String[] day = now.split(" ");
            Log.d(TAG, day[0].toString());
            Log.d(TAG,"run txt_date:  "+ runFragment.txt_date);
            Log.d(TAG,"run    :    "+ runFragment);
            Log.d(TAG, "lat=" + lastLocation.getLatitude());
            Log.d(TAG, "lng=" + lastLocation.getLongitude());

        }
    }

    protected void onPreExecute() {
        super.onPreExecute();

        Log.d(TAG, "on pre Execute");
    }

    protected String doInBackground(String... params) {
        BufferedReader bffr = null;
        BufferedWriter bffw = null;
        StringBuffer sb = null;

        try {
            url = new URL("http://192.168.0.3:9090/map/list.jsp");
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            bffr = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            sb = new StringBuffer();

            String jsonData = writeJSON(params[0], params[1], params[2], params[3]);
            Log.d(TAG, jsonData);
            //write 먼저

            String data=null;
            while ((data = bffr.readLine())!=null) {
                Log.d(TAG, data);
                sb.append(data);
            }

            con.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String date = jsonObject.getString("date");
            String time = jsonObject.getString("time");
            Double lat = Double.parseDouble(jsonObject.getString("lat"));
            Double lng = Double.parseDouble(jsonObject.getString("lng"));
            LatLng position = new LatLng(lat, lng);

          /*  polyOptions.add(position);
            polyOptions.color(Color.BLUE);

            polyline = mainActivity.googleMap.addPolyline(polyOptions);*/
        //    mainActivity.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "on Execute");
        super.onPostExecute(s);
    }

    public String writeJSON(String date, String time, String lat, String lng) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"moveData\":{");
        sb.append("\"date\":\""+date+"\",");
        sb.append("\"time\":\""+time+"\",");
        sb.append("\"lat\":\""+lat+"\",");
        sb.append("\"lng\":\""+lng+"\"");
        sb.append("}");
        sb.append("}");

        return sb.toString();
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

        //거리; 구하기.....
    }
}
