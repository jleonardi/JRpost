package edu.bluejack16_2.jrpost;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Collection;

import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.controllers.LeaderboardController;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Notification;
import edu.bluejack16_2.jrpost.models.Story;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    Spinner spinner;
    public static View view;
    View currentView;

    public ProgressDialog progressDialog;
    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        currentView = view;
        spinner = (Spinner) view.findViewById(R.id.spinner);
        final ListView listViewLeaderBoard= (ListView) view.findViewById(R.id.listViewLeaderBoard);
        final StoryViewAdapter adapter = new StoryViewAdapter(getContext());
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                progressDialog = new ProgressDialog(view.getContext());
//                progressDialog.setMessage("Please wait");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
                String genre=spinner.getSelectedItem().toString();
                adapter.clearStory();
                LeaderboardController.getInstance().getByGenre(genre, adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        try {
            //adapter.sortByLike();
            listViewLeaderBoard.setAdapter(adapter);
            listViewLeaderBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Story story = (Story) adapter.getItem(position);
                    Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                    intent.putExtra("story", story.getStoryId());
                    currentView.getContext().startActivity(intent);
                }
            });
        }catch (Exception e)
        {
            Log.d("error coy",e+"");
        }

        return view;
    }

}
