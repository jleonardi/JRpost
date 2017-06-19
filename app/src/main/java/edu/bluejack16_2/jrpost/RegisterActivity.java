package edu.bluejack16_2.jrpost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.jrpost.controllers.UserController;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toast.makeText(this, UserController.getInstance().addNewUser("Test", "Anjay", "kodokAnjay").toString(), Toast.LENGTH_SHORT).show();
    }
}
