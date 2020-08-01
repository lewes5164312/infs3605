package com.example.infs3605;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AlertsFragment extends Fragment {

Switch alertSwitch;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_alerts, container, false);

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