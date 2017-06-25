package edu.bluejack16_2.jrpost.adapters;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.Notification;

/**
 * Created by RE on 6/23/2017.
 */

public class NotifViewAdapter extends BaseAdapter {
    ArrayList<Notification>notifications;
    Context context;
    public NotifViewAdapter(Context context) {
        this.context= context;
        notifications= new ArrayList<Notification>();
    }

    public void add(Notification notif)
    {
        notifications.add(notif);
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int i) {
        return notifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Notification currentNotif = notifications.get(i);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.notif_row, parent, false);
        }
        TextView lblNotif = (TextView) convertView.findViewById(R.id.lblNotif);
        TextView lblDate = (TextView) convertView.findViewById(R.id.lblDate);
        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblFrom);

        lblNotif.setText(notifications.get(i).getContent());
        lblFrom.setText(notifications.get(i).getFrom());
        lblDate.setText(notifications.get(i).getDate().toString());

        return convertView;
    }
}
