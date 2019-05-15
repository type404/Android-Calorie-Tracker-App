package com.example.calorietracker;

import android.arch.persistence.room.Room;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserSteps extends Fragment {
    StepsDatabase db = null;
    Button addStepsButton;
    Button updateStepsButton;
    EditText addSteps;
    View vUserSteps;
    List<HashMap<String, String>> stepListArray;
    SimpleAdapter myListAdapter;
    ListView stepList;
    String[] colHEAD;
    int[] dataCell;
    TextView editSteps;
    Button deleteStepsButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vUserSteps = inflater.inflate(R.layout.fragment_user_steps, container, false);
        addStepsButton = vUserSteps.findViewById(R.id.addStepsButton);
        deleteStepsButton = vUserSteps.findViewById(R.id.buttonDeleteSteps);
        addSteps = vUserSteps.findViewById(R.id.addSteps);
        db = Room.databaseBuilder(vUserSteps.getContext(),
                StepsDatabase.class, "StepDatabase")
                .fallbackToDestructiveMigration()
                .build();

        colHEAD = new String[] {"TIME","STEPS"};
        dataCell = new int[] {R.id.dateTime,R.id.stepsTaken};
        stepList = vUserSteps.findViewById(R.id.list_view);
        stepListArray = new ArrayList<HashMap<String, String>>();

        addStepsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String newStep = addSteps.getText().toString();
                if(!newStep.isEmpty()) {
                    InsertDatabaseAsyncTask insertDatabaseAsyncTask = new InsertDatabaseAsyncTask();
                    insertDatabaseAsyncTask.execute(newStep);
                    ReadDatabase readDatabase = new ReadDatabase();
                    readDatabase.execute();
                } else {
                    addSteps.setError("Empty String");
                    error();
                }
            }
        });
        /*editSteps.setOnClickListener(v -> {
            addStepsButton.setVisibility(View.GONE);
            updateStepsButton.setVisibility(View.VISIBLE);
        });
        updateStepsButton.setOnClickListener(v -> {
            String updatedStep = addSteps.getText().toString();
            UpdateDatabase updateDatabase = new UpdateDatabase();
            updateDatabase.execute(updatedStep);
        });*/
                   return vUserSteps;
    }

    private View error() {
        Toast.makeText(vUserSteps.getContext(), "Enter Valid Steps",Toast.LENGTH_LONG).show();
        return vUserSteps;
    }
    private class InsertDatabaseAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String curr_date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            StepsTable stepsTable = new StepsTable(curr_date, (params[0]));
            db.stepsDAO().insert(stepsTable);
            return "Steps Added!";
        }

        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(vUserSteps.getContext(),details,Toast.LENGTH_LONG).show();
        }
    }
    private class ReadDatabase extends AsyncTask<Void, Void, HashMap> {
        @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            List<StepsTable> steps = db.stepsDAO().getAll();
            HashMap<String, String> entryList = new HashMap<String, String>();
            if (!(steps.isEmpty() || steps == null) ){
                for (StepsTable temp : steps) {
                    entryList.put("TIME", temp.getTime());
                    entryList.put("STEPS", temp.getSteps());
                }
                return entryList;
            }
            else
                entryList.put("TIME","No entry for today");
                entryList.put("STEPS","No steps for today");
                return entryList;
        }
        @Override
        protected void onPostExecute(HashMap map) {
                stepListArray.add((HashMap<String, String>) map);
                myListAdapter = new SimpleAdapter(vUserSteps.getContext(), stepListArray, R.layout.list_view, colHEAD, dataCell);
                stepList.setAdapter(myListAdapter);
        }
    }
    private class UpdateDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return "User updated";
        }

        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(vUserSteps.getContext(),details,Toast.LENGTH_LONG).show();
        }
    }
}