package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import edu.bluejack16_2.jrpost.adapters.SearchResultAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchStoryFragment extends Fragment {

    Button searchBtn;
    EditText txtSearch;
    public SearchStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        View view = inflater.inflate(R.layout.fragment_search_story, container, false);
        searchBtn= (Button)view.findViewById(R.id.btnSearch);
        txtSearch = (EditText) view.findViewById(R.id.txtSearch);
        final SearchResultAdapter searchResultAdapter= new SearchResultAdapter(view.getContext());
        ListView searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
        searchResultListView.setAdapter(searchResultAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchResultAdapter.clearAll();
                searchResultAdapter.notifyDataSetChanged();
                StoryController.getInstance().getStoryOnSearchTitle(searchResultAdapter, txtSearch.getText().toString(), "All");
            }
        });
        return view;
    }

}
