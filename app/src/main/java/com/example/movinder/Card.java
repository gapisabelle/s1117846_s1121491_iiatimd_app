package com.example.movinder;

public class Card {
    private int id;
    private String title;
    private String rating;
    private String time;
    private String date;
    private String categories;
    private String imageURI;

    public Card(int id, String title, String rating, String time, String date, String categories, String imageURI) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.time = time;
        this.date = date;
        this.categories = categories;
        this.imageURI = imageURI;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
