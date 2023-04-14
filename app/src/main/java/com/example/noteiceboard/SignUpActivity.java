package com.example.noteiceboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, fname,lname,username,mobile;

    private Button signupButton;
    private TextView loginRedirectText;
    private static final String TAG = "SignupActivity";
    String[] items =  {"Teacher","Student"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fname = findViewById(R.id.signup_firstname);
        lname = findViewById(R.id.signup_lastname);
        username = findViewById(R.id.signup_username);
        mobile = findViewById(R.id.signup_mobile);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
                autoCompleteTxt.setText(item);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String fname1 = fname.getText().toString().trim();
                String lname1 = lname.getText().toString().trim();
                String username1 = username.getText().toString().trim();
                String mobile1 = mobile.getText().toString().trim();
                String role1 = autoCompleteTxt.getText().toString().trim();
                if (email1.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if (fname1.isEmpty()){
                    fname.setError("First name cannot be empty");
                }
                if (lname1.isEmpty()){
                    lname.setError("Last name cannot be empty");
                }
                if (username1.isEmpty()){
                    username.setError("Username cannot be empty");
                }
                if (mobile1.isEmpty()){
                    mobile.setError("Mobile cannot be empty");
                }
                if (pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }
                if (role1.isEmpty()){
                    autoCompleteTxt.setError("Role cannot be empty");
                }
                else{
                    auth.createUserWithEmailAndPassword(email1, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                User newUser = new User(fname1,lname1,username1,email1,pass,mobile1,role1);
                                db.collection("users")
                                        .add(newUser)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
//                                          ------------------------------------------
// Get a reference to the Firebase database
//                                                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

// Get a reference to the "students" node in the database
//                                                DatabaseReference studentsRef = databaseRef.child("students");

// Generate a unique student ID using the username and email
                                                String studentId = System.currentTimeMillis() + "-" + new Random().nextInt(1000000);

// Create a new Student object with the required attributes
                                                Map<String, Boolean> batchIds = new HashMap<>();
                                                batchIds.put("1111",true);
                                                Student student = new Student(studentId, fname1 + " " + lname1, email1, batchIds);

// Check if the "students" node exists in the database
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference studentsRef = database.getReference("students");

                                                studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            // "students" node exists in the database
//                                                            // Add the new student ID to the list
//                                                            // Create a new child node with the student ID as the key and the student object as the value
                                                            DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("students");
                                                            batchesRef.child(studentId).setValue(student);

                                                        } else {
                                                            // "students" node does not exist in the database, create it with the initial student ID
                                                            DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference().child("students");
                                                            batchesRef.child(studentId).setValue(student);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        // Handle any errors here
                                                        Toast.makeText(SignUpActivity.this, "database error boss", Toast.LENGTH_SHORT).show();
                                                    }
                                                });




//                                          ---------------------------------------------



                                                Log.d(TAG, "User added with ID: " + documentReference.getId());
                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding user", e);
                                            }
                                        });

                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}