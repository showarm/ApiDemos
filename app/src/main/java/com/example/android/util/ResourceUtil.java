package com.example.android.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;

/**
 * Created by chenshao on 16/12/12.
 */
public class ResourceUtil {

    public static Uri getResourceUri(int resId, String packageName) {
        return Uri.parse("android.resource://"+packageName+"/"+resId);
    }

    /**
     *
     * @param context
     * @param name ic_launcher
     * @param type drawable
     * @param packageName "com.android.testproject" / mContext.getPackageName()
     * @return
     */
    public static int getResourceId(Context context, String name, String type, String packageName){
        Resources themeResources=null;
        PackageManager pm=context.getPackageManager();
        try {
            themeResources=pm.getResourcesForApplication(packageName);
            return themeResources.getIdentifier(name, type, packageName);
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return 0;
    }

}
