package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    String[] registerIndustry = { "IndustryA", "IndustryB", "IndustryC", "IndustryD"};

    int currentSelection = 0;

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText abnET;
    private EditText industryET;
    private EditText emailET;
    private EditText phoneET;
    private EditText passwordET;
    private EditText passwordConfirmET;

    private Button registerBTN;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameET = findViewById(R.id.firstname);
        lastNameET = findViewById(R.id.lastname);
        abnET = findViewById(R.id.ABN);
        emailET = findViewById(R.id.email);
        phoneET = findViewById(R.id.number);
        passwordET = findViewById(R.id.password);
        passwordConfirmET = findViewById(R.id.passwordConfirm);

        registerBTN = findViewById(R.id.register);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        Spinner spinner = (Spinner) findViewById(R.id.industry_spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,registerIndustry);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
    }

    private void registerUser() {

        final String email;
        String password;
        String passwordConfirm;
        final String name;

        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        passwordConfirm = passwordConfirmET.getText().toString();
        name = firstNameET.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email is blank, please enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Name is blank, please enter Email", Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Password is blank, please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length()<6) {
            Toast.makeText(getApplicationContext(), "Password needs to be minimum 6 characters!", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals(passwordConfirm));
        else {
            Toast.makeText(getApplicationContext(), "Passwords do not match, please try again!", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getApplicationContext(), "Attempting to create user...", Toast.LENGTH_LONG).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful! Please log in.", Toast.LENGTH_LONG).show();
                            String user_id=mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user = mDatabase.child(user_id);
                            current_user.child("name").setValue(name);
                            current_user.child("email").setValue(email);
                            current_user.child("industry").setValue(String.valueOf(currentSelection));

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

       // writeNewUserToDB(email,password);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    currentSelection = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
