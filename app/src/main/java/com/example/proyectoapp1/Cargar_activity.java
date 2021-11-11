package com.example.proyectoapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Cargar_activity extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN

        );
        setContentView(R.layout.activity_cargar);
        getSupportActionBar().hide();

        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                Intent newIntent= new Intent(getApplicationContext(), InicioCrud.class);
                startActivity(newIntent);
                finish();
            }
        };


        Timer timer= new Timer();
        timer.schedule(timerTask, SPLASH_SCREEN_DELAY);
    }
}
