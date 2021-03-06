package edu.bluejack16_2.jrpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import edu.bluejack16_2.jrpost.adapters.NotifViewAdapter;
import edu.bluejack16_2.jrpost.controllers.NotificationController;
import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.Notification;
import edu.bluejack16_2.jrpost.models.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }

    ListView listViewNotif;
    View currentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        final NotifViewAdapter notifViewAdapter = new NotifViewAdapter(getContext());
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        currentView = view;
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

        listViewNotif= (ListView) view.findViewById(R.id.listViewNotif);
        NotificationController.getInstance().getNotif(Session.currentUser.getUserId(),notifViewAdapter);
        listViewNotif.setAdapter(notifViewAdapter);

        listViewNotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notif = (Notification) notifViewAdapter.getItem(position);
                Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                intent.putExtra("story", notif.getStoryId());
                currentView.getContext().startActivity(intent);
            }
        });

        return view;
    }

}
