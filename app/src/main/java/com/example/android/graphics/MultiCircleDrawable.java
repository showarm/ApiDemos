package com.example.android.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * http://www.tuicool.com/articles/eqYnaaA
 *
 * 带多个动画的Drawable,需要实现Drawable.Callback接口 。。。。？这个没有关系吧
 * Created by chenshao on 17/1/12.
 *
 * MultiCircleDrawable 这个没有意义
 */
public class MultiCircleDrawable extends Drawable implements Animatable {
    // 每个Drawable动画启动的间隔
    private static final int EACH_CIRCLE_SPACE = 200;
    // CircleDrawable数组
    private CircleDrawable[] mCircleDrawables;

    Callback callback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
// 需要重绘，子Drawable发生重绘会调用这个方法通知父Drawable，如果有设置Callback回调监听的话
            invalidateSelf();
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {

        }
    };

    //三个CircleDrawable
    public MultiCircleDrawable() {
        mCircleDrawables = new CircleDrawable[] {
                new CircleDrawable(),
                new CircleDrawable(),
                new CircleDrawable()
        };
        for (int i = 0; i < mCircleDrawables.length; i++) {
            // 设置动画启动延迟
            mCircleDrawables[i].setAnimatorDelay(EACH_CIRCLE_SPACE * i);
            // 设置回调监听，当CircleDrawable发生重绘时就会调用 invalidateDrawable(Drawable who) 方法
            mCircleDrawables[i].setCallback(callback);
        }
    }
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        for (CircleDrawable c : mCircleDrawables) {
            c.onBoundsChange(bounds);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        for (CircleDrawable c : mCircleDrawables) {
            int count =  canvas.save();
            c.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.RGBA_8888;
    }

    /*********** Animatable ******************/
    @Override
    public void start() {

        for (CircleDrawable c : mCircleDrawables) {
            c.start();
        }
    }

    @Override
    public void stop() {
        for (CircleDrawable c : mCircleDrawables) {
            c.stop();
        }
    }

    @Override
    public boolean isRunning() {
        for (CircleDrawable drawable : mCircleDrawables) {
            if (drawable.isRunning()) {
                return true;
            }
        }
        return false;
    }
    /*********** Animatable ******************/

}
