package com.example.android.customview.views.ownview.basepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.example.android.apis.R;


/**
 * http://git.oschina.net/charliec/Hnews
 * 可以加上页面title  back
 */
public abstract class BasePage implements OnClickListener {
    protected Context ct;
    protected LayoutInflater inflater;
    protected View root;
    //子类的根view
    protected View contentView;
    protected FrameLayout content;
    protected FrameLayout failedView;
    protected FrameLayout loadingView;

    public BasePage(Context context) {
        ct = context;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.basepage,null);
        failedView = (FrameLayout) root.findViewById(R.id.loadfailed);
        loadingView = (FrameLayout) root.findViewById(R.id.loading);
        content = (FrameLayout) root.findViewById(R.id.content);
        content.removeAllViews();
        contentView = inflater.inflate(layoutRes(),null);
        content.addView(contentView);
        findViews();
        initData();

    }

    public View getView() {
        return root;
    }

    public void loadingViewDismiss() {
        if (loadingView != null)
            loadingView.setVisibility(View.GONE);
    }

    public void loadingViewshow() {
        if (loadingView != null)
            loadingView.setVisibility(View.VISIBLE);
    }

    public void failedViewDismiss() {
        if (failedView != null)
            failedView.setVisibility(View.GONE);
    }

    public void failedViewshow() {
        if (failedView != null)
            failedView.setVisibility(View.VISIBLE);
    }

    protected abstract int layoutRes();
    protected abstract void findViews();
    protected abstract void initData();

    protected <T extends View>T findViewThroughId(int id){
        return (T)contentView.findViewById(id);
    }

    @Override
    public void onClick(View v) {

    }
}
