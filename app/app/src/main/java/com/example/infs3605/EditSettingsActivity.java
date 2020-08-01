package com.example.infs3605;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class EditSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    String[] settingIndustry = {"Choose Industry", "Office Environment", "Retail and Grocery", "Construction & Tradespeople", "General"};

    int currentSelection = 0;
    Button industryEditBTN;
    Button businessNameEditBTN;
    EditText businessName;
    Button phoneNumberEditBTN;
    EditText phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        Spinner spinner = (Spinner) findViewById(R.id.industry_spinner_settings);
        spinner.setOnItemSelectedListener(this);
        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        String industry = sp1.getString("Industry", null);
        int industryInt = Integer.parseInt(industry);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, settingIndustry);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        industryEditBTN = findViewById(R.id.industryEditButton);
        industryEditBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                applyIndustryEdit();
            }
        });

        businessNameEditBTN = findViewById(R.id.businessEditButton);
        businessNameEditBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                applyBusinessNameEdit();
            }
        });

        phoneNumberEditBTN = findViewById(R.id.phoneEditButton);
        phoneNumberEditBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                applyPhoneNumberEdit();
            }
        });
    }

    private void applyIndustryEdit() {
        if (currentSelection==0) {
            Toast.makeText(getApplicationContext(), "Please Select Industry!", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();
        String industry = String.valueOf(currentSelection-1);
        ed.putString("Industry",industry);
        ed.commit();



        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String emailCurrent = sp1.getString("Email", null);

        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");
        Query email = users.orderByChild("email").equalTo(emailCurrent);
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot email: snapshot.getChildren()){
                    email.getRef().child("industry").setValue(String.valueOf(currentSelection-1));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Toast.makeText(getApplicationContext(), "Industry Edit Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void applyBusinessNameEdit() {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();

        businessName = findViewById(R.id.businessNameEdit);

        final String name;
        name = businessName.getText().toString();

        ed.putString("BusinessName",name);
        ed.commit();

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String emailCurrent = sp1.getString("Email", null);

        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");
        Query email = users.orderByChild("email").equalTo(emailCurrent);
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot email: snapshot.getChildren()){
                    email.getRef().child("businessname").setValue(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Toast.makeText(getApplicationContext(), "Business Edit Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void applyPhoneNumberEdit() {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();

        phoneNumber= findViewById(R.id.phoneNumberEdit);

        final String phone;
        phone = phoneNumber.getText().toString();

        ed.putString("PhoneNumber",phone);
        ed.commit();

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String emailCurrent = sp1.getString("Email", null);

        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");
        Query email = users.orderByChild("email").equalTo(emailCurrent);
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot email: snapshot.getChildren()){
                    email.getRef().child("phonenumber").setValue(phone);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Toast.makeText(getApplicationContext(), "Phone Number Edit Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentSelection = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
