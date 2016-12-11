package com.example.android.supportv4.app.loader0;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.android.supportv4.app.LoaderCustomSupport.AppEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshao on 16/12/7.
 */
public class DemoLoaderFragment extends LoaderFragment<List<AppEntry>> {

    @Override
    public void onLoadStart() {

    }

    @Override
    public List<AppEntry> onLoadInBackground() throws Exception {
        final PackageManager mPm = getContext().getPackageManager();

        List<ApplicationInfo> apps = mPm.getInstalledApplications(
                PackageManager.GET_UNINSTALLED_PACKAGES |
                        PackageManager.GET_DISABLED_COMPONENTS);
        if (apps == null) {
            apps = new ArrayList<ApplicationInfo>();
        }

        final Context context = getContext();

        // Create corresponding array of entries and load their labels.
        List<AppEntry> entries = new ArrayList<>(apps.size());
        for (int i=0; i<apps.size(); i++) {
//            AppEntry entry = new AppEntry(this, apps.get(i));
//            entry.loadLabel(context);
//            entries.add(entry);
        }

//        Collections.sort(entries, ALPHA_COMPARATOR);
        return entries;
    }

    @Override
    public void onLoadComplete(List<AppEntry> data) {

    }

    @Override
    public void onLoadError(Exception e) {

    }
}
