package com.healthplus.healthchamp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.Adapter.GetCounsellorAdapter;
import com.healthplus.healthchamp.Adapter.GetCounsellorSetAvaialability;
import com.healthplus.healthchamp.Adapter.GetTimeSlotDataAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CounsellorDashboard extends AppCompatActivity {
RecyclerView recyclerview,recyclerviewtuesday,recyclerviewwednesday,recyclerviewthursday,recyclerviewfriday,recyclerviewsaturday;
    List<HealthChampPlusData> data = new ArrayList<>();
    List<HealthChampPlusData> datatuesday = new ArrayList<>();
    List<HealthChampPlusData> datawednesday = new ArrayList<>();
    List<HealthChampPlusData> datathursday = new ArrayList<>();
    List<HealthChampPlusData> datafriday = new ArrayList<>();
    List<HealthChampPlusData> datasaturday = new ArrayList<>();
    ProgressDialog dialog;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcounselor_availability_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_800));
        }
        session = new SessionManager(CounsellorDashboard.this);
        recyclerview=findViewById(R.id.recyclerview);
        recyclerviewfriday=findViewById(R.id.recyclerviewfriday);
        recyclerviewsaturday=findViewById(R.id.recyclerviewsaturday);
        recyclerviewthursday=findViewById(R.id.recyclerviewthursday);
        recyclerviewwednesday=findViewById(R.id.recyclerviewwednesday);
        recyclerviewtuesday=findViewById(R.id.recyclerviewtuesday);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(horizontalLayoutManager1);
        data = new ArrayList<>();
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewtuesday.setLayoutManager(horizontalLayoutManager2);
        datatuesday = new ArrayList<>();
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager3 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewwednesday.setLayoutManager(horizontalLayoutManager3);
        datawednesday = new ArrayList<>();
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager4 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewthursday.setLayoutManager(horizontalLayoutManager4);
        datathursday = new ArrayList<>();
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager5 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewfriday.setLayoutManager(horizontalLayoutManager5);
        datafriday = new ArrayList<>();
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager6 = new LinearLayoutManager(CounsellorDashboard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewsaturday.setLayoutManager(horizontalLayoutManager6);
        datasaturday = new ArrayList<>();
        GetCounsellorAvailability();

    }
    private void GetCounsellorAvailability() {
        dialog = new ProgressDialog(CounsellorDashboard.this);
        dialog.setMessage("please wait...");
        dialog.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(CounsellorDashboard.this);
        String userid = mypref1.getString("userid","");
        String url = AppConstants.Base_URL+"GetCounsellorAvailability?CounsellorID="+userid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    data.clear();
                    datatuesday.clear();
                    datawednesday.clear();
                    datathursday.clear();
                    datafriday.clear();
                    datasaturday.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                        String counsellinday = jsonobject.getString("counsellinday");
                        if (counsellinday.equals("MONDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            data.add(statedata);
                        }
                        if (counsellinday.equals("TUESDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            datatuesday.add(statedata);
                        }
                        if (counsellinday.equals("WEDNESDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            datawednesday.add(statedata);
                        }
                        if (counsellinday.equals("THURSDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            datathursday.add(statedata);
                        }
                        if (counsellinday.equals("FRIDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            datafriday.add(statedata);
                        }
                        if (counsellinday.equals("SATURDAY")) {
                            statedata.setId(jsonobject.getString("id"));
                            statedata.setQualify(jsonobject.getString("counsellinday"));
                            statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                            datasaturday.add(statedata);
                        }
                    }
                    GetCounsellorSetAvaialability adapter = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) data, CounsellorDashboard.this);
                    recyclerview.setAdapter(adapter);
                    GetCounsellorSetAvaialability adapter1 = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) datatuesday, CounsellorDashboard.this);
                    recyclerviewtuesday.setAdapter(adapter1);
                    GetCounsellorSetAvaialability adapter2 = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) datawednesday, CounsellorDashboard.this);
                    recyclerviewwednesday.setAdapter(adapter2);
                    GetCounsellorSetAvaialability adapter3 = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) datathursday, CounsellorDashboard.this);
                    recyclerviewthursday.setAdapter(adapter3);
                    GetCounsellorSetAvaialability adapter4 = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) datafriday, CounsellorDashboard.this);
                    recyclerviewfriday.setAdapter(adapter4);
                    GetCounsellorSetAvaialability adapter5 = new GetCounsellorSetAvaialability((ArrayList<HealthChampPlusData>) datasaturday, CounsellorDashboard.this);
                    recyclerviewsaturday.setAdapter(adapter5);

                    }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CounsellorDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(CounsellorDashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CounsellorDashboard.this);
        requestQueue.add(stringRequest);
    }
}