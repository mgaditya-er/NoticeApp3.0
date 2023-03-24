package com.example.noteiceboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudentList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListBatchAdapter1 adapter;
    private List<String> emailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        String batchName = getIntent().getStringExtra("batchname");
        setTitle(batchName);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("batches");

        batchesRef.orderByChild("name").equalTo(batchName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Batch found!", Toast.LENGTH_SHORT).show();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // retrieve the email list for this batch
                        List<String> emails = (List<String>) snapshot.child("emails").getValue();
                        if (emails != null) {
                            emailList.addAll(emails);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // the batch with the given name does not exist
                    Toast.makeText(getApplicationContext(), "Batch not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle the error
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ListBatchAdapter1(emailList);
        recyclerView.setAdapter(adapter);
    }
}
