package com.example.jcesa.chilakillers;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import android.widget.VideoView;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 8000;

    private VideoView videoView;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE); /*Desactivamos el título de la Activity*/

        setContentView(R.layout.activity_splash_screen);
        setTitle("Bienvenido!");

        // Muestra el Activity en modo Landscape.
        /*this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); */
        setUpVideoView();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void setUpVideoView() {

        videoView = (VideoView) findViewById(R.id.videoView);

        Uri path = Uri.parse("android.resource://com.example.jcesa.chilakillers/"
                + R.raw.adopcion);

        videoView.setVideoURI(path);
        videoView.start();
    }

}



