package com.healthplus.healthchamp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCounsellorProfile extends AppCompatActivity {
LinearLayout btnnext,btnnext1,btnnext2;
LinearLayout layout_addprofile,layout_addeducation,layout_addwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_counsellorprofile);
        btnnext=findViewById(R.id.btnnext);
        btnnext1=findViewById(R.id.btnnext1);
        btnnext2=findViewById(R.id.btnnext2);
        layout_addprofile=findViewById(R.id.layout_addprofile);
        layout_addeducation=findViewById(R.id.layout_addeducation);
        layout_addwork=findViewById(R.id.layout_addwork);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_addprofile.setVisibility(View.GONE);
                layout_addwork.setVisibility(View.GONE);
                layout_addeducation.setVisibility(View.VISIBLE);
            }
        });
        btnnext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_addprofile.setVisibility(View.GONE);
                layout_addeducation.setVisibility(View.GONE);
                layout_addwork.setVisibility(View.VISIBLE);
            }
        });
//        btnnext2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }




}