package com.example.movinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "rating")
    public String rating;
    @ColumnInfo(name = "language")
    public String language;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "categories")
    public String categories;
    @ColumnInfo(name = "image")
    public String imageURI;
    @ColumnInfo(name = "liked")
    public int liked = 0;
    @ColumnInfo(name = "userId")
    public int userId = -1;
    @ColumnInfo(name = "username")
    public String username = "";

    @Ignore
    public Card(int id, String title, String rating, String language, String date, String categories, String imageURI) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.language = language;
        this.date = date;
        this.categories = categories;
        this.imageURI = imageURI;
    }
    public Card(int id, String title, String rating, String language, String date, String categories, String imageURI, int liked, int userId, String username) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.language = language;
        this.date = date;
        this.categories = categories;
        this.imageURI = imageURI;
        this.liked = liked;
        this.userId = userId;
        this.username = username;
    }

    @Ignore
    public Card() {

    }

    public int getUserId() { return liked; }
    public void setUserId(int liked) {
        this.liked = liked;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getLiked() { return liked; }
    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String time) {
        this.language = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
