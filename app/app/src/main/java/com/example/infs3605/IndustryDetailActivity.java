package com.example.infs3605;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IndustryDetailActivity extends AppCompatActivity {

    private TextView industryTV;
    private TextView industryOpenTV;
    private TextView industryLimitTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.industry_detail);

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String industry = sp1.getString("Industry", null);
        int industryInt = Integer.parseInt(industry);

        industryTV = findViewById(R.id.industryName);
        industryOpenTV = findViewById(R.id.industryOpenText);
        industryLimitTV = findViewById(R.id.industryLimitText);

        //add data to textviews

        Industry industrySelected = MainActivity.getIndustryById(industryInt);
        industryTV.setText(industrySelected.getName());
        industryOpenTV.setText(industrySelected.getOpen());
        industryLimitTV.setText(industrySelected.getLimits());


    }
}
