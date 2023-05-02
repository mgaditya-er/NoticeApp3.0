package com.example.noteiceboard;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Activity;
import android.app.AlertDialog;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton,uploadpdf;
    EditText uploadTopic, uploadDesc, uploadLang;
    String imageURL,pdfURl;

    Uri uri , uri1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        String batchcode = getIntent().getStringExtra("code");
        setTitle(batchcode);

        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.uploadLang);
        saveButton = findViewById(R.id.saveButton);
        uploadpdf = findViewById(R.id.selectPDFButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });



        ActivityResultLauncher<Intent> activityResultLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri1 = data.getData();
                            // Display the name of the selected file
                            Toast.makeText(UploadActivity.this, "Selected file: " + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();

                            uploadpdf.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            uploadpdf.setText("File Selected");
                        } else {
                            Toast.makeText(UploadActivity.this, "No file selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pdfPicker = new Intent(Intent.ACTION_GET_CONTENT);
                pdfPicker.setType("application/pdf");
                activityResultLauncher1.launch(pdfPicker);
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(batchcode);
            }
        });
    }

    public void saveData(String batchcode){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri1.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                //
                storageReference1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri urlPDF = uriTask.getResult();
                        pdfURl = urlPDF.toString();


                        uploadData(batchcode);
                        dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });

                //

//                uploadData(batchcode);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(String batchcode){

        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();



        DataClass dataClass = new DataClass(title, desc, lang, imageURL,pdfURl);

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("notices").child(batchcode).child(currentDate)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            // Create a notification data payload
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(UploadActivity.this, "channel_id")
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle("New Notice Added")
                                    .setContentText(""+title+""+currentDate)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UploadActivity.this);

                            // notificationId is a unique int for each notification that you must define
                            int notificationId = 1;
                            notificationManager.notify(notificationId, builder.build());

                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });



    }
}