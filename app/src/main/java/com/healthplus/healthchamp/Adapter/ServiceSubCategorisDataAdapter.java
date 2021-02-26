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

import com.healthplus.healthchamp.Fragment.BMICalculateFragment;
import com.healthplus.healthchamp.Fragment.CounselorListFragment;
import com.healthplus.healthchamp.Fragment.ServiceBySubCategoryFragment;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ServiceSubCategorisDataAdapter extends RecyclerView.Adapter<ServiceSubCategorisDataAdapter.ViewHolder> {
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
    public ServiceSubCategorisDataAdapter(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dsignservicecategory_layout,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);
        holder.txtcategoryname.setText(listData.getName());
        holder.txtid.setText(listData.getId());
        holder.txtimagepath.setText(listData.getDocument());
        Picasso.with(context)
                .load("http://"+list_data.get(position).getDocument())
                .into(holder.imgicon);
        holder.layout_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_data.get(position).getName().equals("How well is my child?"))
                {
                    ServiceBySubCategoryFragment f1 = new ServiceBySubCategoryFragment();
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor prefsEditr1 = mypref1.edit();
                prefsEditr1.putString("servicesubcategoryid", list_data.get(position).getId());
                prefsEditr1.commit();
                }
                else if (list_data.get(position).getName().equals("BMI Calculator"))
                {
                    BMICalculateFragment f1 = new BMICalculateFragment();
                    FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, f1);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else {
                    CounselorListFragment f1 = new CounselorListFragment();
                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, f1);
                    ft.addToBackStack(null);
                    ft.commit();
                }

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
        private TextView txtcategoryname,txtimagepath;
        private TextView txtid;
        private ImageView imgicon;
        private LinearLayout layout_service;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_service = (LinearLayout) itemView.findViewById(R.id.layout_service);
            txtcategoryname=(TextView) itemView.findViewById(R.id.txtcategoryname);
            txtid = (TextView)itemView.findViewById(R.id.txtid);
            txtimagepath=(TextView) itemView.findViewById(R.id.txtimagepath);
            imgicon=(ImageView) itemView.findViewById(R.id.imgicon);
        }
    }
}
