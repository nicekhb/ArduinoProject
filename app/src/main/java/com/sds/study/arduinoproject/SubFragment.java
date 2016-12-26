package com.sds.study.arduinoproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

/**
 * Created by student on 2016-12-12.
 */

public class SubFragment extends Fragment {
    FitnessAsync async;
    LinearLayout titleLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment, container, false);
        titleLayout = (LinearLayout) view.findViewById(R.id.titleLayout);
        RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                    view.setChecked(view.getId() == i);
                }
            }
        };
        ((RadioGroup) view.findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);


        Log.d("JSON", "async start");

        async = new FitnessAsync((MainActivity) getContext());
        async.execute("");
        return view;
    }
}
