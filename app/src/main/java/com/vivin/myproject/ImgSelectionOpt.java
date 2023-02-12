package com.vivin.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ImgSelectionOpt extends AppCompatActivity {

    ImageView scan,add;
    Button logout;
    TextView name_fetch;
    CardView cardView;
    //DatabaseReference database;

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String userId;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_selection_opt);
        Intent inti = getIntent();

        scan = findViewById(R.id.scan_img);
        add = findViewById(R.id.add_img);
        logout = findViewById(R.id.logout);
        name_fetch = findViewById(R.id.name_profile);
        cardView = findViewById(R.id.cardview);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ImgSelectionOpt.this, UserImagesList.class);
                startActivity(in);
            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ImgSelectionOpt.this,ImgScanner.class);
                startActivity(in);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ImgSelectionOpt.this, ImgSelect.class);
                startActivity(in);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ImgSelectionOpt.this, "you LoggedOut Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ImgSelectionOpt.this, LoginActivity.class));
            }
        });

        // ****************** firebase

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("name");

        getdata();
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                name_fetch.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(ImgSelectionOpt.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
