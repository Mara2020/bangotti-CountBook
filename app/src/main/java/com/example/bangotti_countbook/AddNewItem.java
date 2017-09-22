package com.example.bangotti_countbook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

    }

    public void saveItem(View View) {
        String name, number, comment;
        int initialCount;
        Boolean properEntry = true;

        // grab the entire list from shared preferences
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        List<Item> itemList = new ArrayList<Item>();
        String json;
        try {
            json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
            itemList = new ArrayList<Item>();
        }

        // grab the user's input for the name, comment and initial counter
        EditText editTextName = (EditText) findViewById(R.id.nameItem);
        EditText editTextInitialCount = (EditText) findViewById(R.id.initialValue);
        EditText editTextComment = (EditText) findViewById(R.id.commentValue);
        name = editTextName.getText().toString();
        number = editTextInitialCount.getText().toString();
        comment = editTextComment.getText().toString();

        // throw errors if the user does not input into the mandatory fields (name and initial counter)
        if (name.equals("")) {
            editTextName.setError("Name of item is required!");
            properEntry = false;
        }
        if (number.equals("")){
            editTextInitialCount.setError("Initial value is required!");
            properEntry = false;
        }

        // if all mandatory fields are filled, add item to list and save to shared preferences
        if (properEntry) {
            initialCount = Integer.parseInt(number);
            Item item;
            if (comment.isEmpty()) {
                item = new Item(name, initialCount);
            } else {
                item = new Item(name, initialCount, comment);
            }
            itemList.add(item);

            json = gson.toJson(itemList);
            prefsEditor.putString("MyObject",json);
            prefsEditor.commit();
            finish();
        }

    }
}
