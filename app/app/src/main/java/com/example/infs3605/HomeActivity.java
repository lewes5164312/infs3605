package com.example.infs3605;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.infs3605.Notifications.CHANNEL_1_ID;
import static com.example.infs3605.Notifications.CHANNEL_2_ID;

public class HomeActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    private static final String caseURL =
            "https://interactive.guim.co.uk/docsdata/1q5gdePANXci8enuiS4oHUJxcxC13d6bjMRSicakychE.json?fbclid=IwAR0Zpu2s_UDMGyWuwmeSLRnv3t366Cg_ivAh_PzXdJ1tXZd1sGDQxPHqeYc";

    int latestcount=0;
    int previouscount=0;

    TextView growthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set default fragment
        HomeFragment fragment = new HomeFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_slot, fragment);
        fragmentTransaction.commit();

        BottomNavigationView navMenu = findViewById(R.id.bottomNavBar);

        navMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_home) {
                    Fragment fragment = new HomeFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_alerts) {
                    Fragment fragment = new AlertsFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_settings) {
                    Fragment fragment = new SettingsFragment();
                    swapFragment(fragment);
                    return true;
                }
                return false;
            }
        });

        notificationManager = NotificationManagerCompat.from(this);

    }


    //send notification for the latest update for the industry
    //ONLY if sharedprefernce controlled by the switch is on

    public void sendOnChannel1 (View v){

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        if (sp1.getBoolean("NotificationSetting", true) == false) {
            return;
        }

        String industry = sp1.getString("Industry", null);
        int industryInt = Integer.parseInt(industry);

        Industry latestIndustry = MainActivity.getIndustryById(industryInt);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_check)
                .setContentTitle (latestIndustry.getNotification1Title())
                .setContentText(latestIndustry.getNotification1Text())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify (1, notification);
    }

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String date = sdfDate.format(now);
        return date;
    }

    public static String getDateDaysAgo(int Days) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        //based on https://stackoverflow.com/questions/6439946/java-date-problems-finding-the-date-x-days-ago
        Date xDaysAgo = Date.from( Instant.now().minus( Duration.ofDays( Days ) ) );
        String date = sdfDate.format(xDaysAgo);
        return date;
    }

    public void getLatestUpdate (View v){
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        if (sp1.getBoolean("NotificationSetting", true) == false) {
            return;
        }
//currently set to subtract cumulative cases from 3 days ago from 2 days ago to get the latest cases
//this ensures that the data exist however may not be the latest update
//in the future can consider validation
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                caseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("sheets");
                            JSONArray array = jsonObject1.getJSONArray("updates");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                if  ((o.getString("State").equals("NSW")) && (o.getString("Date").equals(getDateDaysAgo(2))))
                                {
                                    latestcount = Integer.parseInt(o.getString("Cumulative case count"));
                                }
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

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET,
                caseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("sheets");
                            JSONArray array = jsonObject1.getJSONArray("updates");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                if  ((o.getString("State").equals("NSW")) && (o.getString("Date").equals(getDateDaysAgo(3))))
                                {
                                    previouscount = Integer.parseInt(o.getString("Cumulative case count"));

                                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                                            .setSmallIcon(R.drawable.ic_check)
                                            .setContentTitle ("NSW records " + (latestcount - previouscount) + " new cases")
                                            .setContentText("On Date " + getDateDaysAgo(2))
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                            .build();

                                    notificationManager.notify (1, notification);
                                }
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
        requestQueue.add(stringRequest2);

    }


    //Swapfragment method From NYT demo (INFS3634)
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_slot, fragment);
        fragmentTransaction.commit();
    }

    //edit settings
    public void editSettingClick(View v) {
        Intent intent = new Intent(this, EditSettingsActivity.class);
        startActivity(intent);
    }

    //when user clicks on notifications in home fragment -> swap to alerts fragment
    public void notificationTextClick (View v) {
        AlertsFragment fragment = new AlertsFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_slot, fragment);
        fragmentTransaction.commit();

    }

}
