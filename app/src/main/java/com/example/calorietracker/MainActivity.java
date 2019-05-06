package com.example.calorietracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


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
        username = findViewById(R.id.text_username);
        password = findViewById(R.id.text_password);
        tmpName = username.getText().toString();
        tmpPw = password.getText().toString();
        TextView signupLink = findViewById(R.id.link_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersValidationAsyncTask validateUsers = new UsersValidationAsyncTask();
                validateUsers.execute();
                login();
            }
        });
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });


    }
    private class UsersValidationAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground (String... tmpName){
             RestClient.getCredentials(tmpName);
             return null;
        }
        @Override
        protected void onPostExecute (Void v){
            Intent intent = new Intent(MainActivity.this,Homescreen.class);
            startActivity(intent);
        }

    }
    private void login(String s) {
        Credential.checkCredential(s);

    }


}
