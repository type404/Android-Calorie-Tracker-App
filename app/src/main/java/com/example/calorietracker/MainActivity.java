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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = findViewById(R.id.btn_login);
        username = findViewById(R.id.text_username);
        password = findViewById(R.id.text_password);
        TextView signupLink = findViewById(R.id.link_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                Intent intent = new Intent(MainActivity.this,Homescreen.class);
                startActivity(intent);
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

    private void login() {

    }
    private class CoursesAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getData();
        }
        @Override
        protected void onPostExecute (String courses){
            TextView resultTextView = findViewById(R.id.tvResult);
            resultTextView.setText(courses);
        }
    }

}
