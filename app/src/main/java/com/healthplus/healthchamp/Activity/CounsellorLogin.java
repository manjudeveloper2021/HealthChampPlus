package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthplus.healthchamp.R;

public class CounsellorLogin extends AppCompatActivity {
TextView txtsignup;
LinearLayout btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_login);
        txtsignup=findViewById(R.id.txtsignup);
        btnlogin=findViewById(R.id.btnlogin);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorLogin.this, CounsellorScreen.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorLogin.this, CounsellorProfile.class);
                startActivity(intent);
            }
        });
    }
}