package com.example.movinder;

import android.icu.util.ULocale;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private Match[] localDataSet;
    private ClickListener clickListener;

    public MatchesAdapter() {
        localDataSet = new Match[0];
    }
    public MatchesAdapter(ClickListener clickListener) {
        localDataSet = new Match[0];
        this.clickListener = clickListener;
    }

    public Match[] getLocalDataSet() {
        return localDataSet;
    }

    public Match getMatch(int index) {
        return localDataSet[index];
    }

    public void setLocalDataSet(Match[] dataSet) {
        localDataSet = dataSet;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(int position);
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
        holder.card.setOnClickListener(v -> clickListener.onClick(position));
        holder.cardTitle.setText(localDataSet[position].getCard().getTitle());
        holder.username.setText(localDataSet[position].getUsername());

        Glide.with(holder.cardImage).load(localDataSet[position].getCard().getImageURI()).into(holder.cardImage);
        Glide.with(holder.userImage).load(localDataSet[position].getUserImage()).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout card;
        public TextView cardTitle;
        public TextView cardLanguage;
        public TextView cardRating;
        public TextView cardYear;
        public TextView cardCategories;
        public ImageView cardImage;
        public ImageView userImage;
        public TextView username;

        public ViewHolder(View view) {
            super(view);

            card = view.findViewById(R.id.matchCard);
            cardTitle = view.findViewById(R.id.card_title);
            cardLanguage = view.findViewById(R.id.card_language);
            cardRating = view.findViewById(R.id.card_rating);
            cardYear = view.findViewById(R.id.card_year);
            cardCategories = view.findViewById(R.id.card_categories);
            cardImage = view.findViewById(R.id.card_image);
            userImage = view.findViewById(R.id.matchcard_userimg);
            username = view.findViewById(R.id.matchcard_username);
        }
    }

    public MatchesAdapter(Match[] dataSet) {
        localDataSet = dataSet;
    }


}
