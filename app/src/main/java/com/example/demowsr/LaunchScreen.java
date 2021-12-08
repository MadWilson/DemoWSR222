package com.example.demowsr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean fs = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstRun",false);
                if (fs)
                {
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstRun", true).apply();
                    Intent i = new Intent(LaunchScreen.this, SignUpActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(LaunchScreen.this, SignInActivity.class);
                    startActivity(i);
                }

                finish();
            }
        }, 5*600);


    }
}