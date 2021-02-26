package com.healthplus.healthchamp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthplus.healthchamp.R;

import org.w3c.dom.Text;

public class CounsellorScreen extends AppCompatActivity {
    TextView txtsignin;
    LinearLayout btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_screen);
        txtsignin=findViewById(R.id.txtsignin);
        btnsignup=findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CounsellorScreen.this, CounsellorVerificationActivity.class);
//                startActivity(intent);
            }
        });
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorScreen.this, CounsellorLogin.class);
                startActivity(intent);
            }
        });
    }
}