package com.example.carrercruize;

public class joblisting {
    private String title;
    private String description;
    private String location;
    private String salary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    private String datePosted;

    public joblisting(String title, String description, String location, String salary, String datePosted) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.datePosted = datePosted;
    }

    // Getter methods
    // Add additional methods as needed
}
