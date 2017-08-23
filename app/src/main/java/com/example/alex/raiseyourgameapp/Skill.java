package com.example.alex.raiseyourgameapp;

/**
 * Created by Alex on 15/08/2017.
 */

public class Skill {
    private String name;
    private String category;
    private String description;
    private int levelReq;
    private int genderReq;
    private boolean moreInfo;
    private String rating;
    private String priority;
    private String infoPath;
    private String sportName;
    private String positionName;
    Skill()
    {

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

    public int getLevelReq() {
        return levelReq;
    }

    public void setLevelReq(int levelReq) {
        this.levelReq = levelReq;
    }

    public int getGenderReq() {
        return genderReq;
    }

    public void setGenderReq(int genderReq) {
        this.genderReq = genderReq;
    }

    public boolean isMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(boolean moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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
}
