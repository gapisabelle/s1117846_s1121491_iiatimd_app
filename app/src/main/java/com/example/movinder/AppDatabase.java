package com.example.movinder;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movinder.Card;

@Database(entities = {Card.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao cardDao();
}