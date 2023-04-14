package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BatchDetailsActivity extends AppCompatActivity {

    Button listbtn,noticebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_details);
        // Get the batchname extra from the intent
        String batchName = getIntent().getStringExtra("batchname");
        setTitle(batchName);

        listbtn = findViewById(R.id.Listbutton2);
        noticebtn = findViewById(R.id.Noticebutton3);

        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatchDetailsActivity.this, StudentList.class);
                intent.putExtra("batchname", batchName); // Pass the batch id to the new activity
                startActivity(intent);
            }
        });

        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatchDetailsActivity.this, NotceHome.class);
                intent.putExtra("batchname", batchName); // Pass the batch id to the new activity
                startActivity(intent);

            }
        });



    }

}