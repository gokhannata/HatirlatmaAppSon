package com.example.hatirlatmaappson;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.example.hatirlatmaappson.fragments.NotList;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;

public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.ic_lock)
                .setContentTitle("Göreviniz")
                .setContentText("Görevinizin vakti yaklaşıyor")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
        /*
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();


        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent listIntent=new Intent(context, NotList.class);
        listIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,listIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Görev Saati")
                .setContentText("")
                .setSound(soundUri)
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());

         */
    }
}
