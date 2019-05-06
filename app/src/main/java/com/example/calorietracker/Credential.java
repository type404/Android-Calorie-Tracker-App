package com.example.calorietracker;

import java.util.Date;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Credential {


        // Java program to calculate MD5 hash value
        public static String getMd5(String input)
        {
            try {

                // Static getInstance method is called with hashing MD5
                MessageDigest md = MessageDigest.getInstance("MD5");

                // digest() method is called to calculate message digest
                //  of an input digest() return array of byte
                byte[] messageDigest = md.digest(input.getBytes());

                // Convert byte array into signum representation
                BigInteger no = new BigInteger(1, messageDigest);

                // Convert message digest into hex value
                String hashtext = no.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                return hashtext;
            }

            // For specifying wrong message digest algorithms
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        public static String passHashConverter(String password){
            return getMd5(password);
        }

    public static void checkCredential(String username) throws JSONException {
        String snippet;
        JSONObject jsonObject = new JSONObject(username);
        String tmpUser = (String) jsonObject.get("username");
        String tmpPw = (String) jsonObject.get("passwordHash");
    }


}

