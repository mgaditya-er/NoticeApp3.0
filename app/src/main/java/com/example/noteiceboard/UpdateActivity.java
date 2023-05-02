package com.example.noteiceboard;


import static com.example.noteiceboard.Constants.TOPIC;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    ImageView updateImage;
    Button updateButton,updatePdf;
    EditText updateDesc, updateTitle, updateLang;
    String title, desc, lang;
    String imageUrl,pdfUrl;
    String key, oldImageURL,oldpdfurl;
    Uri uri,uri1;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        String batchcode = getIntent().getStringExtra("code");
        setTitle(batchcode);
        updateButton = findViewById(R.id.updateButton);
        updateDesc = findViewById(R.id.updateDesc);
        updateImage = findViewById(R.id.updateImage);
        updateLang = findViewById(R.id.updateLang);
        updateTitle = findViewById(R.id.updateTitle);
        updatePdf = findViewById(R.id.changePDFButton);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(UpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateTitle.setText(bundle.getString("Title"));
            updateDesc.setText(bundle.getString("Description"));
            updateLang.setText(bundle.getString("Language"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
            oldpdfurl = bundle.getString("pdf");

        }
        databaseReference = FirebaseDatabase.getInstance().getReference("notices").child(batchcode).child(key);

        updateImage.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(UpdateActivity.this, "Selected file: " + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();

                            updatePdf.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            updatePdf.setText("File changed");
                        } else {
                            Toast.makeText(UpdateActivity.this, "No file Changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        updatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pdfPicker = new Intent(Intent.ACTION_GET_CONTENT);
                pdfPicker.setType("application/pdf");
                activityResultLauncher1.launch(pdfPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this, AdminBatches.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri1.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
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
                imageUrl = urlImage.toString();
                //
                storageReference1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri urlPDF = uriTask.getResult();
                        pdfUrl = urlPDF.toString();
                        updateData();

                        dialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });

                //




                dialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
        lang = updateLang.getText().toString();

        DataClass dataClass = new DataClass(title, desc, lang, imageUrl,pdfUrl);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    StorageReference reference2 = FirebaseStorage.getInstance().getReferenceFromUrl(oldpdfurl);

                    reference.delete();
                    reference2.delete();
                    String titletxt = "Notice "+title+" Updated";
                    String messagetxt = " Please Check";

                    PushNotification notification = new PushNotification(new NotificationData(titletxt,messagetxt),TOPIC);
                    sendNotification(notification);
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, AdminBatches.class);

                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(UpdateActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdateActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}