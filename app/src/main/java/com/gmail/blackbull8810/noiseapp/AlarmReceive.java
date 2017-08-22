package com.gmail.blackbull8810.noiseapp;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceive extends BroadcastReceiver{

    GauseFragment gause = new GauseFragment();

    @Override
    public void onReceive(Context context,Intent intent){

        NotificationManager notifier = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent2 = new Intent(context,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,intent2,0);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            Notification.Builder mBuilder = new Notification.Builder(context);
            mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
            mBuilder.setTicker("소음경고");
            mBuilder.setWhen(System.currentTimeMillis());
            mBuilder.setNumber(1);
            mBuilder.setContentTitle("소음 경고");
            mBuilder.setContentText("소음이 발생하였습니다.");
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setContentIntent(pIntent);
            mBuilder.setAutoCancel(true);

            notifier.notify(222, mBuilder.build());

        }
    }
}