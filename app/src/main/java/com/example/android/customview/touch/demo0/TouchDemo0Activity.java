package com.example.android.customview.touch.demo0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.apis.R;

public class TouchDemo0Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchdemo0);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, DemoActivity_1.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button3) {
            Intent intent = new Intent(this, DemoActivity_2.class);
            startActivity(intent);
        }
    }
}
