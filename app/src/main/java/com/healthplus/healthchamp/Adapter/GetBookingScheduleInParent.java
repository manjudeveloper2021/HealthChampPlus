package com.healthplus.healthchamp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import java.util.ArrayList;

public class GetBookingScheduleInParent extends RecyclerView.Adapter<GetBookingScheduleInParent.ViewHolder> {
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
    public GetBookingScheduleInParent(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_bookingparent,parent,false);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);
        holder.txtname.setText(listData.getName());
        holder.txtmail.setText(listData.getExperience());
        holder.txtmobile.setText(listData.getContact());
        holder.txtdate.setText(listData.getDate());
        holder.txtslot.setText(listData.getAddress());
        holder.txttype.setText(listData.getDocument());
        holder.txtmode.setText(listData.getBehave());
//        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(context);
//        String rolename = mypref1.getString("roleName","");
//        if (rolename.equals("Counsellor"))
//        {
//
//        }
//        if (rolename.equals("Parent"))
//        {
//
//        }

        holder.takecounselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomMainActivity.class);
                context.startActivity(intent);
            }
        });


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
        private TextView txtslot,txtdate,txtname,txtmail,txtmobile,txttype,txtmode,takecounselling;
        private TextView txtid;
        public ViewHolder(View itemView) {
            super(itemView);

            takecounselling = (TextView) itemView.findViewById(R.id.takecounselling);
            txttype = (TextView) itemView.findViewById(R.id.txttype);
            txtmode = (TextView) itemView.findViewById(R.id.txtmode);
            txtslot = (TextView) itemView.findViewById(R.id.txtslot);
            txtdate=(TextView) itemView.findViewById(R.id.txtdate);
            txtname=(TextView) itemView.findViewById(R.id.txtname);
            txtmail=(TextView) itemView.findViewById(R.id.txtmail);
            txtmobile=(TextView) itemView.findViewById(R.id.txtmobile);
        }
    }
}
