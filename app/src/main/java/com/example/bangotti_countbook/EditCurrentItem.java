package com.example.bangotti_countbook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EditCurrentItem extends AppCompatActivity {
    private int position;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_current_item);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = appSharedPrefs.edit();
        gson = new Gson();
        String json;
        try {
            json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
        }

        Item currentItem = itemList.get(position);
        // set the values
        EditText editTextName = (EditText) findViewById(R.id.nameItem);
        EditText editTextComment = (EditText) findViewById(R.id.commentValue);
        EditText editTextInitialCount = (EditText) findViewById(R.id.initialValue);
        EditText editTextCurrentCount = (EditText) findViewById(R.id.currentValue);
        TextView dateView = (TextView) findViewById(R.id.date);
        editTextName.setText(currentItem.getName());
        editTextComment.setText(currentItem.getComment());
        editTextInitialCount.setText(Integer.toString(currentItem.getInitialCount()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
    }

}
