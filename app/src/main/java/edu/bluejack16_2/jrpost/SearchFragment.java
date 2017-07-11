package edu.bluejack16_2.jrpost;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    TabLayoutAdapter adapter;
    TabLayout tabLayoutSearch;
    ViewPager viewPagerSearch;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

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

        tabLayoutSearch = (TabLayout) view.findViewById(R.id.tabLayoutSearch);
        viewPagerSearch= (ViewPager) view.findViewById(R.id.viewPagerSearch);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
        adapter.add(new SearchStoryFragment(),"Story");
        adapter.add(new SearchUserFragment(),"User");
        viewPagerSearch.setAdapter(adapter);
        tabLayoutSearch.setupWithViewPager(viewPagerSearch);

        return view;
    }

}
