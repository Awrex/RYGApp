package com.ryg;

/**
 * Created by Alex Stewart
 * Position, Data Class
 * Uses getters and setters, picked is an int rather than a boolean for ease of storing in a database.
 */

public class Position {
    private String name;
    private String imagePath;
    private String sportName;
    private int picked;

    public Position(String name, String imagePath, String sportName, int picked) {
        this.name = name;
        this.sportName = sportName;
        this.imagePath = imagePath;
        this.picked = picked;
    }

    public int getPicked() {

        return picked;
    }

    public void setPicked(int picked) {
        this.picked = picked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
