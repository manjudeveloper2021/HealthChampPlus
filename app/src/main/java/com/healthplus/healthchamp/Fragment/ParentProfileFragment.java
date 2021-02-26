package com.healthplus.healthchamp.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.healthplus.healthchamp.Activity.AddProfile;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Activity.DrawerActivity;
import com.healthplus.healthchamp.Activity.LoginActivity;
import com.healthplus.healthchamp.Activity.Util;
import com.healthplus.healthchamp.Activity.ViewBookingInCounsellor;
import com.healthplus.healthchamp.Adapter.GetBookingScheduleInCounsellor;
import com.healthplus.healthchamp.Adapter.GetParentChildDetailsAdapter;
import com.healthplus.healthchamp.Adapter.GetPromocodeOfferAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParentProfileFragment extends Fragment {
TextView txtaddchild,txtname,txtmobile,txtemail,txtviewprofile,txtpchildname,txtchilddob,txtchildschool,txtaddress;
LinearLayout layout_addchild,layout_fullprofile;
    List<HealthChampPlusData> childdata = new ArrayList<>();
    RadioGroup radiogroup;
    RecyclerView recyclerview_child;
    RadioButton rbtmale,rbtfemale,rbtother;
    TextInputEditText edtname,edtpincode,edtschoolname,edtdob;
    String genderid;
    ProgressDialog progressBar;
    final Calendar myCalendar1 = Calendar.getInstance();
    Button btnupdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parentprofile, container, false);
        txtaddchild = view.findViewById(R.id.txtaddchild);
        txtchilddob=view.findViewById(R.id.txtchilddob);
        txtaddress=view.findViewById(R.id.txtaddress);
        txtchildschool=view.findViewById(R.id.txtchildschool);
        txtpchildname=view.findViewById(R.id.txtpchildname);
        txtviewprofile=view.findViewById(R.id.txtviewprofile);
        layout_fullprofile=view.findViewById(R.id.layout_fullprofile);
        btnupdate=view.findViewById(R.id.btnupdate);
        txtname=view.findViewById(R.id.txtname);
        radiogroup=view.findViewById(R.id.radiogroup);
        rbtmale=view.findViewById(R.id.rbtmale);
        rbtfemale=view.findViewById(R.id.rbtfemale);
        edtschoolname=view.findViewById(R.id.edtschoolname);
        edtdob=view.findViewById(R.id.edtdob);
        edtpincode=view.findViewById(R.id.edtpincode);
        edtname=view.findViewById(R.id.edtname);
        rbtother=view.findViewById(R.id.rbtother);
        txtmobile=view.findViewById(R.id.txtmobile);
        txtemail=view.findViewById(R.id.txtemail);
        recyclerview_child=view.findViewById(R.id.recyclerview_child);
        layout_addchild=view.findViewById(R.id.layout_addchild);
        txtaddchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_addchild.setVisibility(View.VISIBLE);
                layout_fullprofile.setVisibility(View.GONE);
                btnupdate.setVisibility(View.VISIBLE);
            }
        });
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview_child.setLayoutManager(horizontalLayoutManager1);
        childdata = new ArrayList<>();
        GetChildDetails();
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        txtviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  GetParentChildMaster();
                layout_fullprofile.setVisibility(View.VISIBLE);
                layout_addchild.setVisibility(View.GONE);
                btnupdate.setVisibility(View.GONE);
            }
        });
        edtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(getActivity(), date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),  myCalendar1.get(Calendar.DAY_OF_MONTH));
                mDate.show();

            }
        });
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String Name = mypref1.getString("Name","");
        txtname.setText(Name);
        String EmailID = mypref1.getString("EmailID","");
        txtemail.setText(EmailID);
        String MobileNo = mypref1.getString("parentMobileNo","");
        txtmobile.setText(MobileNo);
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
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("profileaddchildregistergenderid", genderid);
                    prefsEditr.commit();
                }
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String addchildregistergenderid  = mypref1.getString("profileaddchildregistergenderid", "");
                if (addchildregistergenderid.equals(""))
                {
                    Toast.makeText(getActivity(), "Please choose gender", Toast.LENGTH_SHORT).show();
                }
                if (edtname.getText().toString().isEmpty() || edtschoolname.getText().toString().isEmpty() || edtdob.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    PostUserRegistrationDetail();
                }

            }
        });
        return view;
    }
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        if (myCalendar1.before(Calendar.DAY_OF_MONTH))
//        {
//            Toast.makeText(getActivity(), "You can't apply date before today", Toast.LENGTH_SHORT).show();
//        }
            edtdob.setText(sdf.format(myCalendar1.getTime()));

    }
    private void PostUserRegistrationDetail() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String addchildregistergenderid  = mypref1.getString("profileaddchildregistergenderid", "");
        String userid = mypref1.getString("userid","");
        String EmailID = mypref1.getString("EmailID","");
        String MobileNo = mypref1.getString("parentMobileNo","");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String regdate= dateFormat.format(date);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject familydoctordetails = new JSONObject();
        try {
            familydoctordetails.put("ID", userid);
            familydoctordetails.put("ChildName", edtname.getText().toString());
            familydoctordetails.put("FatherName", "");
            familydoctordetails.put("MotherName", "");
            familydoctordetails.put("DOB", edtdob.getText().toString());
            familydoctordetails.put("Age", "");
            familydoctordetails.put("EmailID", EmailID);
            familydoctordetails.put("MobileNo", MobileNo);
            familydoctordetails.put("CreateDate", regdate);
            familydoctordetails.put("CreateBy", userid);
            familydoctordetails.put("ProfilePic", "");
            familydoctordetails.put("UserName", EmailID);
            familydoctordetails.put("Password", "");
            familydoctordetails.put("Status", "1");
            familydoctordetails.put("LoginID", userid);
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
                                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor prefsEditr = mypref.edit();
                                    prefsEditr.putString("addchildprofileid", ID);
                                    prefsEditr.commit();
                                    edtname.getText().clear();
                                   edtdob.getText().clear();
                                   edtschoolname.getText().clear();
                                   edtpincode.getText().clear();
                                   layout_addchild.setVisibility(View.GONE);
                                    btnupdate.setVisibility(View.GONE);
                                    layout_fullprofile.setVisibility(View.GONE);
                                }
                                Toast.makeText(getActivity(), "Child details add success", Toast.LENGTH_SHORT).show();


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
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void GetChildDetails() {
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userid  = mypref1.getString("userid", "");
        String url = AppConstants.Base_URL+"GetParentChild?LoginID="+userid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("")||response.equals(null)||response.equals("null"))
                    {
                        Toast.makeText(getActivity(), "No child details", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        childdata.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            HealthChampPlusData statedata = new HealthChampPlusData();
                            statedata.setId(jsonobject.getString("ID"));
                            statedata.setName(jsonobject.getString("ChildName"));
                            statedata.setDate(Util.convertDate(jsonobject.getString("DOB")));
                            //  statedata.setAddress(jsonobject.getString("OfferValue"));
                            childdata.add(statedata);
                        }
                    }
                    GetParentChildDetailsAdapter adapter = new GetParentChildDetailsAdapter((ArrayList<HealthChampPlusData>) childdata, getActivity());
                    recyclerview_child.setAdapter(adapter);
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

    private void GetParentChildMaster() {
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String addchildprofileid = mypref1.getString("addchildprofileid","");
        String url = AppConstants.Base_URL+"GetParentChildMaster/"+addchildprofileid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = "["+response+"]";
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String id =jsonobject.getString("ID");
                        String name =jsonobject.getString("ChildName");
                        txtpchildname.setText(name);
                      //  String school =jsonobject.getString("DOB");
                       // String address =jsonobject.getString("");
                        String dob =Util.convertDate(jsonobject.getString("DOB"));
                        txtchilddob.setText(dob);

                    }

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