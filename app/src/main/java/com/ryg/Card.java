package com.ryg;

import android.graphics.Color;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Alex Stewart
 * Data Class filled with purely getters and setters of each card in the system.
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
    private String comment = "";
    private String shortDesc;
    private Boolean selected;

    public int getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(int moreInfo) {
        this.moreInfo = moreInfo;
    }

    public Card() {

    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private HashMap<String,Integer> colour = new HashMap<>();

    public Card(int num, String name, String category, String description, String shortDesc, int moreInfo, String infoPath, String sportName, String positionName, int rating, int priority, String comment, int selected) {
        this.num = num;


        this.name = name;
        this.category = category;
        this.description = description;
        this.shortDesc = shortDesc;
        this.moreInfo = moreInfo;
        this.infoPath = infoPath;
        this.sportName = sportName;
        this.positionName = positionName;
        this.rating = rating;
        this.priority = priority;
        this.comment = comment;
        if (selected == 1)
            this.selected = Boolean.TRUE;
        else
            this.selected = Boolean.FALSE;
        //references position
        colour.put("Wellbeing",Color.YELLOW);
        colour.put("Character/Team",Color.RED);
        colour.put("Fielder",Color.DKGRAY);
        colour.put("Bowler",Color.MAGENTA);
        colour.put("Captaincy",Color.CYAN);
        colour.put("Physical",Color.parseColor("#FF9933"));
        colour.put("Mental", Color.GREEN);
        colour.put("Keeper", Color.LTGRAY);
        colour.put("Batter", Color.BLUE);
    }

    /**
     * @return colour - based on what the position name of the card is.
     */
    public int getColour()
    {
        if(category.equals("Skills")|| category.equals("Tactical"))
            return colour.get(positionName);
        else
            return colour.get(category);
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getShortDesc() { return shortDesc;}
    public void setShortDesc() {this.shortDesc = shortDesc;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }


    public String getDescription() {
        return description;
    }


    public String getInfoPath() {
        return infoPath;
    }


    public String getPositionName() {
        return positionName;
    }

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

}
