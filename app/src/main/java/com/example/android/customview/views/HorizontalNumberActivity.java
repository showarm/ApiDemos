package com.example.android.customview.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.apis.R;

/**
 * Created by chenshao on 16/12/11.
 */
public class HorizontalNumberActivity extends AppCompatActivity {

    private TextView mTv;
    private HorizontalNumberView mHnv;
    private SeekBar mSeekBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalnumber);

        mTv = (TextView) findViewById(R.id.tv);
        mHnv = (HorizontalNumberView) findViewById(R.id.hnv);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);

        //you must call this method
        mHnv.setSeekBar(mSeekBar);


        mHnv.setOnSeekBarChangeListener(new HorizontalNumberView.OnSeekBarChangeListener() {
            @Override public void onStateChangeBack(int progress, int currentIndex, String currentValue) {
                mTv.setText("progress:" + progress + " ,index:" + currentIndex + " ,value:"+ currentValue);
            }

            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
