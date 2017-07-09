package edu.bluejack16_2.jrpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.bluejack16_2.jrpost.controllers.FollowController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStoryFragment extends Fragment {

    Button followBtn;
    Story currentStory;
    TextView titleTV;
    TextView usernameTV;
    TextView genreTV;
    TextView contentTV;

    public DetailStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_story, container, false);

        titleTV = (TextView) view.findViewById(R.id.detailStoryTitleTV);
        usernameTV = (TextView) view.findViewById(R.id.detailUsernameTV);
        genreTV = (TextView) view.findViewById(R.id.detailStoryGenre);
        contentTV = (TextView) view.findViewById(R.id.detailContentTV);
        followBtn = (Button) view.findViewById(R.id.detailFollowUserBtn);

        currentStory = ((DetailStoryActivity)getActivity()).currentStory;
        titleTV.setText(currentStory.getStoryTitle());
        usernameTV.setText("By: " + currentStory.getUser().getUsername());
        genreTV.setText(currentStory.getStoryGenre());
        contentTV.setText(currentStory.getStoryContent());

        if(Session.currentUser.getUserId().equals(currentStory.getUser().getUserId())) {
            followBtn.setText("Update Story");
            followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), UpdateStoryActivity.class);
                    intent.putExtra("storyId", currentStory.getStoryId());
                    startActivity(intent);
                }
            });
        } else {
            FollowController.getInstance().checkFollowUser(this, currentStory.getCurrentUser());
        }

//        followBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }

    public void isFollowing(Boolean following) {
        final DetailStoryFragment dsf = this;
        if(following) {
            followBtn.setText("Unfollow User");
            followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowController.getInstance().unFollowUser(currentStory.getCurrentUser());
                    followBtn.setOnClickListener(null);
                    isFollowing(false);
                }
            });
        } else {
            followBtn.setText("Follow User");
            followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowController.getInstance().followUser(currentStory.getCurrentUser());
                    followBtn.setOnClickListener(null);
                    isFollowing(true);
                }
            });
        }
    }

}
