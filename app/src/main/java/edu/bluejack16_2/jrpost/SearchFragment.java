package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import edu.bluejack16_2.jrpost.adapters.SearchResultAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchResultAdapter searchResultAdapter;
    Button searchBtn;
    EditText searchTxt;
    RadioButton rbUser, rbStory;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ListView searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
        searchResultAdapter = new SearchResultAdapter(getContext());
        searchResultListView.setAdapter(searchResultAdapter);

        searchBtn = (Button) view.findViewById(R.id.btnSearch);
        searchTxt = (EditText) view.findViewById(R.id.txtSearch);

        rbStory = (RadioButton) view.findViewById(R.id.rdStory);
        rbUser = (RadioButton) view.findViewById(R.id.rdUser);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultAdapter.clearAll();
                searchResultAdapter.notifyDataSetChanged();
                if(rbStory.isChecked()) {
                    StoryController.getInstance().getStoryOnSearchTitle(searchResultAdapter, searchTxt.getText().toString(), "All");
                } else if(rbUser.isChecked()) {
                    StoryController.getInstance().getStoryOnUsername(searchResultAdapter, searchTxt.getText().toString(), "All");
                } else {
                    Toast.makeText(getContext(), "Please select radiobutton", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
