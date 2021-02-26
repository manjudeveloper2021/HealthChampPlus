package com.healthplus.healthchamp.ui.slideshow;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Activity.Util;
import com.healthplus.healthchamp.Adapter.GetBookingScheduleInCounsellor;
import com.healthplus.healthchamp.Adapter.GetBookingScheduleInParent;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerview;
    List<HealthChampPlusData> data = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
             //   textView.setText(s);
            }
        });
        recyclerview=root.findViewById(R.id.recyclerview);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(horizontalLayoutManager1);
        data = new ArrayList<>();
        GetCounsellingRecords();
        return root;
    }

    private void GetCounsellingRecords() {
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userid = mypref1.getString("userid","");
        String url = AppConstants.Base_URL+"GetCounsellingRecords?LoginID="+userid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    data.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                        statedata.setId(jsonobject.getString("id"));
                        statedata.setName(jsonobject.getString("counsellorName"));
                        statedata.setExperience(jsonobject.getString("email"));
                        statedata.setContact(jsonobject.getString("mobile"));
                        statedata.setDate(Util.convertDate(jsonobject.getString("createdate")));
                        statedata.setAddress(jsonobject.getString("startTime") + " - " + jsonobject.getString("endtime"));
                        statedata.setDocument(jsonobject.getString("counsellingtype"));
                        statedata.setBehave(jsonobject.getString("counsellingmode"));
                        data.add(statedata);
                    }
                    GetBookingScheduleInParent adapter = new GetBookingScheduleInParent((ArrayList<HealthChampPlusData>) data, getActivity());
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}