package com.example.noteiceboard;

import static com.example.noteiceboard.Constants.TOPIC;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHome extends AppCompatActivity {
    private EditText title;
    private EditText message;
    private Button button;

    public String newtitle = "";
    public String newmessage= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_home);
        button = findViewById(R.id.submit_button);
        title = findViewById(R.id.first_edit_text);
        message = findViewById(R.id.second_edit_text);


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titletxt = title.getText().toString();
                String messagetxt = message.getText().toString();
                if(!titletxt.isEmpty() && !messagetxt.isEmpty())
                {
                    PushNotification notification = new PushNotification(new NotificationData(titletxt,messagetxt),TOPIC);
                    sendNotification(notification);
                }

            }


        });

    }
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(NotificationHome.this, "success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NotificationHome.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(NotificationHome.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}