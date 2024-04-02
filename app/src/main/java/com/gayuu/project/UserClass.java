package com.gayuu.project;

public class UserClass {
    private String Title;
    private String Description;
    private String StartTime;

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }


    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserClass(String title, String description, String startTime) {
        Title = title;
        Description = description;
        StartTime = startTime;

    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getStartTime() {
        return StartTime;
    }
    public UserClass() {
    }
}
