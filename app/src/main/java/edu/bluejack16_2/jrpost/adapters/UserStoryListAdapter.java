package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

/**
 * Created by User on 6/23/2017.
 */

public class UserStoryListAdapter extends BaseAdapter {

    ArrayList<Story> stories;
    User storyOwner;
    Context context;

    public UserStoryListAdapter(User storyOwner, Context context) {
        this.storyOwner = storyOwner;
        this.context = context;
        stories = new ArrayList<>();
    }

    public void addStory(Story story){
        stories.add(story);
    }

    public void clearStory() {
        stories.clear();
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Story current = stories.get(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.story_row, parent, false);
        }
        current.setUser(storyOwner);

        TextView tvStoryTitle = (TextView) convertView.findViewById(R.id.storyRowTitleTv);
        TextView  tvUsername = (TextView) convertView.findViewById(R.id.storyRowUsernameTV);
        TextView  tvCreatedAt = (TextView) convertView.findViewById(R.id.storyRowCreatedAtTV);
        TextView  tvGenre = (TextView) convertView.findViewById(R.id.storyRowGenreTV);
        TextView  tvContent = (TextView) convertView.findViewById(R.id.storyRowContentTV);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.storyRowImageView);
        Glide.with(context).using(new FirebaseImageLoader()).load(current.getImageRef()).into(imgView);
        tvStoryTitle.setTextColor(Color.BLACK);
        tvUsername.setTextColor(Color.BLACK);
        tvGenre.setTextColor(Color.BLACK);
        tvContent.setTextColor(Color.BLACK);
        tvCreatedAt.setTextColor(Color.BLACK);

        tvStoryTitle.setText(current.getStoryTitle());
        tvUsername.setText(storyOwner.getUsername());
        //tvCreatedAt.setText(current.getCreatedAt().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd MMMM yyyy");
        tvCreatedAt.setText(sdf.format(new Date(current.getCreatedAt())));
        tvGenre.setText(current.getStoryGenre());
        tvContent.setText(current.getStoryContent());

        return convertView;
    }
}
