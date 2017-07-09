package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import edu.bluejack16_2.jrpost.adapters.TabLayoutAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.tasks.ReadAvailableLanguageTask;

public class DetailStoryActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabLayoutAdapter adapter;
    Story currentStory;
    Menu menu;

    public void addStory(Story story) {
        //currentStory = (Story)ois.readObject();
        currentStory = story;
        if(story.getUser().getUserId().equals(Session.currentUser.getUserId())) {
            MenuItem menuItem = menu.getItem(0);
            menuItem.setTitle("Delete");
        }
        //Toast.makeText(this, story.getStoryId(), Toast.LENGTH_SHORT).show();
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabLayoutAdapter(getSupportFragmentManager());
        adapter.add(new DetailStoryFragment(),"Detail");
        adapter.add(new CommentDetailStoryFragment(),"Comment");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        try{
            if(id == R.id.translateMenu)
            {
                MenuItem menuItem = menu.findItem(id);
                if(menuItem.getTitle().equals("Delete")) {
                    StoryController.getInstance().deleteStory(currentStory.getStoryId(), this);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), TranslateChoiceActivity.class);
                    intent.putExtra("content", currentStory.getStoryContent());
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            Log.d("Delete/Translate", e.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        try{
            Intent intent = getIntent();
//            byte[] buf = intent.getByteArrayExtra("story");
//            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
//            ObjectInputStream ois = new ObjectInputStream(bais);
            String storyId = intent.getStringExtra("story");
            StoryController.getInstance().getStoryOnId(storyId, this);

        }catch (Exception e)
        {
            Toast.makeText(this, "error di Detail : "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.translate_menu_detail, menu);
        this.menu = menu;
        return true;
    }
}
