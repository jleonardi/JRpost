package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.User;

/**
 * Created by User on 6/23/2017.
 */

public class UserListAdapter extends BaseAdapter{

    ArrayList<User> users;
    Context context;

    public UserListAdapter(Context context) {
        users = new ArrayList<>();
        this.context = context;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void clearUser() {
        users.clear();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User current = users.get(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.user_list_row, parent, false);
        }

        TextView usernameTV = (TextView) convertView.findViewById(R.id.usernameTV);
        usernameTV.setText(current.getUsername());



        return convertView;
    }
}
