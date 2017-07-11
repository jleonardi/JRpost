package edu.bluejack16_2.jrpost;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import edu.bluejack16_2.jrpost.controllers.FollowController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;


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
    ImageView imageView;

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
        imageView = (ImageView) view.findViewById(R.id.imageView4);

        currentStory = ((DetailStoryActivity)getActivity()).currentStory;
        titleTV.setText(currentStory.getStoryTitle());
        usernameTV.setText("By: " + currentStory.getUser().getUsername());
        genreTV.setText(currentStory.getStoryGenre());
        contentTV.setText(currentStory.getStoryContent());
        Glide.with(getContext()).using(new FirebaseImageLoader()).load(currentStory.getImageRef()).into(imageView);
        try {
            if (Session.currentUser.getUserId().equals(currentStory.getUser().getUserId())) {
                followBtn.setText("Update Story");
                followBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(getContext(), UpdateStoryActivity.class);
                            intent.putExtra("storyId", currentStory.getStoryId());
                            startActivity(intent);
                            getActivity().finish();
                        }catch(Exception e) {
                            Log.d("UpdateTag", e.getMessage());
                        }
                    }
                });
            } else {
                FollowController.getInstance().checkFollowUser(this, currentStory.getCurrentUser());
            }
        } catch(Exception e) {
            Log.d("UpdateTag", e.getMessage());
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
