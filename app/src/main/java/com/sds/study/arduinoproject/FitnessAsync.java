package com.sds.study.arduinoproject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import static com.sds.study.arduinoproject.ScreenSlidePagerAdapter.SUB;

public class FitnessAsync extends AsyncTask<String, Void, String> {
    URL url;
    HttpURLConnection con;
    MainActivity mainActivity;
    SubFragment subFragment;

    public FitnessAsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.subFragment = (SubFragment)(((ScreenSlidePagerAdapter)mainActivity.mPagerAdapter).fragments[SUB]);
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
        try {
            JSONArray array = new JSONArray(s);

            for (int i = 0; i < array.length(); i++) {
                Fitness fitness = new Fitness();
                JSONObject obj = array.getJSONObject(i);

                fitness.setPartname(obj.getString("partname"));
                fitness.setTitle(obj.getString("title"));
                fitness.setExercise(obj.getString("exercise"));
                fitness.setCount(obj.getInt("count"));

                addTitleTextView(fitness.getTitle()+" ("+fitness.getPartname()+")");

                mainActivity.list.add(fitness);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addTitleTextView(String title) {
        //TextView 생성
        TextView view1 = new TextView(subFragment.getContext());
        view1.setText(title);
        view1.setTextColor(Color.BLACK);
        view1.setTextSize(20);


        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.topMargin = 65;
        view1.setLayoutParams(lp);

        //부모 뷰에 추가
        subFragment.titleLayout.addView(view1);
    }
}
