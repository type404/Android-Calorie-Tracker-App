package com.example.calorietracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FoodSearchAPI {
    private static final String APP_ID = "4b3c3647";
    private static final String APK_KEY = "cd1f9e1edbda1e4c1c78ee4815173560";
//to check JSON OBJECT https://api.edamam.com/api/food-database/parser?ingr=pizza&app_id=4b3c3647&app_key=cd1f9e1edbda1e4c1c78ee4815173560"
    public static String searchFood(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://api.edamam.com/api/food-database/parser?ingr=" +
                    keyword + "&app_id=" + APP_ID + "&app_key=" + APK_KEY );
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }
    public static String[] getFoodCalsAndFat(String result){
        String[] snippet = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("parsed");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0).getJSONObject("food").getJSONObject("nutrients");
            String snippetCal = jsonObject1.getString("ENERC_KCAL");
            String snippetFat = jsonObject1.getString("FAT");
            snippet[0] = snippetCal;
            snippet[1] = snippetFat;
        }catch (Exception e){
            e.printStackTrace();
        }
        return snippet;
    }
}
