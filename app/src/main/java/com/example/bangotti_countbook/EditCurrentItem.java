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

/* The purpose of this class is to act as the page where users can edit an Item (directly edit
* all values except for the date). There is a count up (+) and count down (-) button which
* automatically saves. The save button allows a user to save any changes made on the page.
* The reset button allows a user to set the current counter value to the initial counter. The
* delete button will remove the Item from the List. */
public class EditCurrentItem extends AppCompatActivity {
    private int position, oldCurrentValue;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private List<Item> itemList;
    private Item currentItem;
    private String json;
    private EditText editTextName, editTextComment, editTextInitialCount, editTextCurrentCount;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_current_item);

        // grab the index of the item in the list
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");

        // grab the entire list from shared preferences
        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = appSharedPrefs.edit();
        gson = new Gson();
        try {
            json = appSharedPrefs.getString("CounterList", "");
            Type type = new TypeToken<List<Item>>() {}.getType();
            itemList = gson.fromJson(json, type);
        } catch (Exception e) {
            itemList = new ArrayList<Item>();
        }

        // grab the reference to the item object within the list
        currentItem = itemList.get(position);

        // set all of the values for the item to be viewed & edited
        editTextName = (EditText) findViewById(R.id.nameItem);
        editTextComment = (EditText) findViewById(R.id.commentValue);
        editTextInitialCount = (EditText) findViewById(R.id.initialValue);
        editTextCurrentCount = (EditText) findViewById(R.id.currentValue);
        dateView = (TextView) findViewById(R.id.dateView);
        editTextName.setText(currentItem.getName());
        editTextComment.setText(currentItem.getComment());
        editTextInitialCount.setText(Integer.toString(currentItem.getInitialCount()));
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        oldCurrentValue = currentItem.getCurrentCount();
        dateView.setText(currentItem.getDate());
    }

    /* Function that saves the new list into shared preferences */
    private void commitEdits() {
        json = gson.toJson(itemList);
        prefsEditor.putString("CounterList",json);
        prefsEditor.commit();
    }

    /* Called when the user hits the '+' button to increment the current counter by 1
    * (date is updated since current counter was affected) */
    public void addItem(View view){
        currentItem.incrementCounter();
        currentItem.setDate(new Date());
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

    /* Called when the user hits the '-' button to decrement the current counter by 1
    * (date is updated since current counter was affected) */
    public void subtractItem(View view) {
        currentItem.decrementCounter();
        currentItem.setDate(new Date());
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

    /* Called when the user hits the 'RESET' button to set the current counter to the initial counter
    * (date is updated since current counter was affected) */
    public void resetCurrentValue(View view) {
        currentItem.setCurrentCount(currentItem.getInitialCount());
        currentItem.setDate(new Date());
        editTextCurrentCount.setText(Integer.toString(currentItem.getCurrentCount()));
        dateView.setText(currentItem.getDate());
        commitEdits();
    }

    /* Called when the user hits the 'SAVE' button update editable values such as
     * name, inital counter, current counter, and comment
     * (date is only updated if current counter was affected) */
    public void saveAllEdits(View view) {
        Boolean properEntry = true;

        // throw errors if the user does not input into the mandatory fields (name and counters)
        if (editTextName.getText().toString().equals("")) {
            editTextName.setError("Name of item is required!");
            properEntry = false;
        }
        if (editTextInitialCount.getText().toString().equals("")){
            editTextInitialCount.setError("Initial value is required!");
            properEntry = false;
        }
        if (editTextCurrentCount.getText().toString().equals("")){
            editTextCurrentCount.setError("Current value is required!");
            properEntry = false;
        }

        // if all mandatory fields are filled, save changes to the item in the list update shared preferences
        if (properEntry) {
            currentItem.setName(editTextName.getText().toString());
            currentItem.setInitialCount(Integer.parseInt(editTextInitialCount.getText().toString()));
            if (!(editTextComment.getText().toString().equals(""))) {
                currentItem.setComment(editTextComment.getText().toString());
            }
            if (oldCurrentValue != Integer.parseInt(editTextCurrentCount.getText().toString())) {
                currentItem.setCurrentCount(Integer.parseInt(editTextCurrentCount.getText().toString()));
                currentItem.setDate(new Date());
            }
            commitEdits();
            finish();
        }
    }

    /* Called when the user hits the 'DELETE' button to remove this counter from the list */
    public void deleteFromList(View view) {
        itemList.remove(position);
        commitEdits();
        finish();
    }

}
