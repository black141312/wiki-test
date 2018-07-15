package com.example.aditya.nimo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import static android.content.ContentValues.TAG;
import static com.example.aditya.nimo.MainActivity.recyclerView;

public class backround extends AsyncTask {
    String URL = null;
    JSONObject jsonArray;
    JSONArray js;
    String imageURL, description;
    String pageid;
    String title;
    HttpURLConnection urlConnection = null;
    Context context;

    public backround(String string, Context context) {
        string.replaceAll("\\s+", "+");
        URL = "https://en.wikipedia.org//w/api.php?action=query&format=json" +
                "&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pith" +
                "umbsize=50&pilimit=10&wbptterms=description&gpssearch=" +
                string +
                "&gpslimit=10";
        this.context = context;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (js != null) {
            MainActivity.textView.setVisibility(View.VISIBLE);
            MainActivity.jsonArray = js;


            MainActivity.mAdapter = new BaseAdapter(js);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(MainActivity.mAdapter);
        } else
            Toast.makeText(context, "may be some connection problem or our server are down", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            jsonArray = getJSONObjectFromURL(URL);


            js = jsonArray.getJSONObject("query").getJSONArray("pages");
            JSONObject jsonobject = js.getJSONObject(0);
            pageid = jsonobject.getString("pageid");
            title = jsonobject.getString("title");

            imageURL = jsonobject.getJSONObject("thumbnail").getString("source");
            description = jsonobject.getJSONObject("terms").getJSONArray("description").getString(0);
            Log.d(TAG, "doInBackground: " + pageid + " " + title + "  " + imageURL + " " + description);


        } catch (UnknownHostException u) {
            Log.e(TAG, "doInBackground: no internet");
        } catch (IOException e) {
            e.printStackTrace();
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
