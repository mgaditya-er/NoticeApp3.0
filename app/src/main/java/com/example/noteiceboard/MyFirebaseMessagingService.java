package com.example.noteiceboard;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        // Save the new FCM registration token to the Firebase Realtime Database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference userRef = usersRef.child(userId);
            userRef.child("fcmToken").setValue(token);
        }
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Get the message data payload
        Map<String, String> data = remoteMessage.getData();

        // Get the title and body of the notification from the message data payload
        String title = data.get("title");
        String body = data.get("body");

        // Create an intent to open the app when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
}

