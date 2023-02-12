package com.vivin.myproject;

import android.Manifest;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class ImgScanner extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private final int PICK_IMAGE_REQUEST = 22;
    ImageView scan;
    Button click;
    Uri uri;
    //FirebaseStorage storage;
    //StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_scaner);

        Intent inti = getIntent();

        scan = findViewById(R.id.scan);
        click = findViewById(R.id.click);

        ImagePicker.with(ImgScanner.this)
                .crop(3f, 4f)                    //Crop image(Optional), Check Customization for more option
//              .compress(1024)			//Final image size will be less than 1 MB(Optional)
//              .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly() // this function is use to give access to only camera and directly open camera
                .start();
    }


   /* private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/

    private void askPermission() {
        ActivityCompat.requestPermissions(ImgScanner.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;
        uri = data.getData();
        scan.setImageURI(uri);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImgScanner.this, "Wait a while, your Image is Uploading...", Toast.LENGTH_SHORT).show();

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
                        scan.setImageBitmap(bitmap);
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
                        Toast.makeText(ImgScanner.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ImgScanner.this, "Image uploading Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            /*StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(uri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    //progressDialog.dismiss();
                                    Toast.makeText(ImgScanner.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
                            Toast.makeText(ImgScanner.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            //progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            Toast.makeText(ImgScanner.this, "Image Uploaded!! 2 ", Toast.LENGTH_SHORT).show();
                        }
                    });*/
        } else {
            Toast.makeText(this, "L laag gaye", Toast.LENGTH_SHORT).show();
        }
    }
}


