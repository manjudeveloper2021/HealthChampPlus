package com.healthplus.healthchamp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.healthplus.healthchamp.Activity.LoginActivity;
import com.healthplus.healthchamp.Activity.SessionManager;
import com.healthplus.healthchamp.R;

public class SettingFragment extends Fragment {
LinearLayout layout_logout;
SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting_fragment, container, false);
        layout_logout=view.findViewById(R.id.layout_logout);
        sessionManager = new SessionManager(getActivity());
        layout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   sessionManager.logoutUser();
                SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=spreferences.edit();
                editor.remove("roleName");
                editor.remove("userid");
                editor.remove("timeslotstarttime");
                editor.remove("timeslotendtime");
                editor.apply();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}