package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View vMain;
    TextView welcomeText;
    TextView setCalGoal;
    TextView currDate;
    TextView totalStepsTaken;
    TextView totalCalsConsumed;
    TextView totalCalsBurnt;

    public static MainFragment newInstance(String username, String calGoals, String currDate) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loggedUname", username);
        bundle.putString("loggedUCalGoals", calGoals);
        bundle.putString("currDate", currDate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeText = (TextView) vMain.findViewById(R.id.welcomeMessage);
        setCalGoal = (TextView) vMain.findViewById(R.id.setCalGoal);
        currDate = (TextView) vMain.findViewById(R.id.currDate);
        totalStepsTaken = (TextView) vMain.findViewById(R.id.totalStepsTaken);
        totalCalsBurnt = (TextView) vMain.findViewById(R.id.totalCalsBurnt);
        totalCalsConsumed = (TextView) vMain.findViewById(R.id.totalCalsConsumed);
        String uName = (String) getArguments().getString("loggedUname");
        String uCG = (String) getArguments().getString("loggedUCalGoals");
        String uDate = (String) getArguments().getString("currDate");
        ReportAsyncTask reportAsyncTask = new ReportAsyncTask();
        reportAsyncTask.execute(uDate);
        welcomeText.setText("Welcome: " + uName);
        setCalGoal.setText("Your Calorie Goals Set is: " + uCG);
        currDate.setText(uDate);

        return vMain;
    }

    private class ReportAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... date) {
            String[] calories;
            String reportDate = date[0].substring(0, date[0].indexOf(" "));
            SharedPreferences spUserData = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
            Integer userId = spUserData.getInt("user_id", 0);
            String calData = RestClient.getReportFromUserIdDate(userId, reportDate);
            calories = calData.split(",");
            totalCalsConsumed.setText("Total Calories Consumed today: "+calories[0]);
            String burntRest = RestClient.getUserCalsBurntAtRest(userId);
            Integer bR = Integer.parseInt(burntRest);
            int i =date[0].indexOf(" ");
            String hour = date[0].substring(i+1,date[0].indexOf(":"));
            Integer hR = Integer.parseInt(hour);
            Integer calsBurnt = Integer.parseInt(calories[1]);
            Integer totalBurnt =  calsBurnt + bR/hR;
            totalCalsBurnt.setText("Total Calories Burnt till now: " + String.valueOf(totalBurnt));
            return null;
        }
    }
}
