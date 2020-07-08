package com.example.infs3605;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {
    private ArrayList<Article> articlesToAdapt;
    private Context context;

    public ArticleAdapter(ArrayList<Article> articlesToAdapt){
        this.articlesToAdapt= articlesToAdapt;
        this.context = context;
    }

    public void setData(ArrayList<Article> articlesToAdapt) {
        this.articlesToAdapt = articlesToAdapt;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_entry, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Article articleAtPosition = articlesToAdapt.get(position);

        holder.titleTV.setText(articleAtPosition.getTitle());

        String imageURL = articleAtPosition.getUtlToImage();

        Picasso.with(holder.view.getContext())
                .load(imageURL)
                .into((ImageView) holder.imageIV);


    }

    @Override
    public int getItemCount() {
        return articlesToAdapt.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleTV;
        public ImageView imageIV;

        public ItemViewHolder(View v) {
            super(v);
            view = v.findViewById(R.id.article_view);
            titleTV = v.findViewById(R.id.article_title);
            imageIV = v.findViewById(R.id.article_image);





        }

    }
}
