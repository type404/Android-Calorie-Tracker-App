package com.example.calorietracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class DailyDietFragment extends Fragment {
    View vDailyDiet;
    Spinner vFoodCategories;
    String spinnerItemValue;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDailyDiet = inflater.inflate(R.layout.fragment_daily_diet, container, false);
        vFoodCategories = vDailyDiet.findViewById(R.id.spinnerFoodCategories);
        List<String> list = new ArrayList<>();
        list.add("Cereals");
        list.add("Dessert");
        list.add("Dressings");
        list.add("Fruit");
        list.add("Legumes");
        list.add("Nuts");
        list.add("Poultry");
        list.add("Vegetables");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(vDailyDiet.getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        vFoodCategories.setAdapter(adapter);

        vFoodCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerItemValue = parent.getItemAtPosition(position).toString();
                GetFoodItemsAsyncTask gf = new GetFoodItemsAsyncTask();
                gf.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return vDailyDiet;
    }
    private class GetFoodItemsAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            return RestClient.getFoodItems(spinnerItemValue);
        }

        @Override
        protected void onPostExecute(String json) {
            Gson gson = new GsonBuilder().create();
            Food[] foodItem = gson.fromJson(json, Food[].class);
            foodItem[0].getFoodName();
            foodItem[1].getFoodName();
            }
        }

}
