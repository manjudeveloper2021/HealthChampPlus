package com.healthplus.healthchamp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.healthplus.healthchamp.Adapter.GetBookingScheduleInCounsellor;
import com.healthplus.healthchamp.Adapter.GetCounsellorSetAvaialability;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingInCounsellor extends AppCompatActivity {
RecyclerView recyclerview;
    List<HealthChampPlusData> data = new ArrayList<>();
    ProgressDialog dialog;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbooking_incounsellor_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_800));
        }

        recyclerview=findViewById(R.id.recyclerview);
        session = new SessionManager(ViewBookingInCounsellor.this);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(ViewBookingInCounsellor.this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(horizontalLayoutManager1);
        data = new ArrayList<>();
        GetCounsellingRecords();
    }
    private void GetCounsellingRecords() {
        dialog = new ProgressDialog(ViewBookingInCounsellor.this);
        dialog.setMessage("please wait...");
        dialog.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(ViewBookingInCounsellor.this);
        String userid = mypref1.getString("userid","");
        String url = AppConstants.Base_URL+"GetCounsellingRecordsByCounsellorID?CounsellorId="+userid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    data.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                        statedata.setId(jsonobject.getString("id"));
                        statedata.setName(jsonobject.getString("name"));
                        statedata.setExperience(jsonobject.getString("email"));
                        statedata.setContact(jsonobject.getString("mobile"));
                        statedata.setDate(Util.convertDate(jsonobject.getString("createdate")));
                        statedata.setAddress(jsonobject.getString("startTime") + " - " + jsonobject.getString("endtime"));
                        statedata.setDocument(jsonobject.getString("counsellingtype"));
                        statedata.setAdvise(jsonobject.getString("counsellingmode"));
                        data.add(statedata);
                    }
                    GetBookingScheduleInCounsellor adapter = new GetBookingScheduleInCounsellor((ArrayList<HealthChampPlusData>) data, ViewBookingInCounsellor.this);
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ViewBookingInCounsellor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(ViewBookingInCounsellor.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewBookingInCounsellor.this);
        requestQueue.add(stringRequest);
    }
}