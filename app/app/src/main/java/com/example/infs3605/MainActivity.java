package com.example.infs3605;

import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

    //newsAPI
    private static final String URL = "https://newsapi.org/v2/top-headlines?q=covid&country=au&from=" + getDate() + "&sortBypopularity&apiKey=8ef436de7eae4edda9e7bda8b6c41ef6";

    //industryAPI
    private static final String industryURL = "https://api.jsonbin.io/b/5f0c16e85d4af74b012b85a3/7";

    private static final HashMap<Integer, Article> articleList = new HashMap<>();
    private static final HashMap<Integer, Industry> industryList = new HashMap<>();

    Translate translate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //passes in the language selected in the past activity

        Bundle bundle = getIntent().getExtras();
        final String languageSelected = bundle.getString("languageClicked");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        /*final ProgressDialog dialog= ProgressDialog.show(this,"Doing something", "Please wait....",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (languageSelected == "en") {
                        loadArticleDataEng();
                        loadIndustryDataEng();
                        dialog.dismiss();
                    }
                    else {
                        loadArticleData(languageSelected);
                        loadIndustryData(languageSelected);
                        dialog.dismiss();
                    }
                      }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start(); */


        //if statement to only translate if not enlish
        if (languageSelected.equals("en")){
            loadArticleDataEng();
            loadIndustryDataEng();
        }
        else {
            loadArticleData(languageSelected);
            loadIndustryData(languageSelected);
            Toast.makeText(getApplicationContext(), "Translating: Please wait ~1 min", Toast.LENGTH_LONG).show();
        }


    }

    //get date (used in API URL retrieval) for the current date

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String date = sdfDate.format(now);
        return date;
    }

    private void loadArticleData(final String languageToTrans) {

//retrieve articles from API
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
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
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadIndustryData(final String languageToTrans) {

//retrieve industries from API
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                industryURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("industry");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                industryList.put(i, new Industry(
                                        i,
                                        translate(o.getString("name"), languageToTrans),
                                        translate(o.getString("open"), languageToTrans),
                                        translate(o.getString("limits"), languageToTrans),
                                        translate(o.getString("distancing"), languageToTrans),
                                        translate(o.getString("hygiene"), languageToTrans),
                                        translate(o.getString("wellbeing"), languageToTrans),
                                        translate(o.getString("general"), languageToTrans),
                                        translate(o.getString("notification1Title"), languageToTrans),
                                        translate(o.getString("notification1Text"), languageToTrans),
                                        translate(o.getString("notification2Title"), languageToTrans),
                                        translate(o.getString("notification2Text"), languageToTrans)
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
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadArticleDataEng() {

//retrieve articles from API without translation
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
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
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadIndustryDataEng() {

//retrieve industries from API without translation
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                industryURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("industry");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                industryList.put(i, new Industry(
                                        i,
                                        o.getString("name"),
                                        o.getString("open"),
                                        o.getString("limits"),
                                        o.getString("distancing"),
                                        o.getString("hygiene"),
                                        o.getString("wellbeing"),
                                        o.getString("general"),
                                        o.getString("notification1Title"),
                                        o.getString("notification1Text"),
                                        o.getString("notification2Title"),
                                        o.getString("notification2Text")
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
        //takes in a String and also a target language, and then returns the string of the target language
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