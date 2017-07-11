package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.UpdateStoryActivity;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

/**
 * Created by RE on 7/9/2017.
 */

public class CurrentProfileAdapter extends BaseAdapter {
    ArrayList<Story>stories;
    Context context;

    public CurrentProfileAdapter() {

    }

    public CurrentProfileAdapter(Context context) {
        this.context = context;
        stories=new ArrayList<Story>();
    }

    public void add(Story story)
    {
        stories.add(story);
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int i) {
        return stories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Story current = stories.get(position);
        final View view = convertView;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.story_current_profile_row, parent, false);
        }

        ImageView imgStory = (ImageView) convertView.findViewById(R.id.imgStory);
        Glide.with(context).using(new FirebaseImageLoader()).load(current.getImageRef()).into(imgStory);

        TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
        lblTitle.setText(stories.get(position).getStoryTitle());
        lblTitle.setTextColor(Color.BLACK);

        return convertView;
    }
}
