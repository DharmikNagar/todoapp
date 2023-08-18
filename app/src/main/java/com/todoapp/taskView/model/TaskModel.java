package com.todoapp.taskView.model;

public class TaskModel {

    private int id;
    private int category;
    private String title;
    private String subtitle;
    private String timing;

    public TaskModel(int id, String title, String subtitle, String timing,int category) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.timing = timing;
        this.category = category;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSUBTitle() { return subtitle; }
    public String getTiming() { return timing; }
    public int getCategory() { return category; }

}
