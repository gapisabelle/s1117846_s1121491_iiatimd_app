package com.example.movinder;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movinder.Card;

@Database(entities = {Card.class, Match.class}, version = 4)
@TypeConverters({DbConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao cardDao();
    public abstract MatchDao matchDao();
}