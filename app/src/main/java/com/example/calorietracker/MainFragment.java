package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View vMain;
    TextView welcomeText;
    TextView setCalGoal;
    TextView currDate;
    TextView totalStepsTaken;
    TextView totalCalsConsumed;
    TextView totalCalsBurnt;
    Button btn_updateCalGoal;
    EditText etUpdateCalGoal;
    String calsConsumed;
    String tcb;

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
//        totalStepsTaken = (TextView) vMain.findViewById(R.id.totalStepsTaken);
        totalCalsBurnt = (TextView) vMain.findViewById(R.id.totalCalsBurnt);
        totalCalsConsumed = (TextView) vMain.findViewById(R.id.totalCalsConsumed);
        btn_updateCalGoal = vMain.findViewById(R.id.btn_updateCalGoal);
        etUpdateCalGoal = vMain.findViewById(R.id.text_updateCalGoal);
        String uName = (String) getArguments().getString("loggedUname");
        String uCG = (String) getArguments().getString("loggedUCalGoals");
        String uDate = (String) getArguments().getString("currDate");
        ReportAsyncTask reportAsyncTask = new ReportAsyncTask();
        reportAsyncTask.execute(uDate);
        welcomeText.setText("Welcome: " + uName);
        setCalGoal.setText(uCG);
        btn_updateCalGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calGoals = etUpdateCalGoal.getText().toString();
                setCalGoal.setText(calGoals);
                SharedPreferences sharedPref = vMain.getContext().getSharedPreferences("User_CalGoal", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPref.edit();
                ed.putString("updated_cal_goals",calGoals);
                ed.commit();
            }
        });
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
            calsConsumed = calories[0];
            String burntRest = RestClient.getUserCalsBurntAtRest(userId);
            Integer bR = Integer.parseInt(burntRest);
            int i =date[0].indexOf(" ");
            String hour = date[0].substring(i+1,date[0].indexOf(":"));
            Integer hR = Integer.parseInt(hour);
            Integer calsBurnt = Integer.parseInt(calories[1]);
            Integer totalBurnt =  calsBurnt + bR/hR;
            tcb = String.valueOf(totalBurnt);
            return null;
        }
        protected void onPostExecute(Void v) {
            totalCalsConsumed.setText("Total Calories Consumed today: "+ calsConsumed);
            totalCalsBurnt.setText("Total Calories Burnt till now: " + tcb);
        }
    }
}
