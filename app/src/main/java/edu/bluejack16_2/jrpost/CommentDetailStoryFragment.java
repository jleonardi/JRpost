package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import edu.bluejack16_2.jrpost.adapters.CommentListAdapter;
import edu.bluejack16_2.jrpost.controllers.CommentController;
import edu.bluejack16_2.jrpost.controllers.NotificationController;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentDetailStoryFragment extends Fragment {

    Story currentStory;
    Boolean isLike;
    TextView likersCount;
    CommentDetailStoryFragment currentFragment;
    public CommentDetailStoryFragment() {
        // Required empty public constructor
    }

    public void setNewLikeCount(Integer numLikes) {
        likersCount.setText(numLikes + " likes");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_detail_story, container, false);
        currentStory = ((DetailStoryActivity)getActivity()).currentStory;

        final ListView commentListView = (ListView) view.findViewById(R.id.commentListView);
        final EditText addComentTxt = (EditText) view.findViewById(R.id.addCommentTxt);
        Button addCommentBtn = (Button) view.findViewById(R.id.addCommentBtn);
        final CommentListAdapter commentListAdapter = new CommentListAdapter(getContext());
        likersCount = (TextView) view.findViewById(R.id.likeCountTV);
        final Button likeBtn = (Button) view.findViewById(R.id.likeBtn);
        currentFragment = this;

        likersCount.setText(currentStory.getLikersCount() + " likes");
        isLike = currentStory.isLike(Session.currentUser.getUserId());
        if(isLike) {
            likeBtn.setText("UNLIKE");
        } else {
            likeBtn.setText("LIKE");
        }

        CommentController.getInstance().populateComments(commentListAdapter, currentStory.getStoryId());

        commentListView.setAdapter(commentListAdapter);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLike) {
                    StoryController.getInstance().dislikeStory(Session.currentUser.getUserId(), currentStory.getStoryId() ,currentFragment);
                    likeBtn.setText("LIKE");
                } else {
                    StoryController.getInstance().likeStory(Session.currentUser.getUserId(), currentStory.getStoryId(), currentFragment);
                    likeBtn.setText("UNLIKE");
                }
                isLike = !isLike;
            }
        });

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addComentTxt.getText().toString().equals("")) {
                    //commentListAdapter.clearComment();
                    //CommentController.getInstance().populateComments(commentListAdapter, currentStory.getStoryId());
                    //commentListView.setAdapter(commentListAdapter);
                    CommentController.getInstance().addComment(currentStory.getStoryId(), addComentTxt.getText().toString(), commentListAdapter);
                    NotificationController.getInstance().addNotif("Commented on your story", Session.currentUser.getUserId(), currentStory.getCurrentUser(), currentStory.getStoryId());
                    addComentTxt.setText("");
                }
            }
        });



        return view;
    }

}
