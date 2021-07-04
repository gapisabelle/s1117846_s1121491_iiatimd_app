package com.example.movinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Match {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "card")
    public Card card;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "chatId")
    public String chatId;
    @ColumnInfo(name = "userImage")
    public String userImage;

    @Ignore
    public Match() {

    }

    public Match(Card card, String username, String chatId, String userImage) {
        this.card = card;
        this.username = username;
        this.chatId = chatId;
        this.userImage = userImage;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
