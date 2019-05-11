package com.example.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSteps extends Fragment {
    String curr_time;
    Button addStepsButton;
    EditText addSteps;
    View vUserSteps;
    List<HashMap<String, String>> stepListArray;
    SimpleAdapter myListAdapter;
    ListView stepList;
    String[] colHEAD;
    int[] dataCell;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vUserSteps = inflater.inflate(R.layout.fragment_user_steps, container, false);
        addStepsButton = vUserSteps.findViewById(R.id.addStepsButton);
        addSteps = vUserSteps.findViewById(R.id.addSteps);

        HashMap<String,String> map = new HashMap<String,String>();
        colHEAD = new String[] {"TIME","STEPS"};
        dataCell = new int[] {R.id.dateTime,R.id.stepsTaken};
        stepList = vUserSteps.findViewById(R.id.list_view);
        stepListArray = new ArrayList<HashMap<String, String>>();

        addStepsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String newStep = addSteps.getText().toString();
                if(!newStep.isEmpty()) {
                    String[] stepsArray = newStep.split(",");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("TIME", stepsArray[0]);
                    map.put("STEPS", stepsArray[1]);
                    addMap(map);
                } else {
                    addSteps.setError("Empty String");
                    error();
                }
            }

            private void addMap(HashMap<String, String> map) {
                stepListArray.add(map);
                myListAdapter = new SimpleAdapter(vUserSteps.getContext(), stepListArray, R.layout.list_view, colHEAD, dataCell);
                stepList.setAdapter(myListAdapter);
            }
        });
                   return vUserSteps;

    }

    private View error() {
        Toast.makeText(vUserSteps.getContext(), "Enter Valid Steps and Date separated by comma",Toast.LENGTH_LONG).show();
        return vUserSteps;
    }
}