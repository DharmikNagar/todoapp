package com.todoapp.Fragment.Home.model;

public class todolist_model {
    private String title;
    private int id;

    public todolist_model(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
