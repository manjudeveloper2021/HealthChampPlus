package com.healthplus.healthchamp.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Adapter.ServiceCategorisDataAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePackageActivity extends Fragment {
   ViewPager viewPager;
   LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    FrameLayout simpleFrameLayout;
    TabLayout simpleTabLayout;
    List<HealthChampPlusData> categorylist = new ArrayList<>();
    RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_service_package, container, false);
        recyclerview=view.findViewById(R.id.recyclerview);
        viewPager=view.findViewById(R.id.viewPager);
        SliderDots=view.findViewById(R.id.SliderDots);
        simpleFrameLayout=view.findViewById(R.id.simpleFrameLayout);
        simpleTabLayout=view.findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = simpleTabLayout.newTab();
        firstTab.setText("Services");
        simpleTabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = simpleTabLayout.newTab();
        secondTab.setText("Events");
    simpleTabLayout.addTab(secondTab);
      //  @SuppressLint("WrongConstant")

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
//        recyclerview.setLayoutManager(mLayoutManager);
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerview.setLayoutManager(horizontalLayoutManager);
//        categorylist = new ArrayList<>();
//        ServiceCategories();
        return view;
    }
    private void ServiceCategories() {
      //  String url = AppConstants.Base_URL+"ServiceCategories/GetServiceCategories";
        String url = AppConstants.Base_URL+"GetServiceCategories";
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
                    ServiceCategorisDataAdapter adapter = new ServiceCategorisDataAdapter((ArrayList<HealthChampPlusData>) categorylist, getActivity());
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