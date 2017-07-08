package edu.bluejack16_2.jrpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import edu.bluejack16_2.jrpost.adapters.SearchResultAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchStoryFragment extends Fragment {

    Button searchBtn;
    EditText txtSearch;
    Spinner cmbGenre;
    public SearchStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        final View view = inflater.inflate(R.layout.fragment_search_story, container, false);
        searchBtn= (Button)view.findViewById(R.id.btnSearch);
        txtSearch = (EditText) view.findViewById(R.id.txtSearch);
        cmbGenre = (Spinner) view.findViewById(R.id.cmbGenre);
        ListView listViewUser = (ListView) view.findViewById(R.id.searchResultListView);
        final SearchResultAdapter searchResultAdapter= new SearchResultAdapter(view.getContext());
        ListView searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
        searchResultListView.setAdapter(searchResultAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genre = cmbGenre.getSelectedItem().toString();
                searchResultAdapter.clearAll();
                searchResultAdapter.notifyDataSetChanged();
                StoryController.getInstance().getStoryOnSearchTitle(searchResultAdapter, txtSearch.getText().toString(),genre);
                Toast.makeText(view.getContext(), genre, Toast.LENGTH_SHORT).show();
            }
        });

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Story story = (Story) searchResultAdapter.getItem(i);
                //Toast.makeText(view.getContext(), story.getStoryTitle()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                try{
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ObjectOutputStream oos = new ObjectOutputStream(baos);
//                    oos.writeObject(story);
//                    byte[] buf = baos.toByteArray();
//                    intent.putExtra("story", buf);
                    intent.putExtra("story", story.getStoryId());
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(view.getContext(), e+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
