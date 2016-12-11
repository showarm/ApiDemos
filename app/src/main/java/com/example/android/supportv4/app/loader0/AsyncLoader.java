/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.supportv4.app.loader0;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.util.Log1;


public class AsyncLoader<D> extends AsyncTaskLoader<D> {
    private final String TAG = AsyncLoader.class.getSimpleName();

    private D mData;

    private Exception mError;

    private LoaderCallback<D> mLoaderCallback;

    public AsyncLoader(Context context, LoaderCallback<D> loaderCallback) {
        super(context);
        this.mLoaderCallback = loaderCallback;
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override
    public D loadInBackground() {
        Log1.e(TAG, ">>>loadInBackground");
        try {
            mError = null;
            return mLoaderCallback.onLoadInBackground();
        } catch (Exception e) {
            mError = e;
        }
        return null;
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(D data) {
        Log1.e(TAG, ">>>deliverResult");
        if (isReset()) {
            // An async query came in while the loader is stopped.  We don't need the result.
            if (mData != null) {
                mData = null;
            }
        }

        mData = data;

        if (isStarted()) {
            if (hasError()) {
                mLoaderCallback.onLoadError(mError);
            } else {
                // If the Loader is currently started, we can immediately deliver its results.
                mLoaderCallback.onLoadComplete(mData);
            }
        } else {
            mLoaderCallback.onLoadError(mError);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        Log1.e(TAG, ">>>onStartLoading");
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
            mLoaderCallback.onLoadStart();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        Log1.e(TAG, ">>>onStopLoading");
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(D data) {
        Log1.e(TAG, ">>>onCanceled");
        super.onCanceled(data);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        Log1.e(TAG, ">>>onReset");
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        mData  = null;
        mError = null;
    }

    public boolean hasError() {
        return mError != null;
    }

    public interface LoaderCallback<D> {

        void onLoadStart();

        D onLoadInBackground() throws Exception;

        void onLoadComplete(D data);

        void onLoadError(Exception e);
    }
}
