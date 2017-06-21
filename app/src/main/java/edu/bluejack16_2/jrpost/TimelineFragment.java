package edu.bluejack16_2.jrpost;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Story;


public class TimelineFragment extends Fragment {
    StoryViewAdapter storyViewAdapter;
    public TimelineFragment() {
        // Required empty public constructor
        //StoryController.getInstance().getAllStory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        try {


            ListView storyListView = (ListView) view.findViewById(R.id.storyListView);
            storyViewAdapter = new StoryViewAdapter(getContext());
            StoryController.getInstance().getAllStory(storyViewAdapter);

            storyListView.setAdapter(storyViewAdapter);

            storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(), ((Story)storyViewAdapter.getItem(position)).getStoryId(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.d("Wah", "Kofang");

        }

        return view;
    }
}
