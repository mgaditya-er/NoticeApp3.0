package com.example.noteiceboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLang;
    ImageView detailImage;

    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    String pdfUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String batchcode = getIntent().getStringExtra("code");
        setTitle(batchcode);
        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        detailLang = findViewById(R.id.detailLang);
        Button downloadButton = findViewById(R.id.downloadButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(bundle.getString("Language"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            pdfUrl = bundle.getString("Pdf");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

// Set an OnClickListener to the download button
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the PDF URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdfUrl));
                startActivity(intent);
            }
        });


        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
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
                            deleteButton.setVisibility(View.VISIBLE);
                            deleteButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
                                    FirebaseStorage storage = FirebaseStorage.getInstance();

                                    StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            reference.child(key).removeValue();
                                            Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                        }
                                    });
                                }
                            });
                            editButton.setVisibility(View.VISIBLE);
                            editButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                                            .putExtra("code",batchcode)
                                            .putExtra("Title", detailTitle.getText().toString())
                                            .putExtra("Description", detailDesc.getText().toString())
                                            .putExtra("Language", detailLang.getText().toString())
                                            .putExtra("Image", imageUrl)
                                            .putExtra("pdf",pdfUrl)
                                            .putExtra("Key", key);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }
            });

        }

    }

}
