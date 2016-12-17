package com.sds.study.arduinoproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by student on 2016-12-12.
 */

public class MainFragment extends Fragment {
    CircularProgressBar progressBar;
    CircularProgressBar.ProgressAnimationListener progressAnimationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.main_fragment, container, false);
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
}
