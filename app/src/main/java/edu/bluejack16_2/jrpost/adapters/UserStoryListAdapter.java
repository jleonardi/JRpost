package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;

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

        tvStoryTitle.setText(current.getStoryTitle());
        tvUsername.setText(storyOwner.getUsername());
        //tvCreatedAt.setText(current.getCreatedAt().toString());
        tvGenre.setText(current.getStoryGenre());
        tvContent.setText(current.getStoryContent());

        return convertView;
    }
}
