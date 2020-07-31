package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
    }

    //button onClick methods with intents

    public void englishClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "en");
        startActivity(intent);
    }

    public void chineseClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "zh");
        startActivity(intent);
    }

    public void espanolClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "es");
        startActivity(intent);
    }

    public void koreanClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "ko");
        startActivity(intent);
    }

    public void japaneseClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "ja");
        startActivity(intent);
    }

    public void russianClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "ru");
        startActivity(intent);
    }

    public void indianClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "hi");
        startActivity(intent);
    }

    public void arabicClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("languageClicked", "ar");
        startActivity(intent);
    }
}
