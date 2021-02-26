package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.se.omapi.Session;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtname, edtpassword;
    LinearLayout btnlogin;
    TextView txtsignup;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        session = new SessionManager(LoginActivity.this);
        btnlogin=findViewById(R.id.btnlogin);
        txtsignup=findViewById(R.id.txtsignup);
        edtname=findViewById(R.id.edtname);
        edtpassword=findViewById(R.id.edtpassword);
        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String usertype1 = mypref.getString("roleName", "");
        if (usertype1.equals("Counsellor"))
        {
            Intent intent = new Intent(LoginActivity.this, CounsellorProfile.class);
            startActivity(intent);
        }
        else if (usertype1.equals("Parent"))
        {
            SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor=spreferences.edit();
            editor.remove("timeslotstarttime");
            editor.remove("timeslotendtime");
            editor.apply();
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
            startActivity(intent);
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtname.getText().toString().isEmpty() && edtpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Enter login details", Toast.LENGTH_SHORT).show();
                }
               else if (edtname.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                else if (edtpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginDetail();
                }
            }
        });
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
               startActivity(intent);
            }
        });
    }

    private void loginDetail() {
        String username = edtname.getText().toString();
        String password = edtpassword.getText().toString();
     //   String url = AppConstants.Base_URL + "UserRegistrations/GetAuthentication?UserName="+username+"&Password="+password+"";
        String url = "http://apischoolhealth.litchi.co.in/api/UserManager/GetAuthentication?UserName="+username+"&Password="+password+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = "["+response+"]";
                    JSONArray jsonArrayexamtype = new JSONArray(response);
                    for (int i = 0; i < jsonArrayexamtype.length(); i++) {
                        JSONObject jsonobject = jsonArrayexamtype.getJSONObject(i);
                        String userid = jsonobject.getString("id");
                        String Name = jsonobject.getString("name");
                        String EmailID = jsonobject.getString("email");
                        String UserName = jsonobject.getString("userName");
                        String roleName = jsonobject.getString("roleName");
//                        if (username.equals(UserName)) {
                            if (roleName.equals("Parent")) {
                                String MobileNo = jsonobject.getString("mobileno");
                                String gender = jsonobject.getString("gender");
                                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor prefsEditr = mypref.edit();
                                prefsEditr.putString("parentMobileNo", MobileNo);
                                prefsEditr.putString("parentGender", gender);
                                prefsEditr.commit();
                                Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                                startActivity(intent);
                            }
                            if (roleName.equals("Counsellor")) {
                                String counsellingtype = jsonobject.getString("counsellortype");
                                String counsellortypeid = jsonobject.getString("counsellortypeid");
                                String mobile = jsonobject.getString("mobile");
                                String about = jsonobject.getString("about");
                                String specialization = jsonobject.getString("specialization");
                                Intent intent = new Intent(LoginActivity.this, CounsellorProfile.class);
                                startActivity(intent);
                                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor prefsEditr = mypref.edit();
                                prefsEditr.putString("counsellortypeid", counsellortypeid);
                                prefsEditr.putString("counsellorabout", about);
                                prefsEditr.putString("counsellormobile", mobile);
                                prefsEditr.putString("counsellorspecialization", specialization);
                                prefsEditr.putString("counsellingtype", counsellingtype);
                                prefsEditr.commit();
                            }
                       // }
//                        else{
//                            Toast.makeText(LoginActivity.this, "Please enter correct details", Toast.LENGTH_SHORT).show();
//                        }
                        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor prefsEditr = mypref.edit();
                        prefsEditr.putString("Name", Name);
                        prefsEditr.putString("EmailID", EmailID);
                        prefsEditr.putString("UserName", UserName);
                        prefsEditr.putString("roleName", roleName);
                        prefsEditr.putString("userid", userid);
                        prefsEditr.commit();

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
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit this app ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //  finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}