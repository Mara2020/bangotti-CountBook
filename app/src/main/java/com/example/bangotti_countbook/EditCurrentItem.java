package com.example.bangotti_countbook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditCurrentItem extends AppCompatActivity {
    private int position;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private List<Item> itemList;
    private Item currentItem;
    private String json;
    private EditText editTextName, editTextComment, editTextInitialCount, editTextCurrentCount;
    TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_current_item);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = appSharedPrefs.edit();
        gson = new Gson();
        try {
            json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
        }

        currentItem = itemList.get(position);
        // set the values to be viewed
        editTextName = (EditText) findViewById(R.id.nameItem);
        editTextComment = (EditText) findViewById(R.id.commentValue);
        editTextInitialCount = (EditText) findViewById(R.id.initialValue);
        editTextCurrentCount = (EditText) findViewById(R.id.currentValue);
        dateView = (TextView) findViewById(R.id.date);
        editTextName.setText(currentItem.getName());
        editTextComment.setText(currentItem.getComment());
        editTextInitialCount.setText(Integer.toString(currentItem.getInitialCount()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
    }

    private void commitEdits() {
        json = gson.toJson(itemList);
        prefsEditor.putString("MyObject",json);
        prefsEditor.commit();
    }

    public void addItem(View view){
        currentItem.incrementCounter();
        currentItem.setDate(new Date(System.currentTimeMillis()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

    public void subtractItem(View view) {
        currentItem.decrementCounter();
        currentItem.setDate(new Date(System.currentTimeMillis()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

    public void resetCurrentValue(View view) {
        currentItem.setCurrentCount(currentItem.getInitialCount());
        currentItem.setDate(new Date(System.currentTimeMillis()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

}
