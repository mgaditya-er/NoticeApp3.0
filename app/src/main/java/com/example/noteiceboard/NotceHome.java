package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NotceHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notce_home);


        String batchName = getIntent().getStringExtra("batchname");
        setTitle(batchName);
    }
}