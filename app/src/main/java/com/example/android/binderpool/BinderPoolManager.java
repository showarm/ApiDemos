package com.example.android.binderpool;

import java.util.concurrent.CountDownLatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 线程：创建／就绪／执行／阻塞／结束
 * 1 CountDownLatch 计数锁: 操作完成之前，让其它多个线程等待。
 *  await() //阻塞当前线程，直到计数为0，才继续执行其后面代码
 *
 *
 */
public class BinderPoolManager {
    private static final String TAG = "BinderPoolManager";

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPoolManager sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPoolManager(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPoolManager getInsance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new BinderPoolManager(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection,
                Context.BIND_AUTO_CREATE);
        try {
            //等Service连上后，才能返回new BinderPoolManager(context)，才可以给人调用queryBinder
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // ignored.
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG, "binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };


    /********************功能方法*******************************/

    /**
     * query binder by binderCode from binder pool
     *
     * @param binderCode
     *            the unique token of binder
     * @return binder who's token is binderCode<br>
     *         return null when not found or BinderPoolService died.
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

}
