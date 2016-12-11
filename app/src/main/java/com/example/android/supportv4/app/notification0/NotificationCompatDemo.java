package com.example.android.supportv4.app.notification0;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.android.apis.ApiDemos;
import com.example.android.apis.R;

import java.util.Random;

/**
 * Created by chenshao on 16/12/8.
 */
public class NotificationCompatDemo {

    public void showNotification(Context context){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon48x48_1)
                .setContentTitle(context.getString(R.string.go))
                .setContentText("test notify")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        Intent homeIntent = new Intent(context, ApiDemos.class);
        homeIntent.putExtra("extra_notification_id", "notification_id");
//        homeIntent.putExtra("extra_notification", data.toJSONString());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ApiDemos.class);
        stackBuilder.addNextIntent(homeIntent);

        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(), builder.build());

    }

}
