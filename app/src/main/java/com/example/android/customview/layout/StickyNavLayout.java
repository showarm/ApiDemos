package com.example.android.customview.layout;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by chenshao on 16/12/15.
 *
 * http://www.tuicool.com/articles/JnmIrqU
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

}
