package com.example.aditya.nimo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class pagewiki extends AsyncTask {
    String url = null;
    JSONObject jsonObject;
    String getUrl;
    String pageid;
    Context context;

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Intent intent = new Intent(context, webview.class);
        intent.putExtra("key", getUrl);
        context.startActivity(intent);
    }

    public pagewiki(String pageid, Context context) {
        this.pageid = pageid;
        this.context = context;
        url = "https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=" +
                pageid +
                "&inprop=url&format=json";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            jsonObject = getJSONObjectFromURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            getUrl = jsonObject.getJSONObject("query").getJSONObject("pages").getJSONObject(pageid).getString("fullurl");
            Log.d("kkk", "doInBackground: " + getUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }
}
