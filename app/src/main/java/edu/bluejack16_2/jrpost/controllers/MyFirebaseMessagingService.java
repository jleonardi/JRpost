package edu.bluejack16_2.jrpost.controllers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.bluejack16_2.jrpost.LoginActivity;
import edu.bluejack16_2.jrpost.MainActivity;
import edu.bluejack16_2.jrpost.R;

/**
 * Created by RE on 7/8/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("MyFirebaseMsgService","FROM: "+remoteMessage.getFrom());
        if(remoteMessage.getData().size()>0)
        {
            Log.d("MyFirebaseMsgService","Message data :"+remoteMessage.getData());
        }
        if(remoteMessage.getNotification()!=null)
        {
            Log.d("MyFirebaseMsgService","Message body : "+remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }
    private void sendNotification(String body)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                                                                                      .setContentTitle("JRPost")
                                                                                      .setContentText(body)
                                                                                      .setAutoCancel(true)
                                                                                      .setSound(notifSound)
                                                                                      .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifBuilder.build());

    }
}
