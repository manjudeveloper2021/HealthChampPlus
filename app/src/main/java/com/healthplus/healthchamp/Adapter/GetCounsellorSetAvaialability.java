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

public class GetCounsellorSetAvaialability extends RecyclerView.Adapter<GetCounsellorSetAvaialability.ViewHolder> {
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
    public GetCounsellorSetAvaialability(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_slotavailability,parent,false);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);

        holder.txtid.setText(listData.getId());
        holder.txtslot.setText(listData.getAddress());
        holder.txtday.setText(listData.getQualify());

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
        private TextView txtslot,txtday;
        private TextView txtid;
        public ViewHolder(View itemView) {
            super(itemView);
            txtslot = (TextView) itemView.findViewById(R.id.txtslot);
            txtid=(TextView) itemView.findViewById(R.id.txtid);
            txtday=(TextView) itemView.findViewById(R.id.txtday);
        }
    }
}
