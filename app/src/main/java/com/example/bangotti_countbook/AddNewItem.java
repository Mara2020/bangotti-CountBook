package com.example.bangotti_countbook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        EditText editTextName = (EditText) findViewById(R.id.editText);
        EditText editTextInitialCount = (EditText) findViewById(R.id.editText2);
        EditText editTextComment = (EditText) findViewById(R.id.editText5);
        name = editTextName.getText().toString();
        number = editTextInitialCount.getText().toString();
        comment = editTextComment.getText().toString();

        if (name.equals("")) {
            editTextName.setError("Name of item is required!");
            properEntry = false;
        }
        if (number.equals("")){
            editTextInitialCount.setError("Initial value is required!");
            properEntry = false;
        }

        if (properEntry) {
            initialCount = Integer.parseInt(number);
            Item item = new Item(name, initialCount, comment);
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

            Gson gson = new Gson();
            String json = gson.toJson(item);
            prefsEditor.putString("MyObject",json);
            prefsEditor.commit();
            finish();
        }

    }
}
