package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class StudentList<Student> extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BatchAdapter adapter;
//    private List<Student> students = new ArrayList<>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        String batchName = getIntent().getStringExtra("batchname");
        setTitle(batchName);


    }
}