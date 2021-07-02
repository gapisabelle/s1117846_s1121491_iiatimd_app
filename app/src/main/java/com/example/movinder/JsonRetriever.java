package com.example.movinder;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;

public abstract class JsonRetriever<T> {

    private JsonElement element;

    protected void createJsonConnection(URL url, String method, String body) throws Exception {
        try {
            System.out.println(url.toString());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            httpURLConnection.setRequestProperty("Accept","*/*");
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            OutputStream out = httpURLConnection.getOutputStream();
            OutputStreamWriter w = new OutputStreamWriter(out, "UTF-8");
            w.write(body);
            w.flush();
            w.close();
            out.close();
            InputStream stream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String json = readAll(bufferedReader);

            System.out.println(json);

            this.element = new JsonParser().parse(json);

            stream.close();
        } catch (Exception ex) {
            Log.i("JSON-Retriever", "Little File Not Found Exception!");
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    protected abstract boolean isValidResource();

    public T get() {
        if (this.getElement() == null)
            return null;

        if (!isValidResource())
            return null;

        return getObject();
    }

    public abstract T getObject();

    protected JsonElement getElement() {
        return element;
    }
}
