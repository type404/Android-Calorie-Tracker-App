package com.example.calorietracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MapSearchAPI {
    private static final String API_KEY = "iN5OBRLxGG9QryhAk8vmgRswGlBKzRVG";
    public static String searchMap(String address, String postcode) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("http://www.mapquestapi.com/geocoding/v1/address?key="+API_KEY+"&location="+address+postcode);
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
    public static String searchMapRadius(String postcode) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://www.mapquestapi.com/search/v2/radius?origin="+postcode+"&radius=3.10&maxMatches=10&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799951&outFormat=json&key=");
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
    public static String[] getLatLong(String result){
        String[] snippet = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if(jsonArray != null && jsonArray.length() > 0) {
                JSONArray jsonArray1 =jsonArray.getJSONObject(0).getJSONArray("locations");
                JSONObject jsonObject1 = jsonArray1.getJSONObject(0).getJSONObject("latLng");
                snippet[0] = jsonObject1.getString("lat");
                snippet[1] = jsonObject1.getString("lng");

            }
        }catch (Exception e){
            e.printStackTrace();
            snippet[0] = "NO INFO FOUND";
        }
        return snippet;
    }
}