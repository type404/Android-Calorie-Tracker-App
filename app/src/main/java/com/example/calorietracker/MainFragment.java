package com.example.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View vMain;
    TextView welcomeText;
    TextView setCalGoal;
    TextView currDate;

    private Users aUser;


    public static MainFragment newInstance(String username, String calGoals, String currDate){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loggedUname", username);
        bundle.putString("loggedUCalGoals", calGoals);
        bundle.putString("currDate",currDate);
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
        String uName = (String) getArguments().getString("loggedUname");
        String uCG = (String) getArguments().getString("loggedUCalGoals");
        String uDate = (String) getArguments().getString("currDate");
        welcomeText.setText("Welcome: " + uName);
        setCalGoal.setText("Your set Cal Goals: " + uCG);
        currDate.setText(uDate);
        return vMain;
    }
}
