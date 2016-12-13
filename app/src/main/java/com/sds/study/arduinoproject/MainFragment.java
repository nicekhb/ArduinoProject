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
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.main_fragment, container, false);
        progressBar = (ProgressBar)result.findViewById(R.id.progressBar);
        progressBar.setProgress(82);
        return result;
    }
}
