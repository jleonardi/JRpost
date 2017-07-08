package edu.bluejack16_2.jrpost;

import android.nfc.Tag;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by User on 7/8/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "Service FCM";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, remoteMessage.getNotification().getBody());
    }
}
