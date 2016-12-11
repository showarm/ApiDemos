package com.example.android.supportv4.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * http://www.tuicool.com/articles/JnmIrqU
 * Thx HongYang
 *
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    public StickyNavLayout(Context context) {
        super(context);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }
}
