package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack16_2.jrpost.controllers.UserController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername= (EditText) findViewById(R.id.txtUsername);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnLogin)
        {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if(username.length()==0)
                Toast.makeText(this, "Username must be filled", Toast.LENGTH_SHORT).show();
            else if(password.length()==0)
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            else
            {
                UserController.getInstance().getUser(username, password, this);
            }

        }
        else if(view==btnRegister)
        {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
        }
    }
}
