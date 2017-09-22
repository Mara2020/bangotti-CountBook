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
        this.date = new Date();
        this.initialCount = initialCount;
        this.currentCount = initialCount;
    }

    public Item(String name, int initialCount, String comment) {
        this.name = name;
        this.date = new Date();
        this.initialCount = initialCount;
        this.currentCount = initialCount;
        this.comment = comment;
    }

    public void incrementCounter() {
        this.currentCount += 1;
    }

    public void decrementCounter() {
        if (this.currentCount > 0) {
            this.currentCount -= 1;
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /* get the date in the form of a printable string */
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getInitialCount() {
        return initialCount;
    }

    /* set the string for viewing in the list */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString = sdf.format(date);
        return name + "\t\t\t\t\t" + Integer.toString(currentCount) + "\t\t\t\t\t" + newDateString;
    }

}
