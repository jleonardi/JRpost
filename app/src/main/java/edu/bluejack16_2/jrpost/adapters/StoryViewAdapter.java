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

/**
 * Created by User on 6/21/2017.
 */

public class StoryViewAdapter extends BaseAdapter{

    ArrayList<Story> stories;
    Context context;

    public StoryViewAdapter(ArrayList<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    public void addStory(Story story) {
        stories.add(story);
    }

    public StoryViewAdapter(Context context) {
        this.stories = new ArrayList<>();
        this.context = context;
    }

    public StoryViewAdapter() {

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

        TextView  tvStoryTitle = (TextView) convertView.findViewById(R.id.storyRowTitleTv);
        TextView  tvUsername = (TextView) convertView.findViewById(R.id.storyRowUsernameTV);
        TextView  tvCreatedAt = (TextView) convertView.findViewById(R.id.storyRowCreatedAtTV);
        TextView  tvGenre = (TextView) convertView.findViewById(R.id.storyRowGenreTV);
        TextView  tvContent = (TextView) convertView.findViewById(R.id.storyRowContentTV);

        tvStoryTitle.setText(current.getStoryTitle());
        tvUsername.setText(current.getUser().getUsername());
        //tvCreatedAt.setText(current.getCreatedAt().toString());
        tvGenre.setText(current.getStoryGenre());
        tvContent.setText(current.getStoryContent());



        return convertView;
    }
}
