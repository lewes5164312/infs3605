package com.example.infs3605;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {

    private TextView nameTV;
    private TextView industryTV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("Login",0);
        String industry = sp1.getString("Industry", null);
        String name = sp1.getString("Name", null);
        int industryInt = Integer.parseInt(industry);

        nameTV = view.findViewById(R.id.nameSetting);
        industryTV = view.findViewById(R.id.industrySetting);

        //add data to textviews

        Industry industrySelected = MainActivity.getIndustryById(industryInt);
        nameTV.setText(name);
        industryTV.setText(industrySelected.getName());

        return view;
    }


}