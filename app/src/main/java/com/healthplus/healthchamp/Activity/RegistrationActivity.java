package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
LinearLayout btnsignup;
TextInputEditText edtname,edtmobile,edtmail,edt_password;
RadioGroup radiogroup;
RadioButton rbtmale,rbtfemale,rbtother;
String genderid;
TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        txtlogin=findViewById(R.id.txtlogin);
        rbtother=findViewById(R.id.rbtother);
        edt_password=findViewById(R.id.edt_password);
        btnsignup=findViewById(R.id.btnsignup);
        radiogroup=findViewById(R.id.radiogroup);
        rbtmale=findViewById(R.id.rbtmale);
        rbtfemale=findViewById(R.id.rbtfemale);
        edtname=findViewById(R.id.edtname);
        edtmobile=findViewById(R.id.edtmobile);
        edtmail=findViewById(R.id.edtmail);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radiogroup.getCheckedRadioButtonId() != -1) {
                    int id = radiogroup.getCheckedRadioButtonId();
                    View radioButton = radiogroup.findViewById(id);
                    int radioId = radiogroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radiogroup.getChildAt(radioId);
                    String radio = (String) btn.getText();
                    if (radio.equals("Male")) {
                        radio = "Male";
                        genderid= "1";
                    }
                    if (radio.equals("Female")) {
                        radio = "Female";
                        genderid= "2";
                    }
                    if (radio.equals("Other")) {
                        radio = "Other";
                        genderid= "3";
                    }
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("parentregistergenderid", genderid);
                    prefsEditr.commit();
                }

            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileno = edtmobile.getText().toString();
                String emailid = edtmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String url = "http://apischoolhealth.litchi.co.in/api/UserManager/CheckDuplicateUser?EmailID="+emailid+"";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                                if (response.equals("") || response.equals("null") || response.equals(null)) {

                                    SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                                    String registergenderid  = mypref1.getString("parentregistergenderid", "");
                                    if (registergenderid.equals(""))
                                    {
                                        Toast.makeText(RegistrationActivity.this, "Please choose gender", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (edtmobile.getText().toString().length() < 10){
                                        Toast.makeText(RegistrationActivity.this, "Please enter correct mobile no.", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (!emailid.matches(emailPattern))
                                    {
                                        Toast.makeText(RegistrationActivity.this, "Please enter correct email-id", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        PostOTP();
                                    }
                                }
                                else{
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                     //   String userName = jsonobject.getString("userName");
                                        String email = jsonobject.getString("email");
                                        if (email.equals(email))
                                        {
                                            Toast.makeText(RegistrationActivity.this, "Already exist user. Please login", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }

    private void PostOTP() {
        if (edtmobile.getText().toString().isEmpty() || edtmail.getText().toString().isEmpty() || edtname.getText().toString().isEmpty() || edt_password.getText().toString().isEmpty()){
            Toast.makeText(RegistrationActivity.this, "Please fill details", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
            Random r = new Random(System.currentTimeMillis());
            String otpcode = String.valueOf(10000 + r.nextInt(20000));
            String mobileno = edtmobile.getText().toString();
            String emailid = edtmail.getText().toString();
            String password = edt_password.getText().toString();
            String url = AppConstants.Base_URL + "PostOTP?MobileNo=" + mobileno + "&OTP=" + otpcode + "&EmailID=" + emailid + "";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArrayexamtype = new JSONArray(response);
                        for (int i = 0; i < jsonArrayexamtype.length(); i++) {
                            JSONObject jsonobject = jsonArrayexamtype.getJSONObject(i);
                            String status = jsonobject.getString("Status");
                            if (status.equals("Ok")) ;
                            {
                                Toast.makeText(RegistrationActivity.this, "OTP send success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, VerificationActivity.class);
                                startActivity(intent);
                                String paretname = edtname.getText().toString();
                                String mobileno = edtmobile.getText().toString();
                                String emailid = edtmail.getText().toString();
                                String password = edt_password.getText().toString();
                                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                                SharedPreferences.Editor prefsEditr = mypref.edit();
                                prefsEditr.putString("registername", paretname);
                                prefsEditr.putString("registermobileno", mobileno);
                                prefsEditr.putString("registeremailid", emailid);
                                prefsEditr.putString("registerpassword", password);
                                prefsEditr.putString("registerotpcode", otpcode);
                                prefsEditr.commit();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
            requestQueue.add(stringRequest);
        }
    }
}