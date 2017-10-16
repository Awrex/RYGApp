package com.ryg;

/**
 * Created by Alex on 15/08/2017.
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
