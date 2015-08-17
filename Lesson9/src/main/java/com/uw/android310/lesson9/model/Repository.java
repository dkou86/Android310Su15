package com.uw.android310.lesson9.model;


public class Repository {
    int id;
    Contributor owner;
    String name;
    public String full_name;
    String description;
    String url;

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
