package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.adapters.UserStoryListAdapter;
import edu.bluejack16_2.jrpost.controllers.FollowController;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;
    StoryViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");
        //Toast.makeText(this, currentUser.getUserId(), Toast.LENGTH_SHORT).show();

        TextView nameTV = (TextView) findViewById(R.id.lblName);
        adapter= new StoryViewAdapter();
        final Button btnFollow = (Button) findViewById(R.id.btnFollow);
        FollowController.getInstance().checkFollowUser(btnFollow,currentUser.getUserId());
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnFollow.getText().equals("Follow"))
                {
                    FollowController.getInstance().followUser(currentUser.getUserId());
                    btnFollow.setText("Unfollow");
                }
                else
                {
                    FollowController.getInstance().unFollowUser(currentUser.getUserId());
                    btnFollow.setText("Follow");
                }
            }
        });

        ListView userStoryListView = (ListView) findViewById(R.id.listViewStory);
        final UserStoryListAdapter adapter = new UserStoryListAdapter(currentUser, getApplicationContext());
        userStoryListView.setAdapter(adapter);

        StoryController.getInstance().getStoryOnUserId(currentUser.getUserId(), adapter);


        nameTV.setText(currentUser.getName());

        userStoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //error coy
                Story story = (Story) adapter.getItem(i);
                Toast.makeText(ProfileActivity.this, story.getStoryTitle()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DetailStoryActivity.class);
                try{
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(story);
                    byte[] buf = baos.toByteArray();
                    intent.putExtra("story", buf);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(ProfileActivity.this, e+"", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
