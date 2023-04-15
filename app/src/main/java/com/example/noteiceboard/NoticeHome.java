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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoticeHome extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListBatchAdapter1 adapter;
    private List<String> noticelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notce_home);
        String batchcode = getIntent().getStringExtra("code");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Get a reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference batchesRef = database.getReference("notices").child("1111");

        // Retrieve data from Firebase and display in RecyclerView
        batchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notice> batches = new ArrayList<>();
                for (DataSnapshot batchSnapshot : snapshot.getChildren()) {
                    Notice notice = batchSnapshot.getValue(Notice.class);

                    batches.add(notice);
                }
                NoticeAdapter adapter = new NoticeAdapter(batches);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(NoticeHome.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });



        String batchName = getIntent().getStringExtra("batchname");
        setTitle(batchName);

        // Don't show the floating button
        FloatingActionButton floatingButton = findViewById(R.id.floating_button);
        floatingButton.setVisibility(View.GONE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();

            Query query = db.collection("users").whereEqualTo("email", email);

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshot) {
                    if (!querySnapshot.isEmpty()) {
                        // Get the first document in the query results
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        String role = documentSnapshot.getString("role");
                        if (role.equals("Teacher")) {
                            // Show the floating button
                            FloatingActionButton floatingButton = findViewById(R.id.floating_button);
                            floatingButton.setVisibility(View.VISIBLE);
                            floatingButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showBatchDialog();
                                }
                            });
                            
                        }
                    }
                }
            });

        }
    }

    private void showBatchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.createnoticedialog, null);
        builder.setView(dialogView);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button okButton = dialogView.findViewById(R.id.ok_button);
        EditText noticetitle = dialogView.findViewById(R.id.batch_name_input);
        EditText noticebody = dialogView.findViewById(R.id.unique_code_input);

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
                Toast.makeText(NoticeHome.this, "TItle :"+noticetitle.getText().toString().trim()+"\nbody"+noticebody.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                // Get a reference to the Firebase database
                // Get batch code and name from EditText fields
                String title = noticetitle.getText().toString().trim();
                String body = noticebody.getText().toString().trim();

// Create a new notice object with the batch code, name, and an empty list of emails
                Notice notice = new Notice(title,body,new ArrayList<String>());
// Add current user's email to the list of emails

                DatabaseReference batchesRef1 = FirebaseDatabase.getInstance().getReference().child("notices").child("1111");
                String noticeId = batchesRef1.push().getKey();

                batchesRef1.child(noticeId).setValue(notice);


// Save the Batch object to Firebase
//                DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("notices");
//                batchesRef.child(code).setValue(batch);

                dialog.dismiss();
            }
        });





        dialog.show();
    }
}