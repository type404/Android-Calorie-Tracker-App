package com.example.calorietracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class Report extends Fragment {
    PieChartView chart;

    View vReport;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report, container, false);
        PieChartView chart = vReport.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE));
        pieData.add(new SliceValue(25, Color.GRAY));
        pieData.add(new SliceValue(10, Color.RED));
        pieData.add(new SliceValue(60, Color.MAGENTA));
        PieChartData pieChartData = new PieChartData(pieData);
        chart.setPieChartData(pieChartData);
        return vReport;
    }
}
