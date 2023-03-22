package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent iHome = new Intent(SplashActivity.this,SignUpActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS,0);
//                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
//
//                if(hasLoggedIn)
//                {
//                    Intent iHome1 = new Intent(SplashActivity.this,TeacherHome.class);
//                    startActivity(iHome1);
//                }
//                else
//                {
//                    startActivity(iHome);
//                }
                startActivity(iHome);
            }
        },4000);
    }

}