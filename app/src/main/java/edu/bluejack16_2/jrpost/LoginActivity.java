package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    LoginButton btnLoginFb;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        txtUsername= (EditText) findViewById(R.id.txtUsername);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLoginFb = (LoginButton) findViewById(R.id.fb_login_bn);
        btnLoginFb.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
        callbackManager = CallbackManager.Factory.create();
        btnLoginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                  loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email=object.getString("email");
                                    String name=object.getString("name");
                                    Session.name = name;
                                    Session.username=email;

                                    //harus a disini kodingan insert org yang login pake fb ke firebase
                                    //tapi divalidasiin klo udah pernah login pake fb gk usah insert lagi
                                    //bedain yang login pake fb sama yang register, login pake fb passwordnya kosong

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
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
