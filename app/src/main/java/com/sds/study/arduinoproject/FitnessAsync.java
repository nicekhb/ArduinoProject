package com.sds.study.arduinoproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FitnessAsync extends AsyncTask<String, Void, String> {
    URL url;
    HttpURLConnection con;
    SubFragment subFragment;

    public FitnessAsync(SubFragment subFragment) {
        this.subFragment = subFragment;
    }

    /*운동버튼 5개의 대한 연결페이지*/

    //서버에서 정보 얻어오기
    @Override
    protected void onPreExecute() {
        try {
            url = new URL("http://192.168.0.11:9090/device/fitness");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader buffr = null;
        StringBuffer sb = new StringBuffer();

        try {
            InputStream input = con.getInputStream();
            buffr = new BufferedReader(new InputStreamReader(input, "utf-8"));
            //int code = con.getResponseCode(); //요청 일어남

            String data = null;
            while ((data = buffr.readLine()) != null) {
                sb.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("JSON", sb.toString());
        return sb.toString();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        executeList(s);
    }

    //
    public void executeList(String s) {
        ArrayList<Fitness> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);

            for (int i = 0; i < array.length(); i++) {
                Fitness fitness = new Fitness();
                JSONObject obj = array.getJSONObject(i);

                if ((obj.getString("partname")).equals("가슴")) {
                    subFragment.et_bast.setText(obj.getString("title"));
                } else if (obj.getString("partname").equals("허리")) {
                    subFragment.et_low.setText(obj.getString("title"));
                } else if ((obj.getString("partname")).equals("하체")) {
                    subFragment.et_leg.setText(obj.getString("title"));
                } else if ((obj.getString("partname")).equals("등")) {
                    subFragment.et_back.setText(obj.getString("title"));
                } else if ((obj.getString("partname")).equals("배")) {
                    subFragment.et_body.setText(obj.getString("title"));
                }

                fitness.setPartname(obj.getString("partname"));
                fitness.setTitle(obj.getString("title"));
                fitness.setExercise(obj.getString("exercise"));
                fitness.setCount(obj.getInt("count"));

                list.add(fitness);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
