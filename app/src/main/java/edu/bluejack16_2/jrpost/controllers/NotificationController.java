package edu.bluejack16_2.jrpost.controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.jrpost.adapters.NotifViewAdapter;
import edu.bluejack16_2.jrpost.models.Notification;

/**
 * Created by RE on 6/24/2017.
 */

public class NotificationController {
    private static NotificationController instance;
    private DatabaseReference mDatabase;

    public static NotificationController getInstance() {
        if(instance==null) {
            instance = new NotificationController();
        }
        return instance;
    }

    public NotificationController() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("notification");
    }

    public void addNotif(String content,String from,String userId)
    {
        String notifId=mDatabase.push().getKey();
        Notification notif = new Notification(content, from, userId, notifId);
        mDatabase.child(notifId).setValue(notif);
    }

    public void getNotif(String userId,final NotifViewAdapter adapter)
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("notification").orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Notification notif = ds.getValue(Notification.class);
                    adapter.add(notif);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
