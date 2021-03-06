package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.Comment;

/**
 * Created by User on 6/22/2017.
 */

public class CommentListAdapter extends BaseAdapter {
    ArrayList<Comment> comments;
    Context context;

    public CommentListAdapter(Context context) {
        comments = new ArrayList<>();
        this.context = context;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void clearComment()
    {
        comments=new ArrayList<Comment>();
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment current = comments.get(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.comment_list_row, parent, false);
        }

        TextView usernameTV = (TextView) convertView.findViewById(R.id.commentUsernameTV);
        TextView contentTV = (TextView) convertView.findViewById(R.id.commentContentTV);
        TextView dateTV = (TextView) convertView.findViewById(R.id.lblDate);

        usernameTV.setText(current.getUser().getUsername());
        contentTV.setText(current.getCommentContent());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd MMMM yyyy");
        DateFormat df = DateFormat.getDateInstance();
        dateTV.setText(sdf.format(new Date(current.getCreatedAt())));
        Log.v("TestTanggal", current.getCreatedAt().toString());
        //dateTV.setText(current.getCreatedAt().toString());

        return convertView;
    }
}
