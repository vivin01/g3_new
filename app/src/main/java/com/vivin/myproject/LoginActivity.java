package com.vivin.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button next;
    TextView signup;
    FirebaseAuth auth;
    boolean passwordVisible;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent inti = getIntent();

        // FirebaseDatabase.getInstance().getReference().child("user data").setValue("vivin");
        next = findViewById(R.id.button2);
        signup = findViewById(R.id.signup);
        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.password_signup2);
        auth = FirebaseAuth.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();

                if (TextUtils.isEmpty(text_email)){
                    email.setError("enter email");
                }
                else if(TextUtils.isEmpty(text_password)){
                    password.setError("enter password");
                }
                else {
                    loginUser(text_email, text_password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(in);
                //finish();
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

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "you login successfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(LoginActivity.this, ImgSelectionOpt.class);
                    startActivity(in);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "please enter correct Email & password", Toast.LENGTH_SHORT).show();
                    /*Intent in = new Intent(LoginActivity.this, ImgSelectionOpt.class);
                    startActivity(in);
                    finish();*/
                }
            }
        });

    }
}

// 8.40***********
