package com.gayuu.project;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;





public class AlarmReceiver extends BroadcastReceiver{

    String  title,description;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent){

        int requestId=(int) System.currentTimeMillis();
        title=intent.getStringExtra("Title");
        description=intent.getStringExtra("Description");

        Intent nextActivity=new Intent(context,Notification.class);
        nextActivity.putExtra("Title",title);
        nextActivity.putExtra("Description",description);

        nextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(nextActivity);
       PendingIntent pendingintent=PendingIntent.getActivity(context,requestId,nextActivity,PendingIntent.FLAG_UPDATE_CURRENT);
       NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"Task")
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingintent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify((int)System.currentTimeMillis(), builder.build());
    }
}
