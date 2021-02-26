package com.healthplus.healthchamp.Fragment;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.collection.SimpleArrayMap;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.healthplus.healthchamp.Activity.Util;
import com.healthplus.healthchamp.R;

import java.text.DecimalFormat;

public class BMICalculateFragment extends Fragment {
EditText edtfeet,edtinch,edtweight;
Button calc;
TextView result,txtmale,txtfemale;
ImageView imgmale,imgfemale;
    Double valueheight,valueweight,bmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bmi_calculator, container, false);
        edtfeet = view.findViewById(R.id.edtfeet);
        edtinch = view.findViewById(R.id.edtinch);
        edtweight = view.findViewById(R.id.edtweight);
        calc=view.findViewById(R.id.calc);
        result=view.findViewById(R.id.result);
        imgmale=view.findViewById(R.id.imgmale);
        imgfemale=view.findViewById(R.id.imgfemale);
        txtmale=view.findViewById(R.id.txtmale);
        txtfemale=view.findViewById(R.id.txtfemale);
        imgmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmale.setTextColor(ContextCompat.getColor(getActivity(), R.color.male));
                imgmale.setImageResource(R.drawable.maleblue);
                imgfemale.setImageResource(R.drawable.female);
                txtfemale.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
            }
        });
        imgfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtfemale.setTextColor(ContextCompat.getColor(getActivity(), R.color.female));
                imgfemale.setImageResource(R.drawable.femalepink);
                imgmale.setImageResource(R.drawable.male);
                txtmale.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String str1 = edtweight.getText().toString();
            String str2 = edtfeet.getText().toString();
             //   String str3 = edtfeet.getText().toString();
             //   String str2 = str3+"."+str;


                if(TextUtils.isEmpty(str1)){
                   Toast.makeText(getActivity(), "Please enter your weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(str2)){
                    Toast.makeText(getActivity(), "Please enter your height", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    calculate();
                }

//Get the user values from the widget reference
             //   float weight = Float.parseFloat(str1);
             //   float height = Float.parseFloat(str2);

//Calculate BMI value
            //    float bmiValue = calculateBMI(weight, height);

//Define the meaning of the bmi value
             //   String bmiInterpretation = interpretBMI(bmiValue);
//show message
             //  result.setText(String.valueOf(bmiValue + "-" + bmiInterpretation));

            }

        });
        return view;
    }
    //Calculate BMI
    private float calculateBMI (float weight, float height) {
        return (float) ((weight / height * height) *10000);
    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private void calculate()    {
        valueheight =Double.parseDouble(edtfeet.getText().toString());
        valueweight =Double.parseDouble(edtweight.getText().toString());
        Double valueheightmeters;

        valueheightmeters = valueheight / 100; // Converting to meters.
        bmi = (valueweight / (valueheightmeters * valueheightmeters));
        double roundedNumber = Util.round(bmi, 2);
        //txttipamount.setText(Double.toString(bmi));
        if (bmi >= 30) { /* obese */


            result.setText("Your BMI of " + roundedNumber + " is OBESE.");
        } else if (bmi >= 25) {

            result.setText("Your BMI of " + roundedNumber + " is OVERWEIGHT.");
        } else if (bmi >= 18.5) {
            result.setText("Your BMI of " + roundedNumber + " is IDEAL.");
        } else {

            result.setText("Your BMI of " + roundedNumber + " is UNDERWEIGHT.");
        }
    }
}