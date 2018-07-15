package com.example.aditya.nimo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    EditText editText;
    public static TextView textView;

    public static JSONArray jsonArray;
    public static RecyclerView recyclerView;
    public static BaseAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_query);
        button = findViewById(R.id.imageButton2);
        textView = (TextView) findViewById(R.id.text);
        textView.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                String string1 = string.replaceAll("\\s+", "");
                if (!string1.matches("")) {

                    backround n = new backround(string, getApplicationContext());
                    n.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "put a Query in search box",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}
