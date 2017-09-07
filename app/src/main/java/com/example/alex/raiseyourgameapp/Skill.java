package com.example.alex.raiseyourgameapp;

/**
 * Created by Alex on 15/08/2017.
 */

public class Skill {
    private String name;
    private String category;
    private String description;
    private int levelReq;
    private String genderReq;
    private int moreInfo;
    private String infoPath;
    private String sportName;
    private String shortDesc;
    private String positionName;
    Skill()
    {

    }

    public Skill(String name, String category, String description, int levelReq, String genderReq, int moreInfo, String infoPath, String sportName, String positionName) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.levelReq = levelReq;
        this.genderReq = genderReq;
        this.moreInfo = moreInfo;
        this.infoPath = infoPath;
        this.sportName = sportName;
        this.positionName = positionName;
    }
    public String getShortDesc() { return shortDesc; }
    public void setShortDesc(String shortDesc) { this.shortDesc = shortDesc;}
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

    public int getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(int moreInfo) {
        this.moreInfo = moreInfo;
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

    public String getGenderReq() {
        return genderReq;
    }

    public void setGenderReq(String genderReq) {
        this.genderReq = genderReq;
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
