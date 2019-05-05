package com.example.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserSteps extends Fragment {
    View vUserSteps;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vUserSteps = inflater.inflate(R.layout.fragment_homescreen_main, container, false);
        return vUserSteps;
    }
}
