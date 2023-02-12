package com.vivin.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class ImgSelect extends AppCompatActivity {

    ImageView select;
    Button click2;
    public static ImageView im ;
    private final int PICK_IMAGE_REQUEST = 22;
    Uri uri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inte = getIntent();
        setContentView(R.layout.activity_img_select);

        select = findViewById(R.id.imageView3);
        click2 = findViewById(R.id.click2);

        ImagePicker.with(ImgSelect.this)
                .crop(3f,4f) //Crop image(Optional), Check Customization for more option
//              .compress(1024)			//Final image size will be less than 1 MB(Optional), this will compress our image
//              .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .galleryOnly() // this function is use to give access of gallery only
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        select.setImageURI(uri);

        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImgSelect.this, "Wait a while, your Image is Uploading...", Toast.LENGTH_SHORT).show();

                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {

                    // Get the Uri of data
                    //filePath = data.getData();
                    try {

                        // Setting image on image view using Bitmap
                        Bitmap bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        uri);
                        select.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // Log the exception
                        e.printStackTrace();
                    }
                }
                uploadImage();

            }
        });
    }

    private void uploadImage() {
        if (uri != null) {

            // Defining the child of storageReference

            Date d = new Date();
            String name = d.toString();
            FirebaseStorage.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().
                    getCurrentUser()).getUid()).child(name).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ImgSelect.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ImgSelect.this, "Image uploading Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "L lag gaye", Toast.LENGTH_SHORT).show();
        }
    }
}
