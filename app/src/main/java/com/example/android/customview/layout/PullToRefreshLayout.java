package com.example.android.customview.layout;

import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by chenshao on 16/12/15.
 *
 * http://www.tuicool.com/articles/3Az6z26
 * CoordinatorLayout就是NestedScrollingParent，NestedScrollView既是NestedScrollingChild有事parent
 *
 */
public class PullToRefreshLayout implements NestedScrollingParent {




    /****************** NestedScrollingParent *********************/
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //仅考虑垂直方向
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

    }

    @Override
    public void onStopNestedScroll(View target) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }
    /****************** NestedScrollingParent end *********************/

}
