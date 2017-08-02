package edu.bluejack16_2.jrpost;


import android.app.ProgressDialog;
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
import android.widget.Toast;

import edu.bluejack16_2.jrpost.adapters.UserListAdapter;
import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUserFragment extends Fragment {


    public ProgressDialog progressDialog;
    SearchUserFragment current;
    public SearchUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        current = this;
        Button searchBTN = (Button) view.findViewById(R.id.btnSearch);
        final EditText searchTV = (EditText) view.findViewById(R.id.txtSearch);
        ListView searchResultListView = (ListView) view.findViewById(R.id.listViewSearchUser);
        final UserListAdapter userListAdapter = new UserListAdapter(getContext());
        searchResultListView.setAdapter(userListAdapter);

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListAdapter.clearUser();
                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                UserController.getInstance().searchUser(userListAdapter, searchTV.getText().toString(), current);
            }
        });

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User)userListAdapter.getItem(position);
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("userId", user.getUserId());
                startActivity(intent);
            }
        });

        return view;
    }
}
