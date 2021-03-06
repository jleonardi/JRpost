package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import edu.bluejack16_2.jrpost.R;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;
import edu.bluejack16_2.jrpost.utilities.StoryDateComparator;
import edu.bluejack16_2.jrpost.utilities.StoryLikersComparator;

/**
 * Created by User on 6/21/2017.
 */

@SuppressWarnings("Since15")
public class StoryViewAdapter extends BaseAdapter {

    ArrayList<Story> stories;
    Context context;

    public StoryViewAdapter(ArrayList<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    public void addStory(Story story) {
        stories.add(story);
    }

    public void pushMidStory(Story story)
    {
        if(stories.size()==0)
        {
            stories.add(story);
        }
        else if(story.getLikersCount()<=stories.get(stories.size()-1).getLikersCount())
        {
            stories.add(story);
        }
        else
        {
            for(int i=0;i<stories.size();i++)
            {
                if(story.getLikersCount()>=stories.get(i).getLikersCount())
                {
                    stories.add(i,story);
                    break;
                }
            }
        }
    }

    public void sortByLike()
    {
        Collections.sort(stories, new StoryLikersComparator());
    }

    public void sortByDate() {
        Collections.sort(stories, new StoryDateComparator());
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
        ImageView imgView = (ImageView) convertView.findViewById(R.id.storyRowImageView);
        Glide.with(context).using(new FirebaseImageLoader()).load(current.getImageRef()).into(imgView);

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
