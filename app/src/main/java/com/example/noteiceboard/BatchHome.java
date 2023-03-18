package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BatchHome extends AppCompatActivity {
    private Context context;
    private Activity activity;
    RecyclerView recyclerView;
    NoticeDbHelper NoticeDb;
    ArrayList<String> Notice_id,Notice_title,batch_id;

    FloatingActionButton add_button1;
    private String id, name, code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_home);

        Intent intent = getIntent();
        if(intent != null) {
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            code = intent.getStringExtra("code");
        }




        add_button1 = findViewById(R.id.add_button1);
//        getAndSetIntentData();
        add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BatchHome.this, AddNotice2.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("code",code);

                startActivity(intent);
            }
        });

    }


}