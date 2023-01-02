package com.example.teapot_project.model;

public enum Group {
    RED ("RED"),
    GREEN ("GREEN"),
    BLUE("BLUE");

    private final String title;

    Group(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Group{" +
                "title='" + title + '\'' +
                '}';
    }
}
