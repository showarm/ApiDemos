package com.example.android.customview.views;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.ipeanut.youngsamples.SampleAdapter;
import info.ipeanut.youngsamples.SampleBean;
import info.ipeanut.youngsamples.third.view.mtextview.MTextViewActivity;
import info.ipeanut.youngsamples.third.view.percentProgressView.PercentProgressActivity;
import info.ipeanut.youngsamples.third.view.rollingTextView.RollingActivity;

/**
 * Created by chenshaosina on 15/12/8.
 */
public class ThirdViewActivity extends ListActivity {

    private static String[] names = {
            "解决TextView乱换行并支持图文混排",
            "百分比环形进度条",
            "弹幕"

    };
    private static Class[] cls = {
            MTextViewActivity.class,
            PercentProgressActivity.class,
            RollingActivity.class

    };
    private List<SampleBean> samples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepateSamples();
        setListAdapter(new SampleAdapter(this,samples));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(((SampleBean)(getListAdapter().getItem(position))).intent);
    }

    private void prepateSamples() {
        samples = new ArrayList<>();
        for (int i=0;i<names.length;i++){
            Intent intent = new Intent();
            intent.setClass(this,cls[i]);
            SampleBean sb = new SampleBean(names[i],intent);
            samples.add(sb);
        }
    }
}
