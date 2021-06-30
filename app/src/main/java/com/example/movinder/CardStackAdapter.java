package com.example.movinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private Card[] localDataSet;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipable_card_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardTitle.setText(localDataSet[position].getTitle());
        holder.cardTime.setText(localDataSet[position].getTime());
        holder.cardRating.setText(localDataSet[position].getRating());
        holder.cardYear.setText(localDataSet[position].getDate());
        holder.cardCategories.setText(localDataSet[position].getCategories());

        Glide.with(holder.cardImage).load(localDataSet[position].getImageURI()).into(holder.cardImage);
//        holder.cardImage.setText(localDataSet[position].getImageURI());
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardTitle;
        public TextView cardTime;
        public TextView cardRating;
        public TextView cardYear;
        public TextView cardCategories;
        public ImageView cardImage;

        public ViewHolder(View view) {
            super(view);

            cardTitle = view.findViewById(R.id.card_title);
            cardTime = view.findViewById(R.id.card_time);
            cardRating = view.findViewById(R.id.card_rating);
            cardYear = view.findViewById(R.id.card_year);
            cardCategories = view.findViewById(R.id.card_categories);
            cardImage = view.findViewById(R.id.card_image);
        }
    }

    public CardStackAdapter(Card[] dataSet) {
        localDataSet = dataSet;
    }


}
