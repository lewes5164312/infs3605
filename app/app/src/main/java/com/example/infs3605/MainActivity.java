package com.example.infs3605;

import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


//test
public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://newsapi.org/v2/top-headlines?q=covid&country=au&from=" + getDate() + "&sortBypopularity&apiKey=8ef436de7eae4edda9e7bda8b6c41ef6";

    private static final String industryURL = "https://api.jsonbin.io/b/5f0c16e85d4af74b012b85a3/3";

    private static final HashMap<Integer, Article> articleList = new HashMap<>();
    private static final HashMap<Integer, Industry> industryList = new HashMap<>();

    Translate translate;

    private TextView notificationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        String languageSelected = bundle.getString("languageClicked");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);


        for (int i=0; i < 2; i++)
        {
            Toast.makeText(getApplicationContext(), "Loading Data: Please wait ~15secs", Toast.LENGTH_LONG).show();
        }
        if (languageSelected == "en") {
            loadArticleDataEng();
            loadIndustryDataEng();
        }
        else {
            loadArticleData(languageSelected);
            loadIndustryData(languageSelected);
        }
        progressDialog.dismiss();

    }

    //get date (used in API retrieval)

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String date = sdfDate.format(now);
        return date;
    }

    private void loadArticleData(final String languageToTrans) {
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
                                        translate(o.getString("title"), languageToTrans),
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

    private void loadIndustryData(final String languageToTrans) {
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
                                        translate(o.getString("Name"), languageToTrans),
                                        translate(o.getString("Open"), languageToTrans),
                                        translate(o.getString("Limits"), languageToTrans),
                                        o.getString("Distancing"),
                                        o.getString("Entitlements"),
                                        o.getString("Hygiene"),
                                        o.getString("Records"),
                                        translate(o.getString("NotificationTitle"), languageToTrans),
                                        translate(o.getString("NotificationText"), languageToTrans)
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

    private void loadArticleDataEng() {
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

    private void loadIndustryDataEng() {
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
                                        o.getString("Records"),
                                        o.getString("NotificationTitle"),
                                        o.getString("NotificationText")
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


    public String translate(String inputText, String target) {
        //based on https://medium.com/@yeksancansu/how-to-use-google-translate-api-in-android-studio-projects-7f09cae320c7

            String translateResult = "";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try (InputStream is = getResources().openRawResource(R.raw.credentials)) {

                //Get credentials:
                final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

                //Set credentials and get translate service:
                TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
                translate = translateOptions.getService();

                Translation translation = translate.translate(inputText, Translate.TranslateOption.targetLanguage(target), Translate.TranslateOption.model("base"));
                translateResult = (translation).getTranslatedText();

            } catch (IOException ioe) {
                ioe.printStackTrace();

            }

            return translateResult;

    }

}