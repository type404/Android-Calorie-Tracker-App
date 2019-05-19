package com.example.calorietracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    Integer calsConsumed = 0;
    Integer totalBurnt = 0;
    Integer totalStepsTaken = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vUserSteps = inflater.inflate(R.layout.fragment_user_steps, container, false);
        addStepsButton = vUserSteps.findViewById(R.id.addStepsButton);
        updateStepsButton = vUserSteps.findViewById(R.id.updateStepsButton);
        addSteps = vUserSteps.findViewById(R.id.addSteps);
        db = Room.databaseBuilder(vUserSteps.getContext(),
                StepsDatabase.class, "StepDatabase")
                .fallbackToDestructiveMigration()
                .build();
        colHEAD = new String[]{"ID", "TIME", "STEPS"};
        dataCell = new int[]{R.id.stepId, R.id.dateTime, R.id.stepsTaken};
        stepList = vUserSteps.findViewById(R.id.list_view);
        stepListArray = new ArrayList<HashMap<String, String>>();
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
        addStepsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String newStep = addSteps.getText().toString();
                if (!newStep.isEmpty()) {
                    InsertDatabaseAsyncTask insertDatabaseAsyncTask = new InsertDatabaseAsyncTask();
                    insertDatabaseAsyncTask.execute(newStep);
                } else {
                    addSteps.setError("Empty String");
                    error();
                }
            }
        });
        vUserSteps.findViewById(R.id.btn_updateReportTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReport();
            }
        });
        return vUserSteps;
    }


    private View error() {
        Toast.makeText(vUserSteps.getContext(), "Enter Valid Steps", Toast.LENGTH_LONG).show();
        return vUserSteps;
    }

    private class InsertDatabaseAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String curr_date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            StepsTable stepsTable = new StepsTable(curr_date, Integer.parseInt(params[0]));
            db.stepsDAO().insert(stepsTable);
            ReadDatabaseOne readDatabase = new ReadDatabaseOne();
            readDatabase.execute();
            return "Steps Added!";
        }

        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(vUserSteps.getContext(), details, Toast.LENGTH_LONG).show();
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<StepsTable> steps = db.stepsDAO().getAll();
            SharedPreferences sharedPref = vUserSteps.getContext().getSharedPreferences("User_TotalSteps", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPref.edit();
            ed.putInt("total_steps", totalStepsTaken);
            ed.commit();
            if (!(steps.isEmpty() || steps == null)) {
                for (StepsTable temp : steps) {
                    HashMap<String, String> entryList = new HashMap<String, String>();
                    entryList.put("ID", String.valueOf(temp.getId()));
                    entryList.put("TIME", temp.getTime());
                    entryList.put("STEPS", String.valueOf(temp.getSteps()));
                    stepListArray.add(entryList);
                }
                return "Your steps for the day!";
            } else
                return "No steps Taken!";
        }
        @Override
        protected void onPostExecute(String result) {
            showList();
        }
    }

        private void showList() {
            myListAdapter = new SimpleAdapter(vUserSteps.getContext(), stepListArray, R.layout.list_view, colHEAD, dataCell);
            stepList.setAdapter(myListAdapter);
            stepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = String.valueOf(stepList.getItemAtPosition(position));
                    String[] clickedItem = s.split(",");
                    addStepsButton.setVisibility(View.GONE);
                    updateStepsButton.setVisibility(View.VISIBLE);
                    updateStepsButton.setOnClickListener(v -> {
                        String updatedStep = addSteps.getText().toString();
                        UpdateDatabase updateDatabase = new UpdateDatabase();
                        updateDatabase.execute(clickedItem[1], updatedStep);
                        updateStepsButton.setVisibility(View.GONE);
                        addStepsButton.setVisibility(View.VISIBLE);
                    });
                }
            });
        }

        private class UpdateDatabase extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                Integer index = params[0].indexOf("=");
                Integer id = Integer.parseInt(params[0].substring(index + 1));
                StepsTable s = db.stepsDAO().findByID(id);
                s.setSteps(Integer.valueOf(params[1]));
                db.stepsDAO().updateUsers(s);
                return "Steps Updated!";
            }

            @Override
            protected void onPostExecute(String details) {
                showList();
                Toast.makeText(vUserSteps.getContext(), details, Toast.LENGTH_LONG).show();
            }
        }

        private class ReadDatabaseOne extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                List<StepsTable> steps = db.stepsDAO().getAll();
                HashMap<String, String> entryList = new HashMap<String, String>();
                if (!(steps.isEmpty() || steps == null)) {
                    for (StepsTable temp : steps) {
                        entryList.put("ID", String.valueOf(temp.getId()));
                        entryList.put("TIME", temp.getTime());
                        entryList.put("STEPS", String.valueOf(temp.getSteps()));
                    }
                    stepListArray.add(entryList);
                    return null;
                } else
                    return null;

            }

            @Override
            protected void onPostExecute(Void v) {
                showList();
            }
        }
        public void updateReport() {
            GetStepsTakenDB getStepsTakenDB = new GetStepsTakenDB();
            getStepsTakenDB.execute();
            GetConsDataAT getConsDataAT = new GetConsDataAT();
            getConsDataAT.execute();
        }
    private class GetConsDataAT extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String[] result = new String[3];
            SharedPreferences spUserData = getContext().getSharedPreferences("User_File", Context.MODE_PRIVATE);
            Integer userId = spUserData.getInt("user_id", 0);
            String curr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            result[0] = RestClient.getUserTotalCalsConsumed(userId,curr_date);
            result[1] = RestClient.getUserCalsBurnedPerStep(userId);
            result[2] = RestClient.getUserCalsBurntAtRest(userId);
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {
            calsConsumed = Integer.parseInt(result[0]);
            Double calsBurnedPerStep = totalStepsTaken*Double.parseDouble(result[1]);
            totalBurnt = Integer.parseInt(String.valueOf(calsBurnedPerStep)) + Integer.parseInt(result[2]);
            System.out.println(totalBurnt);
        }
    }
    private class GetStepsTakenDB extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            totalStepsTaken = db.stepsDAO().findStepsTaken();
            return null;
        }
    }

}