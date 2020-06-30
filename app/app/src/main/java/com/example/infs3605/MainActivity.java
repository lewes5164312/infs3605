package com.example.infs3605;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.infs3605.Notifications.CHANNEL_1_ID;
import static com.example.infs3605.Notifications.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        notificationManager = NotificationManagerCompat.from(this);
              editTextTitle  = findViewById(R.id.edit_text_title);
            editTextMessage = findViewById(R.id.edit_text_message);

    }
    public void sendOnChannel1 (View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
    Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
        .setSmallIcon(R.drawable.ic_check)
            .setContentTitle (title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build();

    notificationManager.notify (1, notification);
    }

    public void sendOnChannel2 (View v){

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_check_24)
                .setContentTitle (title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify (2, notification);

    }


}