package com.example.movinder;

import java.io.Serializable;
import java.util.Date;

public class ChatMsg implements Serializable {
    private String message;
    private Date date;
    private int user_id;

    public ChatMsg(String message, Date date, int user_id) {
        this.message = message;
        this.date = date;
        this.user_id = user_id;
    }
    public ChatMsg() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


}
