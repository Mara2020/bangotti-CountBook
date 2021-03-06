package com.example.bangotti_countbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/* The purpose of this class is to act as the main page of the app, displaying the total number
* of counters, the list of counters and an add-to-list button. Counters are referred to by the
* "Item" class. */
public class MainActivity extends AppCompatActivity {

    private ListView itemListView;
    private ArrayAdapter<Item> adapter;
    private Gson gson;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor sharedPrefsEditor;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemListView = (ListView) findViewById(R.id.itemListView);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        sharedPrefsEditor = sharedPrefs.edit();
        gson = new Gson();

        // set the listener so that if you click an item in the list, you can view it
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewCurrentItem.class);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });
    }

    /* Called when user returns to main menu */
    @Override
    protected void onStart() {
        super.onStart();
        TextView totalItemCount = (TextView) findViewById(R.id.totalItemCount);

        // grab the list from the SharedPreferences
        try {
            String json = sharedPrefs.getString("CounterList", "");
            Type type = new TypeToken<List<Item>>(){}.getType();
            itemList = gson.fromJson(json, type);
            // Capture the total number of items and set the string
            totalItemCount.setText(Integer.toString(itemList.size()));
        }
        catch (Exception e) {
            itemList = new ArrayList<Item>();
            totalItemCount.setText("0");
            String json = gson.toJson(itemList);
            sharedPrefsEditor.putString("CounterList",json);
            sharedPrefsEditor.commit();
        }

        // capture the list of items into the display list
        adapter = new ArrayAdapter<Item>(this, R.layout.list_item, itemList);
        itemListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /* Called when the user taps the Add Item button */
    public void addItem(View View) {
        Intent intent = new Intent(this, AddNewItem.class);
        startActivity(intent);
    }

}
