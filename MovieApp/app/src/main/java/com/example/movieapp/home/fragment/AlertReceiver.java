package com.example.movieapp.home.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.movieapp.R;
import com.example.movieapp.entity.WatchList;

import java.util.List;

public class AlertReceiver extends BroadcastReceiver {
    private final static String default_notification_channel_id = "default" ;
    @Override
    public void onReceive(Context context, Intent intent) {

        String sizeofTheArray = intent.getStringExtra("ExpiredDataWatchList");
        if(sizeofTheArray!=null){
            Toast.makeText(context,"Size is: "+sizeofTheArray,Toast.LENGTH_LONG).show();
        }
        //getNotification(context);
    }
    public void getNotification(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10001")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.build();
    }
}
