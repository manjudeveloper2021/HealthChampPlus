package com.healthplus.healthchamp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.healthplus.healthchamp.Model.SpinnerData;
import com.healthplus.healthchamp.R;


import java.util.ArrayList;

public class SpinnerStateAdapter extends BaseAdapter implements SpinnerAdapter {
    private Integer[] images;
    private ArrayList<SpinnerData> statedata;
    String[] colors = {"#13edea","#e20ecd","#15ea0d"};
    String[] colorsback = {"#FF000000","#FFF5F1EC","#ea950d"};
    private Context context;
    LayoutInflater inflater;
    private int resource;


    public SpinnerStateAdapter(Context context, int resource, ArrayList<SpinnerData> statedata) {
        this.context = context;
        this.statedata = statedata;
        this.resource = resource;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override
    public View getDropDownView(final int position, View convertView, ViewGroup parent) {
        View view;
        view =  View.inflate(context, R.layout.spinnerdata_layout, null);
        final TextView textView = (TextView) view.findViewById(R.id.statename);
        final TextView textView2 = (TextView) view.findViewById(R.id.stateid);


        final  SpinnerData statedata1= statedata.get(position);
        textView.setText(statedata1.getName());
        textView2.setText(statedata1.getId());


        return view;
    }



    @Override
    public int getCount() {
        return statedata.size();
    }



    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.spinner_dropdown_layout, null);
       final SpinnerData statedata1= statedata.get(position);
        TextView tvstatename=(TextView)view.findViewById(R.id.statename);
        TextView tvstateid=(TextView)view.findViewById(R.id.stateid);



        return view;
    }


}