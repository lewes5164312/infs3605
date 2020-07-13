package com.example.infs3605;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private TextView notificationTV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rv_articles);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.getAllArticles());

        recyclerView.setAdapter(articleAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("Login",0);
        String name =sp1.getString("Name", null);
        String industry = sp1.getString("Industry", null);
        notificationTV = view.findViewById(R.id.textViewNotifications);
        notificationTV.setText("Welcome Back, "+ name);

        return view;
    }


}