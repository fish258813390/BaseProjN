package com.neil.fish.ui.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neil.fish.R;

/**
 * @author neil
 * @date 2018/1/9
 */
public class ClockActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_clock);
    }
}
