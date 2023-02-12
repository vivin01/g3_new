package com.vivin.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText name;
    EditText password,age,phone,email;
    Button done;
    FirebaseAuth auth;
    boolean passwordVisible;
    FirebaseDatabase firebaseDatabase;

    Intent inte = getIntent();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name2);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        password = findViewById(R.id.password_signup);
        //cpassword = findViewById(R.id.confirmpassword_signup);
        done = findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().getReference().child("user data").child("hello user").setValue()


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_name = name.getText().toString();
                String text_Email = email.getText().toString();
                String text_phone = phone.getText().toString();
                String text_age = age.getText().toString();
                String text_Password = password.getText().toString();

                if (TextUtils.isEmpty(text_name)){
                    name.setError("please enter name");
                }
                else if (TextUtils.isEmpty(text_Email)){
                    email.setError("please enter email");
                }
                else if (TextUtils.isEmpty(text_phone)){
                    phone.setError("please enter phone number");
                }
                else if (TextUtils.isEmpty(text_age)){
                    age.setError("please enter age");
                }
                else if (TextUtils.isEmpty(text_Password)){
                    password.setError("please enter password");
                }
                else if (text_Password.length() < 6){
                    Toast.makeText(SignUpActivity.this, "minimum 6 char required in password ", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(text_name,text_Email,text_phone, text_age, text_Password);
                }

            }

        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            //for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            //for hide password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void registerUser(String text_name, String text_email,String text_phone, String text_age, String text_password) {

        auth.createUserWithEmailAndPassword(text_email, text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    UserHelper user = new UserHelper(text_name,text_email,text_phone,text_age, text_password );

                    FirebaseDatabase.getInstance().getReference("users").
                            child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, "Failed 1", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    //Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(in);
                    finish();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Failed 2 ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
