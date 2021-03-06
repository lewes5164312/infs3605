package com.example.infs3605;

import android.content.Context;
import android.content.Intent;
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
    private View covidView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //news article adapter/recycler views
        recyclerView = view.findViewById(R.id.rv_articles);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.getAllArticles());

        recyclerView.setAdapter(articleAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);


        //onclicklistener for covid-19 industry information
        covidView = view.findViewById(R.id.covidLayout);

        covidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, IndustryDetailActivity.class);
                context.startActivity(intent);
            }
        });

        notificationTV = view.findViewById(R.id.textViewNotifications);

        return view;
    }


}