package com.linwei.electronicdream.toasttracker.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.linwei.electronicdream.toasttracker.R;



public class NotificationUtil {
    Context context;
    String CHANNEL_ID="default";


    public NotificationUtil(Context context){
        this.context=context;
    }

    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Toast Detector", NotificationManager.IMPORTANCE_HIGH);
            String description="";
            channel.setDescription(description);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(Bitmap icon, String title, String content, int notificationID){
        createNotificationChannel();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.mipmap.notilogo)
                .setLargeIcon(icon)
                .setContentTitle("应用名称: "+title)
                .setContentText("Toast内容: "+content)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationID, builder.build());
    }


}
