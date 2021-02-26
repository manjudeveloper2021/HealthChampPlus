package com.healthplus.healthchamp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.healthplus.healthchamp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadReportByCounselor extends AppCompatActivity {
    Uri contentURI;
    private ProgressDialog progressBar;
    private static int RESULT_LOAD_DOC = 1;
    String picturePath,filename;
    Bitmap bm;
    ImageView imgprescription;
    TextView txtchoose;
    Button btnproceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_report);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_800));
        }
        imgprescription=findViewById(R.id.imgprescription);
        txtchoose=findViewById(R.id.txtchoose);
        btnproceed=findViewById(R.id.btnproceed);
        txtchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i.createChooser(i, "Select Image"), RESULT_LOAD_DOC);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_DOC) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(contentURI,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = null;
                picturePath = cursor.getString(columnIndex);
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
              //  edtpath.setText(filename);
                imgprescription.setImageBitmap(bm);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm = Bitmap.createScaledBitmap(bm, 550, 550, true);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

            }
        }
    }

}