package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.bluejack16_2.jrpost.adapters.TabLayoutAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    /*
    SearchResultAdapter searchResultAdapter;
    Button searchBtn;
    EditText searchTxt;
    RadioButton rbUser, rbStory;
    */
    TabLayoutAdapter adapter;
    TabLayout tabLayoutSearch;
    ViewPager viewPagerSearch;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        tabLayoutSearch = (TabLayout) view.findViewById(R.id.tabLayoutSearch);
        viewPagerSearch= (ViewPager) view.findViewById(R.id.viewPagerSearch);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
        //addFragment Search user
        adapter.add(new SearchStoryFragment(),"Story");
        adapter.add(new SearchUserFragment(),"User");
        //addFragment Search Story
        viewPagerSearch.setAdapter(adapter);
        tabLayoutSearch.setupWithViewPager(viewPagerSearch);
        /*
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
                    Toast.makeText(getContext(), "Please select Search Category", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        return view;
    }

}
