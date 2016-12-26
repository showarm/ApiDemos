package com.example.android.customview.views.rollingTextView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by chenshaosina on 15/10/20.
 */
public class RollingActivity extends Activity {
    //两两弹幕之间的间隔时间
    public static final int DELAY_TIME = 800;
    private Random random = new Random();
    final String[] texts = {
            "写代码也要永远热泪盈眶1","写代码也要永远热泪盈眶4","写代码也要永远热泪盈眶7",
            "写代码也要永远热泪盈眶2","写代码也要永远热泪盈眶5","写代码也要永远热泪盈眶8",
            "写代码也要永远热泪盈眶3","写代码也要永远热泪盈眶6","写代码也要永远热泪盈眶9000",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置RollingTextView宽高全屏，这个应该放在RollingTextView里面
        final ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Handler handler = new Handler();
        Runnable createBarrageView = new Runnable() {
            @Override
            public void run() {
                //新建一条弹幕，并设置文字
                final RollingTextView rollingTextView = new RollingTextView(RollingActivity.this);
                rollingTextView.setText(texts[random.nextInt(texts.length)]);
                addContentView(rollingTextView, lp);
                //发送下一条消息
                handler.postDelayed(this, DELAY_TIME);
            }
        };
        handler.post(createBarrageView);

    }
}
