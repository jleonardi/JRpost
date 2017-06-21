package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    LoginButton btnLoginFb;
    CallbackManager callbackManager;
    SharedPreferences prefs;
    SignInButton btnLoginGmail;
    GoogleApiClient googleApiClient;
    final int REQ_CODE = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        btnLoginGmail = (SignInButton) findViewById(R.id.btnLoginGmail);
        btnLoginGmail.setOnClickListener(this);

        txtUsername= (EditText) findViewById(R.id.txtUsername);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLoginFb = (LoginButton) findViewById(R.id.fb_login_bn);
        btnLoginFb.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
        callbackManager = CallbackManager.Factory.create();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefs.getString("username", null) != null) {
            try {
                String userId = prefs.getString("userId", null);
                String name = prefs.getString("name", null);
                String password = prefs.getString("password", null);
                String username = prefs.getString("username", null);
                Session.currentUser = new User(userId, username, name, password);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
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
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("name",name);
                                    editor.putString("username",email);
                                    editor.commit();
                                    Session.currentUser = new User();
                                    Session.currentUser.setName(name);
                                    Session.currentUser.setUsername(email);

                                    //harus a disini kodingan insert org yang login pake fb ke firebase
                                    //tapi divalidasiin klo udah pernah login pake fb gk usah insert lagi
                                    //bedain yang login pake fb sama yang register, login pake fb passwordnya kosong

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQ_CODE) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(result);
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
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
        else if(view==btnLoginGmail)
        {
            signInGmail();
        }
        else if(view==btnRegister)
        {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signInGmail()
    {
        signOutGmail();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void signOutGmail()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    private void handleResult(GoogleSignInResult result)
    {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            //String img_url = account.getPhotoUrl.toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name",name);
            editor.putString("username",email);
            editor.commit();
            Session.currentUser = new User();
            Session.currentUser.setName(name);
            Session.currentUser.setUsername(email);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
    }

}
