package com.example.bangotti_countbook;

import java.util.Date;

/**
 * Created by biancaangotti on 2017-09-16.
 */

public class Item {
    private String name;
    private Date date;
    private int initialCount;
    private int currentCount;
    private String comment;

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

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getInitialCount() {
        return initialCount;
    }

}
