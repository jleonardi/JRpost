package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public void clearStory() {
        stories.clear();
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
        TextView tvLikers = (TextView) convertView.findViewById(R.id.storyRowLikersCount);

        tvStoryTitle.setText(current.getStoryTitle());
        tvUsername.setText(current.getUser().getUsername());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd MMMM yyyy");
        tvCreatedAt.setText(sdf.format(new Date(current.getCreatedAt())));
        tvGenre.setText(current.getStoryGenre());
        tvLikers.setText(current.getLikersCount().toString() + " likes");
        if(current.getStoryContent().length() > 50) {
            tvContent.setText(current.getStoryContent().substring(0, 50));
        } else {
            tvContent.setText(current.getStoryContent());
        }

        return convertView;
    }
}
