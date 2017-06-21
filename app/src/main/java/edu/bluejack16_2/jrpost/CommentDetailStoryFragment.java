package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import edu.bluejack16_2.jrpost.adapters.CommentListAdapter;
import edu.bluejack16_2.jrpost.controllers.CommentController;
import edu.bluejack16_2.jrpost.models.Story;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentDetailStoryFragment extends Fragment {

    Story currentStory;
    public CommentDetailStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_detail_story, container, false);
        currentStory = ((DetailStoryActivity)getActivity()).currentStory;

        ListView commentListView = (ListView) view.findViewById(R.id.commentListView);
        final EditText addComentTxt = (EditText) view.findViewById(R.id.addCommentTxt);
        Button addCommentBtn = (Button) view.findViewById(R.id.addCommentBtn);
        CommentListAdapter commentListAdapter = new CommentListAdapter(getContext());


        CommentController.getInstance().populateComments(commentListAdapter, currentStory.getStoryId());

        commentListView.setAdapter(commentListAdapter);

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addComentTxt.getText().toString().equals("")) {
                    CommentController.getInstance().addComment(currentStory.getStoryId(), addComentTxt.getText().toString());
                }
            }
        });

        return view;
    }

}
