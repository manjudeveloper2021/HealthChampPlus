package com.healthplus.healthchamp.ui.healthtips;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.healthplus.healthchamp.R;

public class HealthTipsFragment extends Fragment {
ImageView whatsapp1,whatsapp2;
    private HealthTipsViewModel healthtipsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        healthtipsViewModel =
                new ViewModelProvider(this).get(HealthTipsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_healthtips, container, false);

        whatsapp1=root.findViewById(R.id.whatsapp1);
        whatsapp2=root.findViewById(R.id.whatsapp2);
        healthtipsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap imgBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.healthtip1);
                String imgBitmapPath= MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),imgBitmap,"Health Tip",null);
                Uri imgBitmapUri=Uri.parse(imgBitmapPath);
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                startActivity(Intent.createChooser(shareIntent,"share image using"));

//                Uri imgUri = Uri.parse(pictureFile.getAbsolutePath());
//                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//                whatsappIntent.setType("text/plain");
//                whatsappIntent.setPackage("com.whatsapp");
//                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
//                whatsappIntent.setType("image/jpeg");
//                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                try {
//                    getActivity().startActivity(whatsappIntent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(getActivity(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });

        return root;
    }
    public void removieAllViews() {

    }

}