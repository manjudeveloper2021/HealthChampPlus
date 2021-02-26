package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.textfield.TextInputEditText;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProfile extends AppCompatActivity {
TextView txtdetail;
LinearLayout layout_additional,btnsignup,btnskip;
    ProgressDialog progressBar;
    RadioGroup radiogroup;
    RadioButton rbtmale,rbtfemale,rbtother;
    TextInputEditText edtname,edtpincode,edtschoolname,edtdob;
    String genderid;
    int age;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_profile);
        txtdetail=findViewById(R.id.txtdetail);
        edtname=findViewById(R.id.edtname);
        radiogroup=findViewById(R.id.radiogroup);
        rbtmale=findViewById(R.id.rbtmale);
        rbtfemale=findViewById(R.id.rbtfemale);
        rbtother=findViewById(R.id.rbtother);
        edtpincode=findViewById(R.id.edtpincode);
        layout_additional=findViewById(R.id.layout_additional);
        btnskip=findViewById(R.id.btnskip);
        btnsignup=findViewById(R.id.btnsignup);
        edtschoolname=findViewById(R.id.edtschoolname);
        edtdob=findViewById(R.id.edtdob);
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
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(AddProfile.this);
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("addchildregistergenderid", genderid);
                    prefsEditr.commit();
                }

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                calculateAge(myCalendar.getTimeInMillis());
            }

        };
        edtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_additional.setVisibility(View.VISIBLE);
                btnsignup.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_button));
            }
        });
//        edtpincode.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                checkPincode(String.valueOf(s));
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProfile.this, DrawerActivity.class);
                startActivity(intent);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(AddProfile.this);
                String addchildregistergenderid  = mypref1.getString("addchildregistergenderid", "");
                if (addchildregistergenderid.equals(""))
                {
                    Toast.makeText(AddProfile.this, "Please choose gender", Toast.LENGTH_SHORT).show();
                }
                if (edtname.getText().toString().isEmpty() || edtschoolname.getText().toString().isEmpty() || edtdob.getText().toString().isEmpty()){
                    Toast.makeText(AddProfile.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    PostUserRegistrationDetail();
                }
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        if (myCalendar.before(Calendar.DAY_OF_MONTH))
//        {
//            Toast.makeText(AddProfile.this, "You can't apply leave of before date", Toast.LENGTH_SHORT).show();
//        }
//        else{
            edtdob.setText(sdf.format(myCalendar.getTime()));
       // }
    }
    private  int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;

        }
        return age;
    }
    private void PostUserRegistrationDetail() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(AddProfile.this);
        String addchildregistergenderid  = mypref1.getString("addchildregistergenderid", "");
        String registerationid = mypref1.getString("registerationid","");
        String registermobileno = mypref1.getString("registermobileno","");
        String registeremailid = mypref1.getString("registeremailid","");
        String registerpassword = mypref1.getString("registerpassword","");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String regdate= dateFormat.format(date);
        RequestQueue requestQueue = Volley.newRequestQueue(AddProfile.this);
        JSONObject familydoctordetails = new JSONObject();
        try {
            familydoctordetails.put("ID", registerationid);
            familydoctordetails.put("ChildName", edtname.getText().toString());
            familydoctordetails.put("FatherName", "");
            familydoctordetails.put("MotherName", "");
            familydoctordetails.put("DOB", edtdob.getText().toString());
            familydoctordetails.put("Age", age);
            familydoctordetails.put("EmailID", registeremailid);
            familydoctordetails.put("MobileNo", registermobileno);
            familydoctordetails.put("CreateDate", regdate);
            familydoctordetails.put("CreateBy", registerationid);
            familydoctordetails.put("ProfilePic", "");
            familydoctordetails.put("UserName", registeremailid);
            familydoctordetails.put("Password", registerpassword);
            familydoctordetails.put("Status", "1");
            familydoctordetails.put("LoginID", registerationid);
            familydoctordetails.put("GenderID", addchildregistergenderid);
            familydoctordetails.put("CountryID", "1");
            familydoctordetails.put("StateID", "1");
            familydoctordetails.put("DistrictID", "");
            familydoctordetails.put("QualificationID", "");
            familydoctordetails.put("SchoolID", "");
            familydoctordetails.put("ClassID", "");
            familydoctordetails.put("SectionID", "");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
      //  String url = AppConstants.Base_URL+"UserRegistrationDetails/PostUserRegistrationDetail";
        String url = AppConstants.Base_URL+"PostParentChildMaster";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, url, familydoctordetails,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.dismiss();
                            if (!response.equals(""))
                            {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                    String ID = jsonobject.getString("ID");
                                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(AddProfile.this);
                                    SharedPreferences.Editor prefsEditr = mypref.edit();
                                    prefsEditr.putString("addchildprofileid", ID);
                                    prefsEditr.commit();
                                    Intent intent = new Intent(AddProfile.this, DrawerActivity.class);
                                    startActivity(intent);
                                }
                                Toast.makeText(AddProfile.this, "Profile add success", Toast.LENGTH_SHORT).show();


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                error.printStackTrace();
                Toast.makeText(AddProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private void checkPincode(String pincode){
        String url1 = "http://heathchampsapi.litchi.co.in/api/DistrictMasters/GetDistrictMasterByPinCode?PinCode="+pincode+"";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        response = "["+response+"]";
                        JSONArray jsonattendance = new JSONArray(response);
                        for (int i = 0; i < jsonattendance.length(); i++) {
                            JSONObject jsonobject = jsonattendance.getJSONObject(i);
                            String name = jsonobject.getString("Name");
                            String id = jsonobject.getString("ID");
                            edtpincode.setText(name);

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
            RequestQueue requestQueue = Volley.newRequestQueue(AddProfile.this);
            requestQueue.add(stringRequest);
    }
}