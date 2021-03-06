package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.User;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView=null;
    Toolbar toolbar=null;
    User currentUser;
    SharedPreferences prefs;
    ImageView profPic;

    public static FloatingActionButton fab;

    public void setPicture(User user) {
        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(Session.currentUser.getImageRef()).into(profPic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("Token", FirebaseInstanceId.getInstance().getToken());
        //set fragment awal
        setTitle("Timeline");
        TimelineFragment timelineFragment= new TimelineFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,timelineFragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                setTitle("New Story");
                NewStoryFragment newStoryFragment= new NewStoryFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,newStoryFragment);
                fragmentTransaction.commit();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //set name di header a user yang lagi login
        View headerView = navigationView.getHeaderView(0);
        profPic = (ImageView) headerView.findViewById(R.id.imageView);
        profPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(getApplicationContext(), CurrentUserProfileActivity.class));
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView lblName = (TextView) headerView.findViewById(R.id.lblName);
        TextView lblUsername = (TextView) headerView.findViewById(R.id.lblUsername);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lblName.setText(Session.currentUser.getName());
        lblUsername.setText(Session.currentUser.getUsername());

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            Session.currentUser=null;
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //jadi di a ne banyak fragment, tiap klik klik menu yang disamping a toh je dak pindah page
        //tapi ganti fragment doang
        if (id == R.id.nav_newStory)
        {
            setTitle("New Story");
            NewStoryFragment newStoryFragment= new NewStoryFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,newStoryFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_leaderboard)
        {
            setTitle("Leaderboard");
            LeaderboardFragment fragment= new LeaderboardFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_search)
        {
            setTitle("Search");
            SearchFragment searchFragment= new SearchFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,searchFragment);
            fragmentTransaction.commit();
        }
        else if(id==R.id.nav_Timeline)
        {
            setTitle("Timeline");
            TimelineFragment timelineFragment= new TimelineFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,timelineFragment);
            fragmentTransaction.commit();
        }
        else if(id==R.id.nav_notification)
        {
            setTitle("Notification");
            NotificationFragment notifFragment = new NotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,notifFragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
