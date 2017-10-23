package com.ryg;

/**
 * Sport
 * Created by Alex Stewart
 * Currently not utilized, will be in the future.
 * Will only contain getters and setters for the values.
 */
import java.util.ArrayList;

public class Sport {
    private String name;
    private String imagePath;
    private ArrayList<Position> posList[];
    public Sport(String name, String imagePath)
    {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Position>[] getPosList() {
        return posList;
    }

    public void setPosList(ArrayList<Position>[] posList) {
        this.posList = posList;
    }


}
