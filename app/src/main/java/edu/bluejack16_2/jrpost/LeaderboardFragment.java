package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.controllers.LeaderboardController;
import edu.bluejack16_2.jrpost.controllers.StoryController;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    Spinner spinner;
    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        final ListView listViewLeaderBoard= (ListView) view.findViewById(R.id.listViewLeaderBoard);
        final StoryViewAdapter adapter = new StoryViewAdapter(getContext());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
            listViewLeaderBoard.setAdapter(adapter);
        }catch (Exception e)
        {
            Log.d("error coy",e+"");
        }

        return view;
    }

}
