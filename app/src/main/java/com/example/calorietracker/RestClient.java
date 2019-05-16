package com.example.calorietracker;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URL =
            "http://192.168.111.1:8080/CalorieTrackerApp/webresources/";
    //class to get Credential Data by a given username
    public static String getCredentials(String username) {
        final String methodPath = "restws.credential/findByUsername/" + username;
        //initialise
        String s = HTTPConnection(methodPath);
        return s;
    }
    //get Count from the tables
    public static String getMaxId(String tablename) {
        final String methodPath = "restws." + tablename + "/findByMaxId";
        //initialise
        String s = HTTPConnectionText(methodPath);
        return s;
    }
    public static String HTTPConnectionText(String methodPath){
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(150000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to text plain
              conn.setRequestProperty("Content-Type", "text/plain");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
    public static String getUserByUserId(Integer id) {
        final String methodPath = "restws.users/" + id;
        //initialise
        String s = HTTPConnection(methodPath);
        return s;
    }
    //
    public static String getFoodByFoodId(Integer id) {
        final String methodPath = "restws.food/" + id;
        //initialise
        String s = HTTPConnection(methodPath);
        return s;
    }
    //get report from user Id
    public static String getReportFromUserId(Integer id) {
        final String methodPath = "restws.report/findByUserId/" + id;
        //initialise
        String s = HTTPConnection(methodPath);
        return s;
    }
    //get food items by category
    public static String getFoodItems(String category) {
        final String methodPath = "restws.food/findByCategory/" + category;
        //initialise
        String s = HTTPConnection(methodPath);
        return s;
    }
    //method to start a HTTP Get Connection
    public static String HTTPConnection(String methodPath){
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
    //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
    //open the connection
            conn = (HttpURLConnection) url.openConnection();
    //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(150000);
    //set the connection method to GET
            conn.setRequestMethod("GET");
    //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
    //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
    //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
    // method to create new Users
    public static void createUsers(Users user){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restws.users/";
        try {

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson=gson.toJson(user);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
    public static void createUserCredential(Credential cred){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restws.credential/";
        try {

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson=gson.toJson(cred);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
    public static void createConsumption(Consumption consumption){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restws.consumption/";
        try {

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson=gson.toJson(consumption);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
    public static void createFood(Food food){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restws.food/";
        try {

            Gson gson = new Gson();
            String stringUserJson=gson.toJson(food);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
}
