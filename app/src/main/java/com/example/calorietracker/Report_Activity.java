package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Report_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String reportDate;
    String reportSDate;
    String reportEDate;
    TextView reportView;
    Boolean pieDate = false;
    Boolean repSDate = false;
    Boolean repEDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        findViewById(R.id.btn_reportDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                pieDate = true;
            }
        });
        findViewById(R.id.btn_reportStartDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                repSDate = true;
            }
        });
        findViewById(R.id.btn_reportEndDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                repEDate = true;
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
    private void drawBarChart(float f1, float f2) {
        BarChart barChart = findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        barChart.getAxisRight().setEnabled(false);

        //data
        float groupSpace = 0.04f;
        float barSpace = 0.02f;
        float barWidth = 0.46f;

        int start = 1;
        int end = 4;

        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();

        for (int i = start; i < end; i++) {
            yVals1.add(new BarEntry(i, f1));
        }

        for (int i = start; i < end; i++) {
            yVals2.add(new BarEntry(i, f2));
        }

        BarDataSet set1, set2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Calories Consumed");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Calories Burnt");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(start, groupSpace, barSpace);
        barChart.invalidate();

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
        if(pieDate) {
            Button button = (Button)findViewById(R.id.btn_reportDate);
            button.setText(reportDate);
            PieReportAsyncTask pieReportAsyncTask = new PieReportAsyncTask();
            pieReportAsyncTask.execute();
        } else if(repSDate) {
            reportSDate = reportDate;
            repSDate = false;
            Button button = (Button)findViewById(R.id.btn_reportStartDate);
            button.setText(reportSDate);
        } else if(repEDate){
            reportEDate = reportDate;
            repEDate = false;
            Button button = (Button)findViewById(R.id.btn_reportEndDate);
            button.setText(reportEDate);
            BarReportAsyncTask barReportAsyncTask = new BarReportAsyncTask();
            barReportAsyncTask.execute();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
/*Report data for pie chart*/
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
    /*Report data for bar graph*/
    private class BarReportAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            SharedPreferences spUserData = Report_Activity.this.getSharedPreferences("User_File", Context.MODE_PRIVATE);
            Integer userId = spUserData.getInt("user_id", 0);
            return RestClient.getReportForAPeriod(userId, reportSDate, reportEDate);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] data = result.split(",");
            Integer total = Integer.parseInt(data[0]) + Integer.parseInt(data[1]);
            float consP = (float) ((Integer.parseInt(data[0]) * 100) / total);
            float burntP = (float) ((Integer.parseInt(data[1]) * 100) / total);
            drawBarChart(consP, burntP);
        }
    }
}