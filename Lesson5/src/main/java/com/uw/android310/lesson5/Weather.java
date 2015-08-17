package com.uw.android310.lesson5;

<<<<<<< HEAD
/**
 * Created by DOu on 7/20/15.
 */
public class Weather {
    private int id;
=======

public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
>>>>>>> lab_5-2_retrofit_solution

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
<<<<<<< HEAD
=======

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id: " + id)
                .append("main: " + main)
                .append("description: " + description)
                .append("icon: " + icon)
                .toString();
    }
>>>>>>> lab_5-2_retrofit_solution
}
