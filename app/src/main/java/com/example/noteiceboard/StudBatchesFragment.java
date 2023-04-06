package com.example.noteiceboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudBatchesFragment extends Fragment {

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private ListBatchAdapter2 adapter;
    private List<Batch> batchList = new ArrayList<>();

    private FirebaseAuth mAuth;
    private String currentUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stud_batches, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserEmail = mAuth.getCurrentUser().getEmail();

        addButton = rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show popup or any other action you want to perform on click
                showPopup();
            }
        });

        recyclerView = rootView.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("batches");

        batchesRef.orderByChild("emails").startAt(currentUserEmail).endAt(currentUserEmail + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Batch batch = snapshot.getValue(Batch.class);
                        if (batch != null) {
                            batchList.add(batch);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // No batch found for the current user
                    Toast.makeText(getContext(), "No batch found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ListBatchAdapter2(getContext(), batchList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void showPopup() {
        // Inflate layout for popup
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

        // Find views in popup layout
        TextView titleTextView = popupView.findViewById(R.id.title_textview);
        EditText batchCodeEditText = popupView.findViewById(R.id.batchcode_edittext);
        Button okButton = popupView.findViewById(R.id.ok_button);
        Button cancelButton = popupView.findViewById(R.id.cancel_button);

        // Set title for popup
        titleTextView.setText("Add Batch");

        // Create and show popup
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(popupView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listeners for buttons
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get batch code from edit text and perform action
                String batchCode = batchCodeEditText.getText().toString();
                DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("batches");

                batchesRef.child(batchCode).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // the batch with the given code exists
                            List<String> emails = new ArrayList<>();
                            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (snapshot.getKey().equals("emails")) {
                                    emails = (List<String>) snapshot.getValue();
                                    if (emails == null) {
                                        emails = new ArrayList<>();
                                    } else if (emails.contains(userEmail)) {
                                        Toast.makeText(getApplicationContext(), "You have already joined this batch", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    break;
                                }
                            }
                            emails.add(userEmail);
                            batchesRef.child(batchCode).child("emails").setValue(emails)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Added to batch!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Failed to add to batch!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            // the batch with the given code does not exist
                            Toast.makeText(getApplicationContext(), "Batch not found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // handle the error
                        Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss(); // Close popup
            }

            private Context getApplicationContext() {
                return getActivity().getApplicationContext(); // if you are using this method in a fragment
                // OR
                // if you are using this method in an activity
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Close popup
            }
        });
    }

}
