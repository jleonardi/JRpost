package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import edu.bluejack16_2.jrpost.adapters.DetailStoryViewAdapter;
import edu.bluejack16_2.jrpost.models.Story;

public class DetailStoryActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    DetailStoryViewAdapter adapter;
    Story currentStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        try{
            Intent intent = getIntent();
            byte[] buf = intent.getByteArrayExtra("story");
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream ois = new ObjectInputStream(bais);
            currentStory = (Story)ois.readObject();
            //Toast.makeText(this, story.getStoryId(), Toast.LENGTH_SHORT).show();
            viewPager= (ViewPager) findViewById(R.id.viewPager);
            tabLayout= (TabLayout) findViewById(R.id.tabLayout);
            adapter = new DetailStoryViewAdapter(getSupportFragmentManager());
            adapter.add(new DetailStoryFragment(),"Detail");
            adapter.add(new CommentDetailStoryFragment(),"Comment");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }catch (Exception e)
        {
            Toast.makeText(this, "error di Detail : "+e, Toast.LENGTH_SHORT).show();
        }
    }
}
