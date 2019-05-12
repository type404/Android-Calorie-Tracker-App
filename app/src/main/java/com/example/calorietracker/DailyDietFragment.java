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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyDietFragment extends Fragment {
    View vDailyDiet;
    Spinner vFoodCategories;
    Spinner vFoodItems;
    String spinnerItemValue;
    String spinnerItemValueFood;
    Button buttonCreateFood;
    Button buttonAddFood;
    String newFood;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDailyDiet = inflater.inflate(R.layout.fragment_daily_diet, container, false);
        vFoodCategories = vDailyDiet.findViewById(R.id.spinnerFoodCategories);
        vFoodItems = vDailyDiet.findViewById(R.id.spinnerFoodItems);
        buttonCreateFood = vDailyDiet.findViewById(R.id.buttonCreateFood);
        buttonAddFood = vDailyDiet.findViewById(R.id.buttonAddFood);
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
        buttonCreateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFood = ((EditText) vDailyDiet.findViewById(R.id.findNewFood)).getText().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(newFood);
                FoodInfoAsyncTask foodInfoAsyncTask=new FoodInfoAsyncTask();
                foodInfoAsyncTask.execute(newFood);
            }
        });
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateConsAsyncTask uaf = new UpdateConsAsyncTask();
                uaf.execute();
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
            List<String> list = new ArrayList<>();
            for(int i = 0; i < foodItem.length; i++){
                String itemname = foodItem[i].getFoodName();
                list.add(itemname);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(vDailyDiet.getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            vFoodItems.setAdapter(adapter);
            vFoodItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerItemValueFood = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            }
        }
    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return searchAPI.searchDescription(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) vDailyDiet.findViewById(R.id.txtFoodDesc);
            String desc = "Food Desc: " + (searchAPI.getSnippet(result));
            String imageURL = searchAPI.getImageURL(result);
            ImageView iv = (ImageView) vDailyDiet.findViewById(R.id.imageFood);
            Picasso.with(vDailyDiet.getContext()).load(imageURL).into(iv);
            int i = desc.indexOf(".");
            desc = desc.substring(0,i);
            tv.setText(desc+".");
        }
    }
    private class FoodInfoAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return FoodSearchAPI.searchFood(params[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tvCal= (TextView) vDailyDiet.findViewById(R.id.txtFoodCalAmount);
            TextView tvFat= (TextView) vDailyDiet.findViewById(R.id.txtFatAmount);
            String[] foodData = FoodSearchAPI.getFoodCalsAndFat(result);
            tvCal.setText("Calories: "+foodData[0]);
            tvFat.setText("Fat Amount: "+foodData[1]);
        }
    }
    private class UpdateConsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.getFoodItems(spinnerItemValue);
        }
        @Override
        protected void onPostExecute(String json) {
            Gson gson = new GsonBuilder().create();
            Food[] foodItem = gson.fromJson(json, Food[].class);
            String etQty = ((EditText) vDailyDiet.findViewById(R.id.foodQty)).getText().toString();
                Integer foodId = foodItem[0].getFoodId();
                Integer userId = Homescreen.getCurrUserId();
                Integer consId = 120;
                String curr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
            }
        }
}
