package com.healthplus.healthchamp.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
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
import com.healthplus.healthchamp.Adapter.GetCounsellorAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CounselorListFragment extends Fragment {
TextView txtconsult;
RecyclerView recyclerview;
    ProgressDialog dialog;
    ImageView back;
    List<HealthChampPlusData> docdata = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counselors_list, container, false);

        recyclerview=view.findViewById(R.id.recyclerview);
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpertServiceFragment f1 = new ExpertServiceFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(horizontalLayoutManager1);
        docdata = new ArrayList<>();
        GetCounsellor();
        return view;
    }
    private void GetCounsellor() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String counsellingtypeiduser = mypref1.getString("counsellingtypeiduser", "");
        String url = AppConstants.Base_URL+"GetCounsellor?CounsellingTypeID="+counsellingtypeiduser+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    docdata.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                        statedata.setId(jsonobject.getString("ID"));
                        statedata.setName(jsonobject.getString("Name"));
                        statedata.setQualify(jsonobject.getString("Specialization"));
                        statedata.setAddress(jsonobject.getString("Address"));
                        statedata.setDocument(jsonobject.getString("ProfilePicURL"));
                        docdata.add(statedata);
                    }
                    GetCounsellorAdapter adapter = new GetCounsellorAdapter((ArrayList<HealthChampPlusData>) docdata, getActivity());
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
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