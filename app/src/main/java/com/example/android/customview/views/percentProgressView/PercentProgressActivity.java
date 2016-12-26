package com.example.android.customview.views.percentProgressView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import info.ipeanut.youngsamples.R;


public class PercentProgressActivity extends Activity {

    public final static int MSG_UPDATE = 1;
    public final static int MSG_FINISHED = 2;

    private PercentProgressView mPercentProgressView;
    private int mDownloadProgress = 0;
    private Handler mHandler = new InnerHandler();
    private boolean downloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentprogress);
        mPercentProgressView = (PercentProgressView) findViewById(R.id.downloadView);
        mPercentProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPercentProgressView.getStatus() == PercentProgressView.STATUS_PEDDING
                        || mPercentProgressView.getStatus() == PercentProgressView.STATUS_PAUSED) {
                    downloading = true;
                    mPercentProgressView.setStatus(PercentProgressView.STATUS_DOWNLOADING);
                    //???????
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (downloading) {
                                if(mDownloadProgress == 100) {
                                    mHandler.sendEmptyMessage(MSG_FINISHED);
                                    return;
                                }
                                mDownloadProgress += 1;
                                mHandler.sendEmptyMessage(MSG_UPDATE);
                                try{
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                }

                            }
                        }
                    }).start();
                } else if(mPercentProgressView.getStatus() == PercentProgressView.STATUS_DOWNLOADING){
                    downloading = false;
                    mPercentProgressView.setStatus(PercentProgressView.STATUS_PAUSED);
                }
            }
        });
    }

    class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISHED:
                    mPercentProgressView.setStatus(PercentProgressView.STATUS_FINISHED);
                    break;
                case MSG_UPDATE:
                    mPercentProgressView.setProgress(mDownloadProgress);
                    break;
            }
            super.handleMessage(msg);
        }
    }


}
