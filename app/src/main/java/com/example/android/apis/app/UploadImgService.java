package com.example.android.apis.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chenshao on 16/12/15.
 *
 * http://www.tuicool.com/articles/B7FNB3i
 * Service里不能直接进行耗时操作，
 * IntentService最起码有两个好处，一方面不需要自己去new Thread了；另一方面不需要考虑在什么时候关闭该Service了。
 * 背后是HandlerThread
 */
public class UploadImgService extends IntentService {

    private static final String ACTION_UPLOAD_IMG = "com.zhy.blogcodes.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH = "com.zhy.blogcodes.intentservice.extra.IMG_PATH";

    //对外
    public static void startUploadImg(Context context, String path) {
        Intent intent = new Intent(context, UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }

    public UploadImgService() {
        super("UploadImgService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)) {
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(path);
            }
        }
    }

    /**
     * 这个方法在一个HandlerThread里执行的
     */
    private void handleUploadImg(String path) {
        try {
            //直接进行耗时操作，没有开启子线程，因为IntentService帮你干了
            Thread.sleep(3000);
            //上次结果回传给调用者
//            Intent intent = new Intent(IntentServiceActivity.UPLOAD_RESULT);
//            intent.putExtra(EXTRA_IMG_PATH, path);
//            sendBroadcast(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
