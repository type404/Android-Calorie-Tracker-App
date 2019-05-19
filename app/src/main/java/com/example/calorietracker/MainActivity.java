package com.example.calorietracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    String tmpName;
    String tmpPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = findViewById(R.id.btn_login);
        TextView signupLink = findViewById(R.id.link_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = findViewById(R.id.text_username);
                password = findViewById(R.id.text_password);
                tmpName = username.getText().toString();
                tmpPw = password.getText().toString();
                UsersValidationAsyncTask validateUsers = new UsersValidationAsyncTask();
                validateUsers.execute();
            }
        });
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
/*Checks Id and password from credential DB*/
    private class UsersValidationAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            return RestClient.getCredentials(tmpName);
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                String pwHash = Credential.passHashConverter(tmpPw);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Credential userCredential = gson.fromJson(json, Credential.class);
                Users loggedInUser = userCredential.getUserId();
                /*Sends user object to the homescreen*/
                if (pwHash.equals(userCredential.getPasswordHash())) {
                    Intent intent = new Intent(MainActivity.this, Homescreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("loggedInUser", loggedInUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Wrong user id and password!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
