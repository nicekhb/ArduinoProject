package com.sds.study.arduinoproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by student on 2016-12-12.
 */

public class ExerciseFragment extends Fragment {
    MainActivity mainActivity;
    LinearLayout exerciseLayout;
    Fitness fitness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_fragment, container, false);
        exerciseLayout = (LinearLayout) view.findViewById(R.id.exerciseLayout);
        Log.d("JSON","exerciseLayout: "+exerciseLayout);
        mainActivity = (MainActivity) getContext();

        for(int i=0; i<mainActivity.list.size(); i++) {
            addExerciseTextView(mainActivity.list.get(i).getExercise());
            addExerciseTextView("횟수:"+mainActivity.list.get(i).getCount());
        }

        return view;
    }

    public void addExerciseTextView(String exercise){
        TextView view2 = new TextView(getContext());
        view2.setText(exercise);
        view2.setTextColor(Color.BLUE);
        view2.setTextSize(12);

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.topMargin = 65;
        view2.setLayoutParams(lp);

        //부모 뷰에 추가
        exerciseLayout.addView(view2);
    }
}
