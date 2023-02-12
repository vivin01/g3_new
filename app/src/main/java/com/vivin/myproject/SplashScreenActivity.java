package com.vivin.myproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {
    //VideoView animation;
    //SurfaceView animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent inti = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //-*******************************************************

        /*VideoView animation = findViewById(R.id.videoView);
        animation.setVideoPath("android.resource://"+getPackageName() + "/" + R.raw.animation2);
        animation.start();*/

       /* Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(i);*/

        LoadingDialog loadingDialog = new LoadingDialog(SplashScreenActivity.this);

        SplashScreenActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.startLoading();
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },3000);

    }
}
