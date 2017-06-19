package edu.bluejack16_2.jrpost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.jrpost.controllers.UserController;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;

    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirm;
    EditText txtName;
    Button btnLogin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtUsername= (EditText) findViewById(R.id.txtUsername);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        txtName = (EditText) findViewById(R.id.txtName);
        txtConfirm= (EditText) findViewById(R.id.txtConfirm);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnLogin)
        {

        }
        else if(view==btnRegister)
        {
            String username = txtUsername.getText().toString();
            String name = txtName.getText().toString();
            String password = txtPassword.getText().toString();
            String confirm = txtConfirm.getText().toString();

            if(username.length()==0)
                Toast.makeText(this, "Username must be filled", Toast.LENGTH_SHORT).show();
            else if(name.length()==0)
                Toast.makeText(this, "Name must be filled", Toast.LENGTH_SHORT).show();
            else if(password.length()==0)
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            else if(!password.equals(confirm))
                Toast.makeText(this, "Password does not match the Confirm Password", Toast.LENGTH_SHORT).show();
            else
            {
                UserController.getInstance().addNewUser(username,name,password);
                Toast.makeText(this, "Succesfully Register", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
