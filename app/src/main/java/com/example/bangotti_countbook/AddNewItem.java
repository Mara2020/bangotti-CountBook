package com.example.bangotti_countbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
    }

    public void saveItem(View View) {
        finish();
    }
}
