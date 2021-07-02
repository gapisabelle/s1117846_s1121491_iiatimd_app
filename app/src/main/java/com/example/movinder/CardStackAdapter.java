package com.example.movinder;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    //    private Card[] localDataSet;
    private List<Card> localDataSet;
    private Context context;

//    public CardStackAdapter() {
//        localDataSet = new ArrayList<Card>(Arrays.asList(new Card[0]));
//    }
    public CardStackAdapter(Context context) {
        localDataSet = new ArrayList<Card>(Arrays.asList(new Card[0]));
        this.context = context;
    }

    public Card[] getLocalDataSet() {
        return localDataSet.toArray(new Card[0]);
    }

    public Card getCard(int index) {
        return localDataSet.get(index);
    }

    public void setLocalDataSet(List<Card> dataSet) {
        System.out.println("[Movinder localDataSet] CHANGE");
//        localDataSet = Arrays.asList(dataSet);
        localDataSet.clear();
        localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void addToLocalDataSet(List<Card> dataSet) {
        int startPos = localDataSet.size();
        localDataSet.addAll(dataSet);
//        notifyDataSetChanged();
//        notifyItemRangeChanged(startPos, localDataSet.size() - startPos);
        notifyDataSetChanged();
    }

    public int removeCardById(int id) {
//        Card[] newLocalDataSet = new Card[0];
//        List<Card> newLocalDataSet = new ArrayList<Card>();
//        int ind = 0;
//        for (int i = 0; i < localDataSet.length; i++) {
//            if (localDataSet[i].getId() != id) newLocalDataSet.add(localDataSet[i]);
//        }

        for (int i = 0; i < localDataSet.size(); i++) {
            if (localDataSet.get(i).getId() == id) {
                System.out.println("[Movinder CardStackAdapter] removed " + localDataSet.get(i).getTitle());
                localDataSet.remove(i);
                return i;
            }
        }

        return -1;

//        localDataSet = newLocalDataSet.toArray(new Card[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipable_card_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardTitle.setText(localDataSet.get(position).getTitle());
        String fullLanguage = localDataSet.get(position).getLanguage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fullLanguage = ULocale.forLanguageTag(localDataSet.get(position).getLanguage()).getDisplayLanguageWithDialect();
        }
        holder.cardLanguage.setText(fullLanguage);

        holder.cardRating.setText(localDataSet.get(position).getRating());
        holder.cardYear.setText(localDataSet.get(position).getDate());
        holder.cardCategories.setText(localDataSet.get(position).getCategories());

        Glide.with(holder.cardImage).load(localDataSet.get(position).getImageURI()).into(holder.cardImage);
//        holder.cardImage.setText(localDataSet.get(position).getImageURI());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
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

    public CardStackAdapter(Card[] dataSet) {
        localDataSet = Arrays.asList(dataSet);
    }


}
