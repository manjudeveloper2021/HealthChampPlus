package com.healthplus.healthchamp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import java.util.ArrayList;

public class GetTimeSlotDataAdapter extends RecyclerView.Adapter<GetTimeSlotDataAdapter.ViewHolder> {
    private ArrayList<HealthChampPlusData> list_data;
    public static ArrayList<String> mList = new ArrayList<>();
    private Context context;
    public String status1;
    public int position,row_index;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public GetTimeSlotDataAdapter(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot_design,parent,false);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);

        holder.txtid.setText(listData.getId());
        holder.txttimeslot.setText(listData.getAddress());
        holder.txtstarttime.setText(listData.getName());
        holder.txtendtime.setText(listData.getDentalcaries());

       holder.layout_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
                if(row_index==position){
                    holder.txttimeslot.setBackgroundResource(R.drawable.roundedcounsellor_button);
                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
                    prefsEditr.putString("timeslotid", list_data.get(position).getId());
                    prefsEditr.putString("timeslotstarttime", list_data.get(position).getName());
                    prefsEditr.putString("timeslotendtime", list_data.get(position).getDentalcaries());
                    prefsEditr.commit();


                }
                else{
                    holder.txttimeslot.setBackgroundResource(R.drawable.roundedbordercounsellor_button);
                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.black));
                }



//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//
//                }


//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//                }
//
//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//                }
//
//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//                }
//
//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//                }
//
//                if(row_index==position){
//                    holder.txttimeslot.setBackgroundResource(R.drawable.complete_button);
//                    holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("timeslot", list_data.get(position).getAddress());
//                    prefsEditr.commit();
//                }

            }
        });
        if(row_index==position){
            holder.txttimeslot.setBackgroundResource(R.drawable.roundedcounsellor_button);
            holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.white));


        }
        else{
            holder.txttimeslot.setBackgroundResource(R.drawable.roundedbordercounsellor_button);
            holder.txttimeslot.setTextColor(context.getResources().getColor(R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, HealthChampPlusData data) {
        list_data.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(HealthChampPlusData data) {
        int position = list_data.indexOf(data);
        list_data.remove(position);
        notifyItemRemoved(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txttimeslot,txtstarttime,txtendtime;
        private TextView txtid;
        private LinearLayout layout_slot;
        public ViewHolder(View itemView) {
            super(itemView);
            txtendtime = (TextView) itemView.findViewById(R.id.txtendtime);
            txtstarttime = (TextView) itemView.findViewById(R.id.txtstarttime);
            txttimeslot = (TextView) itemView.findViewById(R.id.txttimeslot);
            txtid=(TextView) itemView.findViewById(R.id.txtid);
            layout_slot=(LinearLayout) itemView.findViewById(R.id.layout_slot);
        }
    }
}
