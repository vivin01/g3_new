package com.vivin.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        /*VideoView animation = findViewById(R.id.videoView2);
        animation.setVideoPath("android.resource://"+getPackageName() + "/" + R.raw.animation2);
        animation.start();

        if(animation.isPlaying()){
            Toast.makeText(this, "nikal", Toast.LENGTH_SHORT).show();
        }*/

        Intent in = new Intent(this,SplashScreenActivity.class);
        startActivity(in);
        finish();

    }
}