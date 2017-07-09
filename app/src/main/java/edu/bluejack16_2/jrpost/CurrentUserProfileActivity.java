package edu.bluejack16_2.jrpost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.bluejack16_2.jrpost.adapters.CurrentProfileAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Session;

public class CurrentUserProfileActivity extends AppCompatActivity {

    TextView lblName;
    TextView lblUsername;
    ImageView imgProfile;
    ListView listViewProf;
    CurrentProfileAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_profile);

        lblName= (TextView) findViewById(R.id.lblName);
        lblName.setText(Session.currentUser.getName());
        lblUsername= (TextView) findViewById(R.id.lblUsername);
        lblUsername.setText(Session.currentUser.getUsername());
        imgProfile= (ImageView) findViewById(R.id.imgProfile);
        listViewProf= (ListView) findViewById(R.id.listViewProf);
        CurrentProfileAdapter adapter= new CurrentProfileAdapter(getApplicationContext());

        StoryController.getInstance().getStoryOnUserId(Session.currentUser.getUserId(),adapter);

        listViewProf.setAdapter(adapter);
    }
}
