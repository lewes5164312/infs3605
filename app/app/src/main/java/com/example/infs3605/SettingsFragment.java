package com.example.infs3605;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {

    private TextView nameTV;
    private TextView industryTV;
    private TextView businessNameTV;
    private TextView emailTV;
    private TextView phoneNumberTV;
    private FirebaseAuth mAuth;
    private Switch alertSwitch;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("Login",0);
        String industry = sp1.getString("Industry", null);
        String name = sp1.getString("Name", null) + " " + sp1.getString("LastName", null);
        String businessName = sp1.getString("BusinessName", null);
        String email = sp1.getString("Email", null);
        String phoneNumber = sp1.getString("PhoneNumber", null);
        int industryInt = Integer.parseInt(industry);

        nameTV = view.findViewById(R.id.nameSetting);
        industryTV = view.findViewById(R.id.industrySetting);
        businessNameTV = view.findViewById(R.id.businessNameSetting);
        emailTV = view.findViewById(R.id.emailSetting);
        phoneNumberTV = view.findViewById(R.id.phoneNumberSetting);

        //add data to textviews

        Industry industrySelected = MainActivity.getIndustryById(industryInt);
        nameTV.setText(name);
        industryTV.setText(industrySelected.getName());
        businessNameTV.setText(businessName);
        emailTV.setText(email);
        phoneNumberTV.setText(phoneNumber);

        alertSwitch = view.findViewById(R.id.alertswitch);


        //whether the setting is checked by default is based on sharedpreference
        SharedPreferences sp = getActivity().getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();
        if (sp.getBoolean("NotificationSetting",true) == true ){
            alertSwitch.setChecked(true);}

        //update sharedpreference when changed
        alertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                SharedPreferences sp = getActivity().getSharedPreferences("Login", 0);
                SharedPreferences.Editor ed = sp.edit();
                if (isChecked==true) {
                    ed.putBoolean("NotificationSetting",true);

                }
                else {
                    ed.putBoolean("NotificationSetting", false);
                }
                ed.commit();
            }
        });

        return view;
    }

}