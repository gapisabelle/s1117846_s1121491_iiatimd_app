package com.example.movinder;

import com.google.gson.JsonObject;

import java.net.URL;

public class UserCredentials extends JsonRetriever  {

    public UserCredentials(String name, String password) {
        try{
            this.createJsonConnection(new URL("http://127.0.0.1:8000"), "POST", String.format("{email:%s, password:%s}", name, password));

        }
        catch(Exception e){
            e.printStackTrace();

        }

    }

    @Override
    protected boolean isValidResource() {
        return false;
    }

    @Override
    public Object getObject() {



        JsonObject object = this.getElement().getAsJsonObject();
        //String name = object.get("name").getAsJsonObject();
        return null;
    }
}
