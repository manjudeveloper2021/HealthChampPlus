package com.healthplus.healthchamp.Fragment;

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

public class CounselorProfileFragment extends Fragment {
TextView txtavailable;
    ProgressDialog dialog;
    CircleImageView profile_image;
    ImageView back;
    TextView txtqualification,txtabout,txtname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counselor_profile, container, false);
        txtavailable=view.findViewById(R.id.txtavailable);
        txtname=view.findViewById(R.id.txtname);
        back=view.findViewById(R.id.back);
        txtqualification=view.findViewById(R.id.txtqualification);
        txtabout=view.findViewById(R.id.txtabout);
        profile_image=view.findViewById(R.id.profile_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounselorListFragment f1 = new CounselorListFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        txtavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounselorAvailability f1 = new CounselorAvailability();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        getProfile();
        return view;
    }
    public void getProfile() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String counsellorid = mypref1.getString("counselloriduser", "");
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
                                txtqualification.setText(jsonobject.getString("qualification")+" | "+jsonobject.getString("specialization"));
                                txtabout.setText(jsonobject.getString("about"));
                                if (jsonobject.getString("profilePicURL").equals("")||jsonobject.getString("profilePicURL").equals("null")||jsonobject.getString("profilePicURL").equals(null)) {

                                    Picasso.with(getActivity())
                                            .load(R.drawable.docmale)
                                            .into(profile_image);
                                }
                                else{
                                    Picasso.with(getActivity())
                                            .load("http://apischoolhealth.litchi.co.in/ProfileImage/"+jsonobject.getString("profilePicURL"))
                                            .into(profile_image);
                                }
                                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor prefsEditr1 = mypref1.edit();
                                prefsEditr1.putString("counsellorprofileid", id);
                                prefsEditr1.commit();
                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
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