package com.example.calorietracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Report_Fragment extends Fragment {
    View vReport;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report, container, false);
        drawChart();
        return vReport;
    }
    private void drawChart() {
        PieChart pieChart = vReport.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "January", 0));
        yvalues.add(new PieEntry(15f, "February", 1));
        yvalues.add(new PieEntry(12f, "March", 2));
        yvalues.add(new PieEntry(25f, "April", 3));
        yvalues.add(new PieEntry(23f, "May", 4));
        yvalues.add(new PieEntry(17f, "June", 5));

        PieDataSet dataSet = new PieDataSet(yvalues, "Results");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("desc");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

    }
}