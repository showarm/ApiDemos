package com.example.android.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.example.android.apis.R;

/**
 * http://www.tuicool.com/articles/UVnYriU
 * <p>
 * http://www.tuicool.com/articles/fuqE3e
 */
public class YoungAnimActivity extends AppCompatActivity {

    FrameLayout anim_view;
    int x, y;
    int r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_younganim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        anim_view = (FrameLayout) findViewById(R.id.anim_view);
        anim_view.setAlpha(0);

//        onCreate中获取view宽高为0

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAnima1(view);

            }
        });
    }
    void playAnima1(View view) {

        ObjectAnimator fabScale = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f, 1.0f);
        fabScale.setDuration(1000);
        fabScale.setInterpolator(new AccelerateInterpolator());
        fabScale.setRepeatMode(ValueAnimator.RESTART);
        fabScale.setRepeatCount(1);

        ObjectAnimator fabScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.5f, 1.0f);
        fabScaleY.setDuration(1000);
        fabScaleY.setInterpolator(new AccelerateInterpolator());
        fabScaleY.setRepeatMode(ValueAnimator.RESTART);
        fabScaleY.setRepeatCount(1);

        AnimatorSet a = new AnimatorSet();
        a.playTogether(fabScale, fabScaleY);
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playAnima2();

            }
        });
        a.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void playAnima2() {
        anim_view.setAlpha(1);

//        anim_view.setVisibility(View.VISIBLE);

//                x = anim_view.getWidth() / 2;
//                y = anim_view.getHeight() / 2;

        x = anim_view.getWidth() / 2;
        y = anim_view.getHeight();

//                r = (int) Math.sqrt(anim_view.getWidth()*anim_view.getWidth() +
//                        anim_view.getHeight()*anim_view.getHeight() );
        r = anim_view.getHeight() + anim_view.getWidth();


        //相对于anim_view
        Animator anima = ViewAnimationUtils.createCircularReveal(anim_view,
                x, y, 0, r);
        anima.setDuration(3000);
        anima.setInterpolator(new LinearInterpolator());
        anima.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                anim_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        anim_view.setVisibility(View.GONE);
                        anim_view.setAlpha(0);

                    }
                }, 200);
            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(anim_view, "alpha", 0f, 1f);
        alpha.setDuration(3000);
//        alpha.setupStartValues();

        AnimatorSet a = new AnimatorSet();
        a.playTogether(anima, alpha);
        a.start();

    }
}
