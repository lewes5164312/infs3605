package com.example.infs3605;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
private Button signupBTN;
    private Button loginBTN;
    private Button fakeloginBTN;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        loginBTN = findViewById(R.id.login);
        fakeloginBTN = findViewById(R.id.fakeLogin);
        signupBTN = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        //fakeloginBTN.setVisibility(View.INVISIBLE);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupClick(v);
            }
        });
    }

    public void login() {

        String email;
        String password;



        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        Toast.makeText(getApplicationContext(), "Attempting to login...", Toast.LENGTH_LONG).show();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email is blank, please enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Password is blank, please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successful Login, welcome back!", Toast.LENGTH_LONG).show();
                            String user_id=mAuth.getCurrentUser().getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

//add details from firebase DB to shared preferences
//reference https://stackoverflow.com/questions/47621936/how-can-i-get-the-child-values-from-firebase-database
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference userRef = rootRef.child("Users").child(user_id);
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    SharedPreferences sp = getSharedPreferences("Login", 0);
                                    SharedPreferences.Editor ed = sp.edit();
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    ed.putString("Name",name);
                                    String lastname = dataSnapshot.child("lastname").getValue(String.class);
                                    ed.putString("LastName",lastname);
                                    String email = dataSnapshot.child("email").getValue(String.class);
                                    ed.putString("Email",email);
                                    String businessName = dataSnapshot.child("businessname").getValue(String.class);
                                    ed.putString("BusinessName",businessName);
                                    String phoneNumber= dataSnapshot.child("phonenumber").getValue(String.class);
                                    ed.putString("PhoneNumber",phoneNumber);
                                    String industry = dataSnapshot.child("industry").getValue(String.class);
                                    ed.putString("Industry",industry);
                                    ed.commit();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            userRef.addListenerForSingleValueEvent(eventListener);


                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signupClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void fakeLoginClick(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

}
