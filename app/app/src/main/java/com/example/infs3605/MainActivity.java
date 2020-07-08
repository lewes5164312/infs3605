package com.example.infs3605;

<<<<<<< HEAD
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
=======
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.infs3605.Notifications.CHANNEL_1_ID;
import static com.example.infs3605.Notifications.CHANNEL_2_ID;
>>>>>>> Notification

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    private static final String URL = "https://newsapi.org/v2/top-headlines?q=covid-19&country=au&from=2020-06-15&sortBypopularity&apiKey=8ef436de7eae4edda9e7bda8b6c41ef6";

    private static final HashMap<Integer, Article> articleList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        loadData();

    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

//retrieve articles from API
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("articles");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                articleList.put(i, new Article(
                                        i,
                                        o.getString("author"),
                                        o.getString("title"),
                                        o.getString("description"),
                                        o.getString("url"),
                                        o.getString("urlToImage"),
                                        o.getString("publishedAt"),
                                        o.getString("content")
                                ));


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

<<<<<<< HEAD
    public static ArrayList<Article> getAllArticles() {
        return new ArrayList<Article>((List) Arrays.asList(articleList.values().toArray()));
=======
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
>>>>>>> Notification
    }
}