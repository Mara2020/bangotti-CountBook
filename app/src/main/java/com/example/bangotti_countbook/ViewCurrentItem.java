package com.example.bangotti_countbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewCurrentItem extends AppCompatActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_item);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        List<Item> itemList = new ArrayList<Item>();
        try {
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
        }

        Item currentItem = itemList.get(position);
        // set the values to be viewed
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView commentView = (TextView) findViewById(R.id.commentView);
        TextView initialCountView = (TextView) findViewById(R.id.initialView);
        TextView currentCountView = (TextView) findViewById(R.id.currentView);
        TextView dateView = (TextView) findViewById(R.id.dateView);
        nameView.setText(currentItem.getName());
        if (currentItem.getComment().isEmpty()) {
            commentView.setText("no comment");
        } else {
            commentView.setText(currentItem.getComment());
        }
        initialCountView.setText(Integer.toString(currentItem.getInitialCount()));
        currentCountView.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
    }

    public void editItem(View view) {
        Intent intent = new Intent(ViewCurrentItem.this, EditCurrentItem.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
