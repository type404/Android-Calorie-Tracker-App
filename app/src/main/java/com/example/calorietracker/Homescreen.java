package com.example.calorietracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//    TextView welcomeText;
//    TextView calGoals;
static Integer userId;
    String uName;
    String getCalGoals;
    String curr_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calorie Tracker");

//      welcomeText = findViewById(R.id.welcomeMessage);
        Bundle bundle = getIntent().getExtras();
        Users aUser = bundle.getParcelable("loggedInUser");
        uName = aUser.getFirstname();
        userId = aUser.getUserId();
 //       welcomeText.setText("Welcome " + name);

        curr_date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
//        TextView date = (TextView) findViewById(R.id.currDate);
//        date.setText(curr_date);

//        calGoals = findViewById(R.id.setCalGoal);
        UserGetReportAsyncTask userReport = new UserGetReportAsyncTask();
        userReport.execute();



    }

    private class UserGetReportAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            return RestClient.getReportFromUserId(userId);
        }

        @Override
        protected void onPostExecute(String json) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            ReportNetbeans rn = gson.fromJson(json, ReportNetbeans.class);
            getCalGoals = rn.getSetCalsGoal().toString();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = MainFragment.newInstance(uName,getCalGoals,curr_date);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homescreen, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch (id) {
            case R.id.nav_home:
                nextFragment = new MainFragment();
                break;
            case R.id.nav_daily_diet:
                nextFragment = new DailyDietFragment();
                break;
            case R.id.nav_steps_screen:
                nextFragment = new UserSteps();
                break;
            case R.id.nav_reports:
                nextFragment = new Report();
                break;
            case R.id.nav_maps:
                Intent i = new Intent(Homescreen.this, Mapsscreen.class);
                startActivity(i);
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        assert nextFragment != null;
        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //returns the userId for the logged in User
    public static int getCurrUserId(){
        return userId;
    }
}
