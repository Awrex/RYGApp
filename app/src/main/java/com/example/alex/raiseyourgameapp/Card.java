package com.example.alex.raiseyourgameapp;

import android.graphics.Color;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Alex on 15/08/2017.
 */

public class Card implements Serializable{
    public int num;
    private String name;
    private String category;
    private String description;
    private int moreInfo;
    private String infoPath;
    private String sportName;
    private String positionName;
    private int rating;
    private int priority;
    private String comment;
    private String secondComment;
    private HashMap<String,Integer> colour = new HashMap<>();

    public Card(int num, String name, String category, String description, int moreInfo, String infoPath, String sportName, String positionName, int rating, int priority, String comment, String secondComment) {
        this.num = num;


        this.name = name;
        this.category = category;
        this.description = description;
        this.moreInfo = moreInfo;
        this.infoPath = infoPath;
        this.sportName = sportName;
        this.positionName = positionName;
        this.rating = rating;
        this.priority = priority;
        this.comment = comment;
        this.secondComment = secondComment;
        //references position
        colour.put("Wellbeing",Color.YELLOW);
        colour.put("Character/Team",Color.RED);
        colour.put("Fielding",Color.DKGRAY);
        colour.put("Bowling",Color.MAGENTA);
        colour.put("Captaincy",Color.CYAN);
        colour.put("Physical",Color.parseColor("#FF9933"));
    }
    public int getColour()
    {
        return colour.get(category);
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(int moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getInfoPath() {
        return infoPath;
    }

    public void setInfoPath(String infoPath) {
        this.infoPath = infoPath;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getBigName(){
        char[] n = name.toCharArray();
        String newName = "";
        newName += n[0];
        for (int j = 1; j < n.length; j++) {
            if (Character.isUpperCase(n[j]))
                newName += " " + n[j];
            else
                newName += n[j];
        }
        return newName;}
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSecondComment() {
        return secondComment;
    }

    public void setSecondComment(String secondComment) {
        this.secondComment = secondComment;
    }
}
