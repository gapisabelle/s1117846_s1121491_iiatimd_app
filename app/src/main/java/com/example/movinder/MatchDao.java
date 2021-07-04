package com.example.movinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movinder.Match;

import java.util.List;

@Dao
public interface MatchDao {
    @Query("SELECT * FROM match")
    List<Match> getAll();

    @Query("SELECT * FROM match WHERE id IN (:userIds)")
    List<Match> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM match WHERE id = :id LIMIT 1")
    Match findById(String id);
    @Query("SELECT * FROM match WHERE id = :id LIMIT 1")
    Match findById(int id);

    @Query("SELECT COUNT(*) FROM match WHERE id = :id LIMIT 1")
    boolean exists(String id);
    @Query("SELECT COUNT(*) FROM match WHERE id = :id LIMIT 1")
    boolean exists(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Match... matches);

    @Query("DELETE FROM match WHERE id = :id")
    void deleteById(int id);

    @Delete
    void delete(Match match);

    @Query("DELETE FROM match")
    void deleteAll();
}