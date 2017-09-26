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

/* The purpose of this class is to be the view page for when a user clicks on a specific counter
* (item). On this page they can see all of the details of the Item. They can go to the edit view
* by clicking the edit button at the bottom corner. */
public class ViewCurrentItem extends AppCompatActivity {
    private int position;
    private List<Item> itemList;
    private Gson gson;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_item);

        // grab the index of the item in the list
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // grab the entire list from shared preferences
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        gson = new Gson();
        try {
            String json = sharedPrefs.getString("CounterList", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
            itemList = new ArrayList<Item>();
        }
        // grab the reference to the item object within the list
        Item currentItem = itemList.get(position);

        // set all of the values for the item to be viewed
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView commentView = (TextView) findViewById(R.id.commentView);
        TextView initialCountView = (TextView) findViewById(R.id.initialView);
        TextView currentCountView = (TextView) findViewById(R.id.currentView);
        TextView dateView = (TextView) findViewById(R.id.dateView);
        nameView.setText(currentItem.getName());
        commentView.setText(currentItem.getComment());
        initialCountView.setText(Integer.toString(currentItem.getInitialCount()));
        currentCountView.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
    }

    /* Called when the user taps the Edit Item button - takes user to editable page */
    public void editItem(View view) {
        Intent intent = new Intent(ViewCurrentItem.this, EditCurrentItem.class);
        intent.putExtra("position",position);
        startActivity(intent);
        finish();
    }
}
