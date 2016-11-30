package com.jessi.pms.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.jessi.pms.Home;
import com.jessi.pms.PatientMonitor;
import com.jessi.pms.R;

/**
 * Created by Jessi on 11/30/2016.
 */

public class MedicineAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("timelog", "onReceive");

        Bundle extras = intent.getExtras();
        String title = extras.getString("title");
        String content = extras.getString("content");
        Log.v("timelog", "onReceive > title : " + title);
        Log.v("timelog", "onReceive > content : " + content);
        int ctr = extras.getInt("rc");

        Log.v("timelog", "onReceive > rc : " + String.valueOf(ctr));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentIntent(PendingIntent.getActivity(context, ctr, new Intent(context, PatientMonitor.class), 0))
                .setTicker("PMS: New operation")
                .build();

        notificationManager.notify(ctr, notification);
    }
}
