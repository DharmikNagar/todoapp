package com.todoapp.taskView.model;

public class DateWiseTaskModel {
    private String date;
    private int id;

    public DateWiseTaskModel(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() { return id; }
    public String getDate() { return date; }

    public void setDate(String date)
    {
        this.date = date;
    }
}
