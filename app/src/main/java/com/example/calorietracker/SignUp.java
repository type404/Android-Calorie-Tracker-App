package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView dobView;
    private TextView loginLnk;
    private String SpinnerItemValue;
    private String tmpFName;
    private String tmpLName;
    private String tmpEmail;
    private String tmpUName;
    private String tmpPwdHash;
    private String tmpHeight;
    private String tmpWeight;
    private RadioGroup rg;
    private RadioButton tmpRadio;
    private String tmpGender;
    private String tmpSPM;
    private String tmpLOA;
    private String tmpPAddress;
    private String tmpPostcode;
    String dobDate;
    Spinner activityLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dobView = findViewById(R.id.dob);
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpFName = ((EditText) findViewById(R.id.firstName)).getText().toString();
                tmpLName = ((EditText) findViewById(R.id.lastName)).getText().toString();
                tmpUName = ((EditText) findViewById(R.id.create_username)).getText().toString();
                String tmpPwd = ((EditText) findViewById(R.id.input_password)).getText().toString();
               /* if(isPassword(tmpPwd)) {*/
                    tmpPwdHash = Credential.passHashConverter(tmpPwd);
          /*      } else {
                    ((EditText) findViewById(R.id.input_password)).setError("Re-enter password!");
                }*/
                tmpEmail = ((EditText) findViewById(R.id.emailAddress)).getText().toString();
                tmpHeight = ((EditText) findViewById(R.id.height)).getText().toString();
                tmpWeight = ((EditText) findViewById(R.id.weight)).getText().toString();
                rg = (RadioGroup)findViewById(R.id.radio_grp);
                int selectedId = rg.getCheckedRadioButtonId();
                tmpRadio = (RadioButton) findViewById(selectedId);
                tmpGender = tmpRadio.getText().toString();
                tmpSPM = ((EditText) findViewById(R.id.stepsPerMile)).getText().toString();
                tmpPAddress = ((EditText) findViewById(R.id.address)).getText().toString();
                tmpPostcode = ((EditText) findViewById(R.id.postcode)).getText().toString();
                String sinup_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
                PostAsyncTask postAsyncTask=new PostAsyncTask();
                if (!(tmpFName).isEmpty() && !(tmpLName).isEmpty() && !(tmpEmail).isEmpty() && !(dobDate).isEmpty() && !(tmpHeight).isEmpty()
                        && !(tmpWeight).isEmpty() && !(tmpGender).isEmpty() && !(tmpPAddress).isEmpty() && !(tmpPostcode).isEmpty()
                        && !(SpinnerItemValue).isEmpty() && !(tmpSPM).isEmpty()) {

                    postAsyncTask.execute(tmpFName,tmpLName,tmpEmail,dobDate,tmpHeight,tmpWeight,tmpGender,tmpPAddress,tmpPostcode,SpinnerItemValue,tmpSPM, tmpUName, tmpPwdHash, sinup_date);
                }
            }
        });

        loginLnk = findViewById(R.id.link_login);
        findViewById(R.id.btn_dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        /*Spinner for activity levels*/
        activityLevel = findViewById(R.id.spinnerActivityLevel);
        List<String> list = new ArrayList<>();
        list.add("Choose Activity Level");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        activityLevel.setAdapter(adapter);

        activityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItemValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loginLnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    /*Posts data into sign up and credential table*/
    private class PostAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String id = RestClient.getMaxId("users");
            Integer uId = Integer.parseInt(id) + 1;
            Users user=new Users(uId,params[0],params[1],params[2], Date.valueOf(params[3]),Integer.valueOf(params[4]),Integer.valueOf(params[5]),Character.valueOf(params[6].charAt(0)),params[7],params[8],Integer.valueOf(params[9]),Integer.valueOf(params[10]));
            RestClient.createUsers(user);
            Credential cred = new Credential(params[11],params[12],Date.valueOf(params[13]),user);
            RestClient.createUserCredential(cred);
            return "User was added!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        dobDate = year+"-"+month+"-"+dayOfMonth;
        dobView = findViewById(R.id.dob);
        dobView.setText(dobDate);
    }
    public static boolean isPassword(String string){
        String regEx = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}
