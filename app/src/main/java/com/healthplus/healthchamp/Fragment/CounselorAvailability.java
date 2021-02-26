package com.healthplus.healthchamp.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Activity.CounsellorProfile;
import com.healthplus.healthchamp.Adapter.GetTimeSlotDataAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class CounselorAvailability extends Fragment {
TextView txtconfirm;
RecyclerView recyclerview,recyclerviewtuesday,recyclerviewwednesday,recyclerviewthursday,recyclerviewfriday,recyclerviewsaturday;
EditText edtdate;
ImageView back;
String daynameavailability;
    final Calendar myCalendar1 = Calendar.getInstance();
    private ArrayList<HealthChampPlusData> list_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counselor_availability_layout, container, false);
        txtconfirm=view.findViewById(R.id.txtconfirm);

        back=view.findViewById(R.id.back);
        edtdate=view.findViewById(R.id.edtdate);
        recyclerview=view.findViewById(R.id.recyclerview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounselorProfileFragment f1 = new CounselorProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
                SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=spreferences.edit();
                editor.remove("timeslotstarttime");
                editor.remove("timeslotendtime");
                editor.apply();
                editor.commit();
            }
        });
        txtconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String timeslotstarttime = mypref1.getString("timeslotstarttime", "");
                String timeslotendtime = mypref1.getString("timeslotendtime","");
                if (edtdate.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                }
                else if(timeslotstarttime.equals("") || timeslotendtime.equals(""))
                {
                    Toast.makeText(getActivity(), "Please choose atleast one slot", Toast.LENGTH_SHORT).show();
                }
                else {
                    BookAppointmentFragment f1 = new BookAppointmentFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, f1);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
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
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(getActivity(), date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),  myCalendar1.get(Calendar.DAY_OF_MONTH));
                mDate.show();

            }
        });


        return view;
    }
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (myCalendar1.before(Calendar.DAY_OF_MONTH))
        {
            Toast.makeText(getActivity(), "You can't apply date before today", Toast.LENGTH_SHORT).show();
        }
        else{
            edtdate.setText(sdf.format(myCalendar1.getTime()));


            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
            Date date = null;
            try {
                date = sdf1.parse(edtdate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String goal = outFormat.format(date);
          //  Toast.makeText(getActivity(), goal, Toast.LENGTH_SHORT).show();
            SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditr = mypref.edit();
            prefsEditr.putString("bookingdayname", goal);
            prefsEditr.putString("bookingdate", edtdate.getText().toString());
            prefsEditr.commit();
            @SuppressLint("WrongConstant")
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerview.setLayoutManager(mLayoutManager);
            list_data = new ArrayList<>();
            GetSlot();

        }
    }

    private void GetSlot() {
       // String url = AppConstants.Base_URL+"GetSlot";
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String counselloriduser = mypref1.getString("counselloriduser", "");
        String bookingdayname = mypref1.getString("bookingdayname","");
        String url = AppConstants.Base_URL+"GetCounsellorAvailability?CounsellorID="+counselloriduser+"";
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    list_data.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        HealthChampPlusData statedata = new HealthChampPlusData();
                      String dayname =  bookingdayname.toUpperCase();
                      if (dayname.equals(jsonobject.getString("counsellinday"))) {
                          statedata.setAddress(jsonobject.getString("counsellingStarttime") + " - " + jsonobject.getString("counsellingEndTime"));
                          statedata.setId(jsonobject.getString("id"));
                          statedata.setName(jsonobject.getString("counsellingStarttime"));
                          statedata.setDentalcaries(jsonobject.getString("counsellingEndTime"));
                          list_data.add(statedata);
                      }

                    }
                    GetTimeSlotDataAdapter adapter = new GetTimeSlotDataAdapter((ArrayList<HealthChampPlusData>) list_data, getActivity());
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}