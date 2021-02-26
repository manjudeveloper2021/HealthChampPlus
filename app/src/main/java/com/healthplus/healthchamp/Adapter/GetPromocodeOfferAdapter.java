package com.healthplus.healthchamp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.healthplus.healthchamp.Fragment.CounselorProfileFragment;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetPromocodeOfferAdapter extends RecyclerView.Adapter<GetPromocodeOfferAdapter.ViewHolder> {
    private ArrayList<HealthChampPlusData> list_data;
    public static ArrayList<String> mList = new ArrayList<>();
    private Context context;
    public String status1;
    public int position;
    public  int row_index=0;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public GetPromocodeOfferAdapter(ArrayList<HealthChampPlusData> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_promocode,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HealthChampPlusData listData = list_data.get(position);
        holder.txtid.setText(listData.getId());
        holder.txtoffername.setText(listData.getName());
        holder.txtoffercode.setText(listData.getQualify());
        holder.txtoffervalue.setText(listData.getAddress());


        holder.rel_offer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(context);
                String counsellingamount  = mypref1.getString("counsellingamount", "");
                String donateamount = counsellingamount;
                Double counselorcharge = Double.valueOf(donateamount);//Double.valueOf(dororprojectfundneeded);

                String  offercharge = list_data.get(position).getAddress();
                Double offamount = Double.valueOf(offercharge);
                if ((counselorcharge < offamount) || (counselorcharge.equals(offamount)))
                {
                    holder.selectoffer.setVisibility(View.GONE);
                    Toast.makeText(context, "Can not apply this offer", Toast.LENGTH_SHORT).show();
                }
              else {
                    row_index = position;
                    notifyDataSetChanged();
                    Toast.makeText(context, "Offer Applied", Toast.LENGTH_SHORT).show();
                    SharedPreferences mypref2 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefsEditr1 = mypref2.edit();
                    prefsEditr1.putString("promocodeid", list_data.get(position).getId());
                    prefsEditr1.putString("promocodeoffer", list_data.get(position).getName());
                    prefsEditr1.putString("promocodeoffercode", list_data.get(position).getQualify());
                    prefsEditr1.putString("promocodeoffervalue", list_data.get(position).getAddress());
                    prefsEditr1.commit();
                }

            }
        });
        if (row_index == position) {
            holder.selectoffer.setVisibility(View.VISIBLE);

        }
        else{
            holder.selectoffer.setVisibility(View.GONE);
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
        private TextView txtid,txtoffername,txtoffervalue,txtoffercode;
        private ImageView selectoffer;
        private RelativeLayout rel_offer1;
        public ViewHolder(View itemView) {
            super(itemView);
            txtoffername = (TextView)itemView.findViewById(R.id.txtoffername);
            txtoffervalue = (TextView)itemView.findViewById(R.id.txtoffervalue);
            txtid=(TextView) itemView.findViewById(R.id.txtid);
            txtoffercode=(TextView) itemView.findViewById(R.id.txtoffercode);
            selectoffer=(ImageView) itemView.findViewById(R.id.selectoffer);
            rel_offer1=(RelativeLayout) itemView.findViewById(R.id.rel_offer1);



        }
    }
}
