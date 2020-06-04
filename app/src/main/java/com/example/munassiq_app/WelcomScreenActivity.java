package com.example.munassiq_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomScreenActivity extends AppCompatActivity {
    int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mySuperIntent = new Intent(WelcomScreenActivity.this, MainActivity.class);
                startActivity(mySuperIntent);

                finish();

            }
        }, TIME_OUT);
    }
}
