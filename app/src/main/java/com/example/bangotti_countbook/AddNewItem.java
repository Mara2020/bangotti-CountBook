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

/* The purpose of this class is to create new Items and add them to the list of counters on the
* main page. */
public class AddNewItem extends AppCompatActivity {
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private Gson gson;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        // grab the entire list from shared preferences
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        sharedPrefsEditor = sharedPrefs.edit();
        gson = new Gson();
        itemList = new ArrayList<Item>();
        String json;
        try {
            json = sharedPrefs.getString("CounterList", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
            itemList = new ArrayList<Item>();
        }
    }

    public void saveItem(View view) {
        String name, number, comment;
        int initialCount;
        Boolean properEntry = true;

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
            Item item = new Item(name, initialCount, comment);
            itemList.add(item);

            String json = gson.toJson(itemList);
            sharedPrefsEditor.putString("CounterList",json);
            sharedPrefsEditor.commit();
            finish();
        }

    }
}
