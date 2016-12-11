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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.Random;

/**
 * https://github.com/lijy91/BlankApp/blob/master/library/src/main/java/org/blankapp/app/LoaderFragment.java
 */
public abstract class LoaderFragment<D> extends Fragment implements LoaderManager.LoaderCallbacks<D>, AsyncLoader.LoaderCallback<D> {
    private final String TAG = LoaderFragment.class.getSimpleName();

    private final int LOADER_ID = new Random().nextInt();

    private LoaderManager mLoaderManager;

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.destroyLoader();
    }

    protected void ensureLoaderManager() {
        if (mLoaderManager == null) {
            mLoaderManager = getLoaderManager();
        }
    }

    protected void initLoader() {
        Log.e(TAG, String.format(">>>initLoader(%d)", LOADER_ID));
        ensureLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);
    }

    public void restartLoader() {
        Log.e(TAG, String.format(">>>restartLoader(%d)", LOADER_ID));
        ensureLoaderManager();
        mLoaderManager.restartLoader(LOADER_ID, null, this);
    }

    public void destroyLoader() {
        Log.e(TAG, String.format(">>>destroyLoader(%d)", LOADER_ID));
        ensureLoaderManager();
        mLoaderManager.destroyLoader(LOADER_ID);
    }

    protected void startLoading() {
        ensureLoaderManager();
        AsyncLoader<D> asyncLoader = (AsyncLoader<D>) mLoaderManager.getLoader(LOADER_ID);
        asyncLoader.startLoading();
    }

    protected void stopLoading() {
        ensureLoaderManager();
        AsyncLoader<D> asyncLoader = (AsyncLoader<D>) mLoaderManager.getLoader(LOADER_ID);
        asyncLoader.stopLoading();
    }

    protected void forceLoad() {
        ensureLoaderManager();
        AsyncLoader<D> asyncLoader = (AsyncLoader<D>) mLoaderManager.getLoader(LOADER_ID);
        asyncLoader.forceLoad();
    }

    @Override
    public Loader<D> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, ">>>onCreateLoader");
        return new AsyncLoader<D>(getActivity(), this);
    }

    @Deprecated
    @Override
    public void onLoadFinished(Loader<D> loader, D data) {
        Log.d(TAG, ">>>onLoadFinished");
    }

    @Deprecated
    @Override
    public void onLoaderReset(Loader<D> loader) {
        Log.d(TAG, ">>>onLoaderReset");
    }

}
