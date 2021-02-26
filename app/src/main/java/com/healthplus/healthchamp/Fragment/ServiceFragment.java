package com.healthplus.healthchamp.Fragment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Adapter.ServiceCategorisDataAdapter;
import com.healthplus.healthchamp.Adapter.ServiceSubCategorisDataAdapter;
import com.healthplus.healthchamp.Adapter.SpinnerStateAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.Model.SpinnerData;
import com.healthplus.healthchamp.R;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceFragment extends Fragment {
LinearLayout layout_bmi;
    List<HealthChampPlusData> categorylist = new ArrayList<>();
    RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_service_fragment, container, false);
        layout_bmi=view.findViewById(R.id.layout_bmi);
        recyclerview=view.findViewById(R.id.recyclerview);
        layout_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMICalculateFragment f1 = new BMICalculateFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f1).commit();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(mLayoutManager);
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerview.setLayoutManager(horizontalLayoutManager);
        categorylist = new ArrayList<>();
        ServiceSubCategories();
        return view;
    }
    private void ServiceSubCategories() {
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String servicecategoryid = mypref1.getString("servicecategoryid", "");
      //  String url = AppConstants.Base_URL+"ServiceSubCategories/GetServiceSubCategoryByCategoryID?CategoryId="+servicecategoryid+"";
        String url = AppConstants.Base_URL+"GetServiceSubCategoryByCategoryID?CategoryId="+servicecategoryid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("msg", response);
                    JSONArray jsonarray = new JSONArray(response);
                    for(int i=0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                        statedata.setName(jsonobject.getString("Name"));
                        statedata.setId(jsonobject.getString("ID"));
                        statedata.setDocument(jsonobject.getString("Icon"));
                        categorylist.add(statedata);
                    }
                    ServiceSubCategorisDataAdapter adapter = new ServiceSubCategorisDataAdapter((ArrayList<HealthChampPlusData>) categorylist, getActivity());
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "*/*");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}