package com.example.bangotti_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by biancaangotti on 2017-09-16.
 */

public class Item {
    private String name, comment;
    private Date date;
    private int initialCount, currentCount;

    public Item(String name, int initialCount) {
        this.name = name;
        this.date = new Date(System.currentTimeMillis());
        this.initialCount = initialCount;
        this.currentCount = initialCount;
    }

    public Item(String name, int initialCount, String comment) {
        this.name = name;
        this.date = new Date(System.currentTimeMillis());
        this.initialCount = initialCount;
        this.currentCount = initialCount;
        this.comment = comment;
    }

    public void incrementCounter() {
        this.currentCount += 1;
    }

    public void decrementCounter() {
        this.currentCount -= 1;
    }

    public Date getDate() {
        return date;
    }

    public Date setDate() {
        return date;
    }

    public String setName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String setComment() {
        return comment;
    }

    public String getComment() {
        return comment;
    }

    public int setCurrentCount() {
        return currentCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int setInitialCount() {
        return initialCount;
    }

    public int getInitialCount() {
        return initialCount;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString = sdf.format(date);
        return name + "\t\t\t\t\t" + Integer.toString(currentCount) + "\t\t\t\t\t" + newDateString;
    }

}
