package com.example.android.graphics.loadingRenderer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;


public class LoadingDrawable extends Drawable implements Animatable {
    private LoadingRenderer mLoadingRender;

    private final Callback mCallback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable d) {
            invalidateSelf();
        }

      //LoadingDrawable本身没有注册callback，下面两个方法调用不到
        /**
         * drawable可以通过该方法来安排动画的下一帧。可以仅仅简单的调用postAtTime(Runnable, Object, long)
         * 来实现该方法。参数分别与方法的参数对应
         * @param who The drawable being scheduled.
         * @param what The action to execute.
         * @param when The time (in milliseconds) to run
         */
        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            scheduleSelf(what, when);
        }
        /**
         *可以用于取消先前通过scheduleDrawable(Drawable who, Runnable what, long when)调度的某一帧。
         *可以通过调用removeCallbacks(Runnable,Object)来实现
         * @param who The drawable being unscheduled.
         * @param what The action being unscheduled.
         */
        @Override
        public void unscheduleDrawable(Drawable d, Runnable what) {
            unscheduleSelf(what);
        }
    };

    public LoadingDrawable(LoadingRenderer loadingRender) {
        this.mLoadingRender = loadingRender;
        this.mLoadingRender.setCallback(mCallback);
    }

    @Override
    public void draw(Canvas canvas) {
        mLoadingRender.draw(canvas, getBounds());
    }

    @Override
    public void setAlpha(int alpha) {
        mLoadingRender.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mLoadingRender.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        mLoadingRender.start();
    }

    @Override
    public void stop() {
        mLoadingRender.stop();
    }

    @Override
    public boolean isRunning() {
        return mLoadingRender.isRunning();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (mLoadingRender.getHeight() + 1);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (mLoadingRender.getWidth() + 1);
    }
}
