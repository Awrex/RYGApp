package com.ryg;

import android.util.Log;

/** Skill
 * Created by Alex Stewart
 *
 * A data class that mostly contains getters and setters.
 * Includes a few additional conditions to check for and methods to make displaying the eventual card easier.
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

    /** getName
     * If the category of the skill is "Skills" or "Tactical" which involves multiple different positions
     * The name of the skil then also includes the name of the position and then the original name.
     * @return name
     */
    public String getName(){
    if(category.equals("Skills")||category.equals("Tactical"))
    return positionName + " - " + name;
        else
            return name;
    }

    /**
     * Gets the description of the skill, then makes sure the next character after each new line is an actual letter.
     * XML formats the descriptions to where it inserts tabs into each new line on the description.
     * This method is run each time from the MainController when a new skill is created.
     */
    public void breakDesc(){
        char[] n = description.toCharArray();
        String newName = "";
        newName += n[0];
        int errnum = 0;
        if(newName.length() != 0) {
            for (int j = 1; j < n.length; j++) {
                if (n[j] == '\n') {
                    try {
                        newName += n[j];
                        newName += '\n';
                        if (j < n.length)
                        {
                            j++;
                            errnum = 1;
                            n[j] = '\n';
                            if(j < n.length)
                                j++;
                        }

                        while (n[j] == ' ' && j < n.length) {
                            errnum = 2;
                            j++;
                        }
                            j--;
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        Log.d(e.getMessage(),name + j + ", ERRNUM = " + errnum);
                    }
                } else
                    newName += n[j];
                errnum = 0;
            }
        }
        description = newName;
        /*    description = description.replace(":", ":\n\n");
            description = description.replace(".", ".\n\n");
            description = description.replace("*", "*\n\n");
            description = description.replace("e.\n\ng.\n\n", "e.g.");
            description = description.replace("i.\n\ne.\n\n", "i.e.");
            description = description.replace(">", "\n>");
        description = description.replace("may include some of the following:\n\n","may include some of the following:\n");
        description = description.replace("incl.\n\n" , "incl.");*/
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
