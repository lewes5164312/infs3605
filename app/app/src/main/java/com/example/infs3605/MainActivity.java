package com.example.infs3605;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.infs3605.Notifications.CHANNEL_1_ID;
import static com.example.infs3605.Notifications.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://newsapi.org/v2/top-headlines?q=covid&country=au&from=" + getDate() + "&sortBypopularity&apiKey=8ef436de7eae4edda9e7bda8b6c41ef6";

    private static final String industryURL = "https://api.jsonbin.io/b/5f0c16e85d4af74b012b85a3/1";

    private static final HashMap<Integer, Article> articleList = new HashMap<>();
    private static final HashMap<Integer, Industry> industryList = new HashMap<>();

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        loadArticleData();
        loadIndustryData();
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

    //get date (used in API retrieval)

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String date = sdfDate.format(now);
        return date;
    }

    private void loadArticleData() {
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

    private void loadIndustryData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

//retrieve industries from API
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                industryURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("industry");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                industryList.put(i, new Industry(
                                        i,
                                        o.getString("Name"),
                                        o.getString("Open"),
                                        o.getString("Limits"),
                                        o.getString("Distancing"),
                                        o.getString("Entitlements"),
                                        o.getString("Hygiene"),
                                        o.getString("Records")
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



    public static ArrayList<Article> getAllArticles() {
        return new ArrayList<Article>((List) Arrays.asList(articleList.values().toArray()));
    }

    public static ArrayList<Industry> getAllIndustries() {
        return new ArrayList<Industry>((List) Arrays.asList(industryList.values().toArray()));
    }

    public static Industry getIndustryById(int itemID) {
        return industryList.get(itemID);
    }

}