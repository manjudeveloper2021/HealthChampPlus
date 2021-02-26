package com.healthplus.healthchamp.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.healthplus.healthchamp.R;
import com.healthplus.healthchamp.ui.slideshow.SlideshowFragment;

public class ConfirmBooking extends Fragment {
TextView txtsession,txtcouncildate;
Button btnback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_appointment, container, false);
        txtsession=view.findViewById(R.id.txtsession);
        txtcouncildate=view.findViewById(R.id.txtcouncildate);
        btnback=view.findViewById(R.id.btnback);
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bookingdate = mypref1.getString("bookingdate","");
        txtcouncildate.setText(bookingdate);
        String counsellingtypenameuser = mypref1.getString("counsellingtypenameuser","");
        txtsession.setText("Booked Counselling For" + counsellingtypenameuser);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideshowFragment f1 = new SlideshowFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;

    }
}