package com.blog.demo.core.models;

public class NavItems {
    private String title;
    private String link;

    public NavItems(String title, String link) {
        this.title = title;
        this.link = link;
    }
    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
}