package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.jrpost.adapters.UserStoryListAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.User;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");
        Toast.makeText(this, currentUser.getUserId(), Toast.LENGTH_SHORT).show();

        TextView nameTV = (TextView) findViewById(R.id.lblName);

        ListView userStoryListView = (ListView) findViewById(R.id.listViewStory);
        UserStoryListAdapter adapter = new UserStoryListAdapter(currentUser, getApplicationContext());
        userStoryListView.setAdapter(adapter);
        StoryController.getInstance().getStoryOnUserId(currentUser.getUserId(), adapter);


        nameTV.setText(currentUser.getName());

    }
}
