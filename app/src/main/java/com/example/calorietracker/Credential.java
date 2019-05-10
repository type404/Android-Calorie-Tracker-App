package com.example.calorietracker;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;


public class Credential {

        private String username;

        private String passwordHash;

        private Date signUpDate;

        private Users userId;

        public Credential() {
        }

        public Credential(String username) {
            this.username = username;
        }

        public Credential(String username, String passwordHash, Date signUpDate, Users user) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.signUpDate = signUpDate;
            this.userId = user;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public void setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
        }

        public Date getSignUpDate() {
            return signUpDate;
        }

        public void setSignUpDate(Date signUpDate) {
            this.signUpDate = signUpDate;
        }

        public Users getUserId() {
            return userId;
        }

        public void setUserId(Users userId) {
            this.userId = userId;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += (username != null ? username.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object object) {
            // TODO: Warning - this method won't work in the case the id fields are not set
            if (!(object instanceof Credential)) {
                return false;
            }
            Credential other = (Credential) object;
            if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "restws.Credential[ username=" + username + " ]";
        }

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

//    public static boolean checkCredential(String username, String password){
//        String user = RestClient.getCredentials(username);
//        String pwHash = passHashConverter(password);
////        JSONObject jsonObject = new JSONObject(user);
////        String tmpUser = jsonObject.getString("username");
////        String tmpPw = jsonObject.getString("passwordHash");
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//        Credential userCredential = gson.fromJson(user,Credential.class);
//            if(pwHash.equals(userCredential.getPasswordHash())){
//            return true;
//        }
//      return false;
//    }


}

