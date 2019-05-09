package com.example.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Maps extends Fragment {
    View vMaps;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMaps = inflater.inflate(R.layout.fragment_maps, container, false);
        return vMaps;
    }
}
