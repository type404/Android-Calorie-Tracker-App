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
    private Users aUser;

    public static MainFragment newInstance(Users user){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("logged Users", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);

        welcomeText = (TextView) vMain.findViewById(R.id.welcomeMessage);
        aUser = (Users) getArguments().getParcelable("logged Users");
        String name = aUser.getFirstname();
        welcomeText.setText("Welcome " + name);
        return vMain;
    }
}
