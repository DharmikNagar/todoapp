package com.todoapp.taskView.model;

public class DateWiseTaskModel {
    private String date;
    private int id;
    private int category;

    public DateWiseTaskModel(int id, String date,int category) {
        this.id = id;
        this.date = date;
        this.category = category;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public int getCategory() { return category; }

    public void setDate(String date)
    {
        this.date = date;
    }
}
