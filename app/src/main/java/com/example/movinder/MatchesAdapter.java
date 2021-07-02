package com.example.movinder;

import android.icu.util.ULocale;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private Card[] localDataSet;

    public MatchesAdapter() {
        localDataSet = new Card[0];
    }

    public Card[] getLocalDataSet() {
        return localDataSet;
    }

    public Card getCard(int index) {
        return localDataSet[index];
    }

    public void setLocalDataSet(Card[] dataSet) {
        localDataSet = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_card_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardTitle.setText(localDataSet[position].getTitle());
//        String fullLanguage = localDataSet[position].getLanguage();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            fullLanguage = ULocale.forLanguageTag(localDataSet[position].getLanguage()).getDisplayLanguageWithDialect();
//        }
//        holder.cardLanguage.setText(fullLanguage);

//        holder.cardRating.setText(localDataSet[position].getRating());
//        holder.cardYear.setText(localDataSet[position].getDate());
//        holder.cardCategories.setText(localDataSet[position].getCategories());

        Glide.with(holder.cardImage).load(localDataSet[position].getImageURI()).into(holder.cardImage);
//        holder.cardImage.setText(localDataSet[position].getImageURI());
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardTitle;
        public TextView cardLanguage;
        public TextView cardRating;
        public TextView cardYear;
        public TextView cardCategories;
        public ImageView cardImage;

        public ViewHolder(View view) {
            super(view);

            cardTitle = view.findViewById(R.id.card_title);
            cardLanguage = view.findViewById(R.id.card_language);
            cardRating = view.findViewById(R.id.card_rating);
            cardYear = view.findViewById(R.id.card_year);
            cardCategories = view.findViewById(R.id.card_categories);
            cardImage = view.findViewById(R.id.card_image);
        }
    }

    public MatchesAdapter(Card[] dataSet) {
        localDataSet = dataSet;
    }


}
