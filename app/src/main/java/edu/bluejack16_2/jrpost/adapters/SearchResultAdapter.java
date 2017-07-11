package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
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
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

/**
 * Created by User on 6/22/2017.
 */

public class SearchResultAdapter extends BaseAdapter{
    ArrayList<Story> resultStories;
    Context context;

    public SearchResultAdapter(Context context) {
        resultStories = new ArrayList<>();
        this.context = context;
    }

    public void addStory(Story story) {
        if(!resultStories.contains(story)){
            resultStories.add(story);
        }
    }

    public void clearAll(){
        resultStories.clear();
    }

    @Override
    public int getCount() {
        return resultStories.size();
    }

    @Override
    public Object getItem(int position) {
        return resultStories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Story current = resultStories.get(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.story_row, parent, false);
        }

        TextView tvStoryTitle = (TextView) convertView.findViewById(R.id.storyRowTitleTv);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.storyRowUsernameTV);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.storyRowCreatedAtTV);
        TextView tvGenre = (TextView) convertView.findViewById(R.id.storyRowGenreTV);
        TextView tvContent = (TextView) convertView.findViewById(R.id.storyRowContentTV);

        ImageView imgView = (ImageView) convertView.findViewById(R.id.storyRowImageView);
        Glide.with(context).using(new FirebaseImageLoader()).load(current.getImageRef()).into(imgView);

        tvStoryTitle.setText(current.getStoryTitle());
        tvUsername.setText(current.getUser().getUsername());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd MMMM yyyy");
        tvCreatedAt.setText(sdf.format(new Date(current.getCreatedAt())));
        tvGenre.setText(current.getStoryGenre());
        tvContent.setText(current.getStoryContent());

        return convertView;
    }
}
