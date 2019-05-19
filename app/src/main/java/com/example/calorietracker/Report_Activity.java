package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class Report_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String reportDate;
    TextView reportView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        findViewById(R.id.btn_reportDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });
    }

    private void drawChart(float f1, float f2, float f3) {
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(f1, "Consumed", 0));
        yvalues.add(new PieEntry(f2, "Burnt", 1));
        yvalues.add(new PieEntry(f3, "Remaining", 2));

        PieDataSet dataSet = new PieDataSet(yvalues, "Calories");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);
    }

    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Report_Activity.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //the datepicker is returning May as the fourth month
        int newMonth = month+1;
        reportDate = year + "-0" + newMonth + "-" + dayOfMonth;
        reportView = findViewById(R.id.reportDate);
        reportView.setText(reportDate);
        PieReportAsyncTask pieReportAsyncTask = new PieReportAsyncTask();
        pieReportAsyncTask.execute();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class PieReportAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            SharedPreferences spUserData = Report_Activity.this.getSharedPreferences("User_File", Context.MODE_PRIVATE);
            Integer userId = spUserData.getInt("user_id", 0);
            return RestClient.getReportFromUserIdDate(userId, reportDate);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] data = result.split(",");
            Integer total = Integer.parseInt(data[0])+Integer.parseInt(data[1])+Integer.parseInt(data[2]);
            float consP = (float) ((Integer.parseInt(data[0])*100)/total);
            float burntP = (float) ((Integer.parseInt(data[1])*100)/total);
            float remP = (float) ((Integer.parseInt(data[2])*100)/total);
            drawChart(consP,burntP,remP);
        }
    }
}