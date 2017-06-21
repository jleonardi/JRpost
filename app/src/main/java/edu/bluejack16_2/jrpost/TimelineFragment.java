package edu.bluejack16_2.jrpost;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Story;


public class TimelineFragment extends Fragment {
    StoryViewAdapter storyViewAdapter;
    public ProgressDialog progressDialog;
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
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().setTitle("New Story");
                    NewStoryFragment newStoryFragment= new NewStoryFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,newStoryFragment);
                    fragmentTransaction.commit();
                }
            });
            ListView storyListView = (ListView) view.findViewById(R.id.storyListView);
            storyViewAdapter = new StoryViewAdapter(getContext());
            //loading
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //StoryController.getInstance().getAllStory(storyViewAdapter,this);
            StoryController.getInstance().getStoryOnFollowedUser(storyViewAdapter,this);

            storyListView.setAdapter(storyViewAdapter);

            storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getContext(), ((Story)storyViewAdapter.getItem(position)).getStoryId(), Toast.LENGTH_SHORT).show();
                    try {
                        Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject((Story) storyViewAdapter.getItem(position));
                        byte[] buf = baos.toByteArray();
                        intent.putExtra("story", buf);

                        startActivity(intent);
                    }catch (Exception e)
                    {
                        Toast.makeText(view.getContext(), "error di Timeline : "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.d("Wah", "Kofang");

        }

        return view;
    }
}
