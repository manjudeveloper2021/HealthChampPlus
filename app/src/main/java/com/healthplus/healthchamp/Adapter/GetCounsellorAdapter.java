package com.healthplus.healthchamp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.healthplus.healthchamp.Fragment.CounselorProfileFragment;
import com.healthplus.healthchamp.Fragment.DentalScreeningFragment;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetCounsellorAdapter extends RecyclerView.Adapter<GetCounsellorAdapter.ViewHolder> {
    private ArrayList<HealthChampPlusData> list_data;
    public static ArrayList<String> mList = new ArrayList<>();
    private Context context;
    public String status1;
    public int position;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public GetCounsellorAdapter(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_counsellorlist,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);
        holder.txtid.setText(listData.getId());
        holder.txtname.setText(listData.getName());
        holder.txtspecialization.setText(listData.getQualify());
        holder.txtlocation.setText(listData.getAddress());
        holder.txturl.setText(listData.getDocument());
   if (list_data.get(position).getDocument().equals("")||list_data.get(position).getDocument().equals("null")||list_data.get(position).getDocument().equals(null)) {
       Picasso.with(context)
               .load(R.drawable.docmale)
               .into(holder.profilepic);
   }
   else{

       Picasso.with(context)
               .load("http://apischoolhealth.litchi.co.in/ProfileImage/"+listData.getDocument())
               .into(holder.profilepic);
   }
        holder.txtconsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounselorProfileFragment f1 = new CounselorProfileFragment();
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor prefsEditr1 = mypref1.edit();
                prefsEditr1.putString("counselloriduser", list_data.get(position).getId());
                prefsEditr1.commit();
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
        private TextView txtid,txtname,txtspecialization;
        private TextView txtlocation,txtconsult,txturl;
        private CircleImageView profilepic;
        public ViewHolder(View itemView) {
            super(itemView);
            txturl = (TextView)itemView.findViewById(R.id.txturl);
            txtname = (TextView)itemView.findViewById(R.id.txtname);
            txtspecialization = (TextView)itemView.findViewById(R.id.txtspecialization);
            txtid=(TextView) itemView.findViewById(R.id.txtid);
            txtlocation=(TextView) itemView.findViewById(R.id.txtlocation);
            txtconsult=(TextView) itemView.findViewById(R.id.txtconsult);
            profilepic=(CircleImageView) itemView.findViewById(R.id.profilepic);


        }
    }
}
