package com.example.aditya.nimo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {

    JSONArray jsonArray;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView imageView;
        public String pageid;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.t1);
            desc = (TextView) view.findViewById(R.id.t2);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public BaseAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagewiki pagewik = new pagewiki(myViewHolder.pageid, viewGroup.getContext());
                pagewik.execute();
            }
        });

        context = viewGroup.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        JSONObject jsonobject = null;
        String pageid = null;
        String description = null;
        String imageURL = null;
        String title = null;
        try {
            jsonobject = jsonArray.getJSONObject(i);
            pageid = jsonobject.getString("pageid");
            title = jsonobject.getString("title");
            imageURL = jsonobject.getJSONObject("thumbnail").getString("source");
            description = jsonobject.getJSONObject("terms").getJSONArray("description").getString(0);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackground: " + pageid + " " + title + "  " + imageURL + " " + description);
        myViewHolder.title.setText(title);
        myViewHolder.desc.setText(description);
        myViewHolder.pageid = pageid;


        URL url = null;
        try {
            url = new URL(imageURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(context)
                .load(imageURL)
                .into(myViewHolder.imageView);


    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
