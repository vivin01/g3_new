package com.vivin.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class UserImagesList extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image_list);

        Intent inti = getIntent();

        listView = findViewById(R.id.listView);
        Toast.makeText(this, "vivin", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(UserImagesList.this,R.layout.activity_list_item, list);
        listView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String link = dataSnapshot.getValue(String.class);
                    //
                    //list.add(Objects.requireNonNull(dataSnapshot.getValue()).toString());\
                    list.add(link);
                    //Picasso.get().load(link).into(rImage);
                }
                //adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
