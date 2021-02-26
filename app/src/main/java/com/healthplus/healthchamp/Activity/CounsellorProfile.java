package com.healthplus.healthchamp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class CounsellorProfile extends AppCompatActivity {
LinearLayout layout_set,layout_view,layout_booking;
TextView txtname,txtmobile,txtmail,txtabout,txtlogout,txtqualification;
    ProgressDialog dialog;
    CircleImageView profile_image;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellordashboard);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_800));
        }
        session = new SessionManager(CounsellorProfile.this);
        layout_set=findViewById(R.id.layout_set);
        txtqualification=findViewById(R.id.txtqualification);
        layout_booking=findViewById(R.id.layout_booking);
        layout_view=findViewById(R.id.layout_view);
        profile_image=findViewById(R.id.profile_image);
        txtname=findViewById(R.id.txtname);
        txtabout=findViewById(R.id.txtabout);
        txtmobile=findViewById(R.id.txtmobile);
        txtmail=findViewById(R.id.txtmail);
        txtlogout=findViewById(R.id.txtlogout);
        layout_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorProfile.this, SetCounsellorAvailability.class);
                startActivity(intent);
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref0 = PreferenceManager.getDefaultSharedPreferences(CounsellorProfile.this);
                SharedPreferences.Editor editor = mypref0.edit();
                String roleName = mypref0.getString("roleName","");
                editor.remove("roleName");
                editor.clear();
                editor.commit();
                Intent intent = new Intent(CounsellorProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        layout_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorProfile.this, CounsellorDashboard.class);
                startActivity(intent);
            }
        });
        layout_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorProfile.this, ViewBookingInCounsellor.class);
                startActivity(intent);
            }
        });
        getProfile();
//        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(CounsellorProfile.this);
//        String Name = mypref1.getString("Name","");
//        txtname.setText(Name);
//        String EmailID = mypref1.getString("EmailID","");
//        txtmail.setText(EmailID);
//        String MobileNo = mypref1.getString("counsellormobile","");
//        txtmobile.setText(MobileNo);
//        String about = mypref1.getString("counsellorabout","");
//         txtabout.setText(about);
//        String counsellorspecialization = mypref1.getString("counsellorspecialization","");
//        txtqualification.setText(counsellorspecialization);
    }

    public void getProfile() {
        dialog = new ProgressDialog(CounsellorProfile.this);
        dialog.setMessage("please wait...");
        dialog.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(CounsellorProfile.this);
        String counsellorid = mypref1.getString("userid", "");
        String counsellingtype = mypref1.getString("counsellingtype","");
        String url = "http://apischoolhealth.litchi.co.in/api/UserManager/GetCounsellor?CounsellorID="+counsellorid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            response = "["+response+"]";
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id = jsonobject.getString("id");
                                txtname.setText(jsonobject.getString("name"));
                                txtmobile.setText(jsonobject.getString("mobile"));
                                txtmail.setText(jsonobject.getString("email"));
                                txtqualification.setText(counsellingtype + " | " + jsonobject.getString("qualification")+" | "+jsonobject.getString("specialization"));
                                txtabout.setText(jsonobject.getString("about"));
                                if (jsonobject.getString("profilePicURL").equals("")||jsonobject.getString("profilePicURL").equals("null")||jsonobject.getString("profilePicURL").equals(null)) {

                                    Picasso.with(CounsellorProfile.this)
                                            .load(R.drawable.docmale)
                                            .into(profile_image);
                                }
                                else{
                                    Picasso.with(CounsellorProfile.this)
                                            .load("http://apischoolhealth.litchi.co.in/ProfileImage/"+jsonobject.getString("profilePicURL"))
                                            .into(profile_image);
                                }
//                                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(CounsellorProfile.this);
//                                SharedPreferences.Editor prefsEditr1 = mypref1.edit();
//                                prefsEditr1.putString("counsellorprofileid", id);
//                                prefsEditr1.commit();
                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(CounsellorProfile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(CounsellorProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CounsellorProfile.this);
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