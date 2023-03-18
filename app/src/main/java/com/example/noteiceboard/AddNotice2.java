package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNotice2 extends AppCompatActivity {
    private TextView bname,bcode;
    private EditText title,content;
    private Button create;
    String id, name, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice2);


        Intent intent = getIntent();
        if(intent != null) {
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            code = intent.getStringExtra("code");
        }



        bname = findViewById(R.id.textView);
        bcode = findViewById(R.id.textView2);
        bname.setText(name);
        bcode.setText(code);
        title = findViewById(R.id.editTextTextPersonName);
        content = findViewById(R.id.editTextTextPersonName2);


        create = findViewById(R.id.button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeDbHelper noticeDb = new NoticeDbHelper(AddNotice2.this);
                noticeDb.addNotice(title.getText().toString().trim(),content.getText().toString().trim(), Integer.parseInt(id));
                Intent iHome = new Intent(AddNotice2.this,BatchHome.class);
                startActivity(iHome);
            }
        });






    }
}