package com.example.infs3605;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IndustryDetailActivity extends AppCompatActivity {

    private TextView industryTV;
    private TextView industryOpenTV;
    private TextView industryLimitTV;
    private TextView industryDistancingTV;
    private TextView industryHygieneTV;
    private TextView industryWellbeingTV;
    private TextView industryGeneralTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.industry_detail);

        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        String industry = sp1.getString("Industry", null);
        int industryInt = Integer.parseInt(industry);

        industryTV = findViewById(R.id.industryName);
        industryOpenTV = findViewById(R.id.industryOpenText);
        industryLimitTV = findViewById(R.id.industryLimitText);
        industryDistancingTV = findViewById(R.id.industryDistancingText);
        industryHygieneTV = findViewById(R.id.industryHygieneText);
        industryWellbeingTV = findViewById(R.id.industryWellbeingText);
        industryGeneralTV = findViewById(R.id.industryGeneralText);


        //add data to textviews

        Industry industrySelected = MainActivity.getIndustryById(industryInt);
        industryTV.setText(industrySelected.getName());
        industryOpenTV.setText(industrySelected.getOpen());
        industryLimitTV.setText(industrySelected.getLimits());
        industryDistancingTV.setText(industrySelected.getDistancing());
        industryHygieneTV.setText(industrySelected.getHygiene());
        industryWellbeingTV.setText(industrySelected.getWellbeing());
        //12 is the general information column of the API
        industryGeneralTV.setText(MainActivity.getIndustryById(12).getGeneral());

    }
}
