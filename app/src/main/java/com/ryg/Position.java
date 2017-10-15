package com.ryg;

import java.util.ArrayList;

/**
 * Created by Alex on 15/08/2017.
 */

public class Position {
    private String name;
    private String level;
    private String imagePath;
    private String sportName;
    private int picked;
    private ArrayList<Skill> skillList;
    public Position(ArrayList<Skill> pulledList) {
        skillList = new ArrayList<Skill>();

    }

    public Position(String name, String level, String imagePath, String sportName, int picked) {
        this.name = name;
        this.level = level;
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
