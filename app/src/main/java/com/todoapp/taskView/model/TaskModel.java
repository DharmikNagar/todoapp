package com.todoapp.taskView.model;

public class TaskModel {

    private int id;
    private int category;
    private int status;
    private String title;
    private String subtitle;
    private String timing;

    public TaskModel(int id, String title,int status, String subtitle, String timing,int category) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.subtitle = subtitle;
        this.timing = timing;
        this.category = category;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getStatus() { return status; }
    public String getSUBTitle() { return subtitle; }
    public String getTiming() { return timing; }
    public int getCategory() { return category; }

}
