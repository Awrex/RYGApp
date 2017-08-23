package com.example.alex.raiseyourgameapp;

import java.util.ArrayList;

/**
 * Created by Alex on 15/08/2017.
 */

public class Position {
    private String name;
    private String level;
    private String sportName;
    private String imagePath;
    private ArrayList<Skill> skillList;
    public Position(ArrayList<Skill> pulledList) {
        skillList = new ArrayList<Skill>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public ArrayList<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<Skill> skillList) {
        this.skillList = skillList;
    }
}
