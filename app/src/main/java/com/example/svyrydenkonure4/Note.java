package com.example.svyrydenkonure4;

public class Note {
    private String title;
    private String description;
    private long createdTime;
    private String importance;
    private String uri;

    public Note(String title, String description, long createdTime, String importance, String uri) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
        this.importance = importance;
        this.uri = uri;
    }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }

    public String getImportance() { return importance; }

    public void setImportance(String importance) {
        this.importance = importance;
    }

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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return this.title + this.description + this.importance;
    }
}
