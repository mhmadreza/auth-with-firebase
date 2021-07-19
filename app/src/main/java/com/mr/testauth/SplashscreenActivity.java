package com.mr.testauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashscreenActivity extends AppCompatActivity {

    private int DELAYSCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        setUISS();
    }

    private void setUISS() {

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent move = new Intent(SplashscreenActivity.this, LoginActivity.class);
                startActivity(move);
                finish();
            }
        }, DELAYSCREEN );

    }

}