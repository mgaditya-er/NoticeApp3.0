package com.example.noteiceboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminBatches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_batches);

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBatchDialog();
            }
        });




        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference batchesRef = database.getReference("batches");

        // Retrieve data from Firebase and display in RecyclerView
        batchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Batch> batches = new ArrayList<>();
                for (DataSnapshot batchSnapshot : snapshot.getChildren()) {
                    Batch batch = batchSnapshot.getValue(Batch.class);
                    batches.add(batch);
                }
                BatchAdapter adapter = new BatchAdapter(batches);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(AdminBatches.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });


    }

        private void showBatchDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.createbatchdialog, null);
            builder.setView(dialogView);
            Button cancelButton = dialogView.findViewById(R.id.cancel_button);
            Button okButton = dialogView.findViewById(R.id.ok_button);
            EditText batchName = dialogView.findViewById(R.id.batch_name_input);
            EditText batchcode = dialogView.findViewById(R.id.unique_code_input);

            AlertDialog dialog = builder.create();
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AdminBatches.this, "Name :"+batchName.getText().toString().trim()+"\nCode"+batchcode.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    // Get a reference to the Firebase database
                    // Get batch code and name from EditText fields
                    String code = batchcode.getText().toString().trim();
                    String name = batchName.getText().toString().trim();

// Create a new Batch object with the batch code, name, and an empty list of emails
                    Batch batch = new Batch(name, Integer.parseInt(code), new ArrayList<String>());

// Add current user's email to the list of emails
                    String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    batch.getEmails().add(userEmail);

// Save the Batch object to Firebase
                    DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("batches");
                    batchesRef.child(code).setValue(batch);

                    dialog.dismiss();
                }
            });
            dialog.show();

        }

    }

