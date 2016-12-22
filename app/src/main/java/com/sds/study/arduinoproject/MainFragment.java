package com.sds.study.arduinoproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

/**
 * Created by student on 2016-12-12.
 */

public class MainFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    CircularProgressBar progressBar;
    CircularProgressBar.ProgressAnimationListener progressAnimationListener;
    boolean isRunMode = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.main_fragment, container, false);
        SwitchCompat switchCompat = (SwitchCompat) result.findViewById(R.id
                .switch_compat);
        switchCompat.setSwitchPadding(40);
        switchCompat.setOnCheckedChangeListener(this);
        progressBar = (CircularProgressBar)result.findViewById(R.id.progressBar);
        //progressBar.setProgress(77);
        progressAnimationListener = new CircularProgressBar.ProgressAnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                progressBar.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                progressBar.setSubTitle("화이팅!!");
            }
        };
        progressBar.animateProgressTo(0, 77, progressAnimationListener);
        return result;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_compat:
                isRunMode = isChecked;
                Log.d("JSON", "isRunMode: "+isRunMode);
                break;
        }

    }
}
