package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class VerificationActivity extends AppCompatActivity {
LinearLayout btnverify;
ProgressDialog progressBar;
PinView firstPinView;
TextView txtresend;
ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification);
        btnverify=findViewById(R.id.btnverify);
        txtresend=findViewById(R.id.txtresend);
        imgback=findViewById(R.id.imgback);
        firstPinView = findViewById(R.id.firstPinView);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(VerificationActivity.this);
                String registerotpcode  = mypref1.getString("registerotpcode", "");
                String pinview = firstPinView.getText().toString();
              if (firstPinView.equals(registerotpcode) || firstPinView.getText().toString().equals(registerotpcode) || registerotpcode.equals(pinview))
              {
                   postUserRegistration();
              }
              else{
                  Toast.makeText(VerificationActivity.this, "OTP not matched", Toast.LENGTH_SHORT).show();
              }
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        txtresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reendPostOTP();
            }
        });
    }

    private void postUserRegistration() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(VerificationActivity.this);
        String registername  = mypref1.getString("registername", "");
        String registermobileno = mypref1.getString("registermobileno","");
        String registeremailid = mypref1.getString("registeremailid","");
        String registerotpcode = mypref1.getString("registerotpcode","");
        String registergenderid = mypref1.getString("parentregistergenderid","");
        String registerpassword = mypref1.getString("registerpassword","");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String regdate= dateFormat.format(date);
        RequestQueue requestQueue = Volley.newRequestQueue(VerificationActivity.this);
        JSONObject familydoctordetails = new JSONObject();
        try {

            familydoctordetails.put("UserName", registeremailid);
            familydoctordetails.put("Name", registername);
            familydoctordetails.put("EmailID", registeremailid);
            familydoctordetails.put("MobileNo", registermobileno);
            familydoctordetails.put("Password", registerpassword);
            familydoctordetails.put("EmailOTP", registerotpcode);
            familydoctordetails.put("MobileOTP", registerotpcode);
            familydoctordetails.put("Status", 1);
            familydoctordetails.put("UserExpInDays", "30");
            familydoctordetails.put("PasswordExpInDays", "30");
            familydoctordetails.put("LoginFlag", "0");
            familydoctordetails.put("CreatedDate", regdate);
            familydoctordetails.put("UpdatedDate", "");
            familydoctordetails.put("RoleID", "0");
            familydoctordetails.put("SessionID", 1);
            familydoctordetails.put("GenderID",registergenderid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstants.Base_URL+"PostParent";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, url, familydoctordetails,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.dismiss();

                          if (!response.equals(""))
                          {
                             // JSONArray jsonArrayexamtype = new JSONArray(response);
                              for (int i = 0; i < response.length(); i++) {
                                  JSONObject jsonobject = new JSONObject(String.valueOf(response));
                              //   JSONObject jsonobject = response.getJSONObject(String.valueOf(response));
                                  String ID = jsonobject.getString("ID");
                                  SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(VerificationActivity.this);
                                  SharedPreferences.Editor prefsEditr = mypref.edit();
                                  prefsEditr.putString("registerationid", ID);
                                  prefsEditr.commit();
                                  Intent intent = new Intent(VerificationActivity.this, AddProfile.class);
                                  startActivity(intent);
                              }
                              Toast.makeText(VerificationActivity.this, "Registered Success", Toast.LENGTH_SHORT).show();


                          }
                        } catch (Exception e) {
                            Toast.makeText(VerificationActivity.this, "Registered Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(VerificationActivity.this, AddProfile.class);
//                            startActivity(intent);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                error.printStackTrace();
                Toast.makeText(VerificationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void reendPostOTP() {
        Random r = new Random( System.currentTimeMillis());
        String otpcode = String.valueOf(10000 + r.nextInt(20000));
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(VerificationActivity.this);
        String registername  = mypref1.getString("registername", "");
        String registermobileno = mypref1.getString("registermobileno","");
        String registeremailid = mypref1.getString("registeremailid","");
        String registerotpcode = mypref1.getString("registerotpcode","");
        String url = AppConstants.Base_URL+"PostOTP?MobileNo="+registermobileno+"&OTP="+otpcode+"&EmailID="+registeremailid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayexamtype = new JSONArray(response);
                    for (int i = 0; i < jsonArrayexamtype.length(); i++) {
                        JSONObject jsonobject = jsonArrayexamtype.getJSONObject(i);
                        String status = jsonobject.getString("Status");
                        if (status.equals("Ok"));
                        {
                            Toast.makeText(VerificationActivity.this, "OTP resend success", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(VerificationActivity.this);
        requestQueue.add(stringRequest);
    }
}