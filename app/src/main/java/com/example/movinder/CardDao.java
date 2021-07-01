package com.example.movinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movinder.Card;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    List<Card> getAll();

    @Query("SELECT * FROM card WHERE id IN (:userIds)")
    List<Card> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM card WHERE title LIKE :title LIMIT 1")
    Card findByTitle(String title);

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    Card findById(String id);
    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    Card findById(int id);

    @Query("SELECT COUNT(*) FROM card WHERE id = :id LIMIT 1")
    boolean exists(String id);
    @Query("SELECT COUNT(*) FROM card WHERE id = :id LIMIT 1")
    boolean exists(int id);

    @Insert
    void insertAll(Card... cards);

    @Query("DELETE FROM card WHERE id = :id")
    void deleteById(int id);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM card")
    void deleteAll();
}