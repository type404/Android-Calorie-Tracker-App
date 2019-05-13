package com.example.calorietracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import java.sql.Date;
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
    String servingUnit;
    Integer servingAmount;
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
        list.add("Bread");

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
            servingAmount = foodItem[0].getServingAmount();
            servingUnit = foodItem[0].getServingUnit();
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
            UpdateFoodTableAsyncTask updateFoodTableAsyncTask = new UpdateFoodTableAsyncTask();
            updateFoodTableAsyncTask.execute(foodData[0],foodData[1]);
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
            Integer qty = Integer.parseInt(etQty);
                Integer foodId = foodItem[0].getFoodId();
                SharedPreferences spUserData = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
                Integer userId = spUserData.getInt("user_id",0);
                String consId = RestClient.getCount("consumption");
                Integer cId = 1;
                PostConsAsyncTask pcat = new PostConsAsyncTask();
                pcat.execute(cId,userId,foodId,qty);
            }
        }

    private class PostConsAsyncTask extends AsyncTask<Integer, Void, String>
    {
        @Override
        protected String doInBackground(Integer... params) {
            Users user1;
            Food food1;
            String curr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
            String user = RestClient.getUserByUserId(params[1]);
            String food = RestClient.getFoodByFoodId(params[2]);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            try {
                user1 = gson.fromJson(user, Users.class);
                Log.i("user", String.valueOf(user1));
                food1 = gson.fromJson(food, Food.class);
                Log.d("food", String.valueOf(food1));
            } catch (IllegalStateException | JsonSyntaxException exception){
                return "Update Fail";
            }
            Consumption consumption=new Consumption(params[0],user1,food1, Date.valueOf(curr_date),params[3]);
            RestClient.createConsumption(consumption);
            return "Your consumption updated";
        }
        @Override
        protected void onPostExecute(String response) {
           Toast.makeText(vDailyDiet.getContext(),response,Toast.LENGTH_LONG).show();
        }
    }
    private class UpdateFoodTableAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Food food = new Food(Food.createID(),newFood,spinnerItemValue,Integer.parseInt(params[0]),servingUnit,servingAmount,Integer.parseInt(params[0]));
            RestClient.createFood(food);
              return "New food item was added to the list!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(vDailyDiet.getContext(),response,Toast.LENGTH_LONG).show();
        }
    }
}
