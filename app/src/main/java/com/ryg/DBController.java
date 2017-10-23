package com.ryg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;


/**
 * Created by Alex Stewart
 * DBController
 * Pulls data such as a list of positions, cards, skills and the athlete data
 * Updates each table
 * Fills data into the card table from the skill table based on skill level
 * At this stage, Sport is included but not used due to possibly having one database for all sports.
 */
public class DBController extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RYG.db";

    private static final String TABLE_SPORT = "Sport";
    private static final String TABLE_POSITION = "Position";

    private static final String SPORT_name = "Name";
    private static final String SPORT_imagePath = "imagePath";

    private static final String POSITION_name = "Name";
    private static final String POSITION_level = "Level";
    private static final String POSITION_sportName = "sportName";
    private static final String POSITION_imagePath = "imagePath";
    private static final String POSITION_picked = "picked";






    public DBController(Context con){
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //

    /** onCreate
     * Creates the database.
     * Left some variables uninitialized due to them being intended for preparedStatements which at this stage are not working.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSport = "CREATE TABLE " + TABLE_SPORT + "(" + SPORT_name + " TEXT PRIMARY KEY, " + SPORT_imagePath+ " TEXT)";
        String createPos = "CREATE TABLE "+ TABLE_POSITION + "(" + POSITION_name + " TEXT PRIMARY KEY, "+ POSITION_level + " INT, "+ POSITION_imagePath + " TEXT, " + POSITION_sportName + " TEXT, " + POSITION_picked + " BOOLEAN)";

        SQLiteStatement stmt = db.compileStatement(createSport);
        stmt.execute();

        stmt = db.compileStatement(createPos);
        stmt.execute();
        //changed to directly inserting text due to complications with preparedStatements
        stmt = db.compileStatement("Create Table Athlete (Name TEXT PRIMARY KEY, Gender TEXT, Email TEXT, DOB TEXT, LEVEL int, Teams TEXT, Country TEXT, Zipcode TEXT, Terms1 int, Terms2 int, Terms3 int, sortTime TEXT, weeksFreq int)");
        stmt.execute();
        /*
        String SKILL_name = "Skill";
        String SKILL_category = "Category";
        String SKILL_description = "Description";
        String SKILL_levelReq = "levelReq";
        String SKILL_genderReq = "genderReq";
        String SKILL_moreInfo = "moreInfo";
        String SKILL_infoPath = "infoPath";
        String SKILL_sportName = "sportName";
        String SKILL_positionName = "positionName";
        */
        stmt = db.compileStatement("CREATE TABLE Skill ( skillName TEXT PRIMARY KEY, Category TEXT, Description TEXT, shortDescription TEXT, levelReq INT, genderReq TEXT, moreInfo BOOLEAN, infoPath TEXT, sportName TEXT, positionName TEXT)");
        stmt.execute();
        /*
        private static final String CARD_name = "Card";
        private static final String CARD_category = "Category";
        private static final String CARD_description = "Description";
        private static final String CARD_moreInfo = "moreInfo";
        private static final String CARD_infoPath = "infoPath";
        private static final String CARD_sportName = "sportName";
        private static final String CARD_positionName = "positionName";
        private static final String CARD_rating = "Rating";
        private static final String CARD_priority = "Priority";
        private static final String CARD_comment = "Comment";
        private static final String CARD_secondComment = "secondComment";
        */
        stmt = db.compileStatement("CREATE TABLE Card (num INT PRIMARY KEY, cardName TEXT, Category TEXT, Description TEXT, shortDescription TEXT, moreInfo BOOLEAN, infoPath TEXT, sportName TEXT, positionName TEXT, Rating INT, Priority INT, Comment TEXT, secondComment TEXT, Selected INT)");
        stmt.execute();
    }
    /** getPositions()
       gets a list of all the positions in the database.
     */
    public ArrayList getPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Position",null);
        if(c!= null) {
            while(c.moveToNext()){
                Position tempPosition = new Position(c.getString(0), c.getString(2), c.getString(3), c.getInt(4));
                positions.add(tempPosition);
            }
        }
        c.close();
        return positions;
    }
    /** updatePosition()
    When a position is selected or unselected, changes that value in the app.
     @param pos (Position)
    */
    public void updatePosition(Position pos){
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put("picked", pos.getPicked());
            String where = "Name='" + pos.getName() + "'";
            try {
                db.update("Position", dataToInsert, where, null);
            } catch (Exception e) {
                String error = e.getMessage();
            }
        }
    /** updatePositions
    When a sort has already been started and the user wants to add positions to their sort -
     This sets every position selected to 1.
     A fail-safe to make sure nothing goes wrong.
     @param positions (ArrayList<String>)
     */
    public void updatePositions(ArrayList<String> positions) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i<positions.size(); i++) {
            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put("picked", 1);
            String where = "Name='" + positions.get(i) + "'";
            try {
                db.update("Position", dataToInsert, where, null);
            } catch (Exception e) {
                String error = e.getMessage();
            }
        }
    }
    /** newAthlete
        When the user resets their app/database, this allows them to keep their athlete data.
     @param a (Athlete)
     */
    public void newAthlete(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", a.getName());
        values.put("Gender", a.getGender());
        values.put("Email", a.getEmail());
        values.put("DOB", a.getDob());
        values.put("LEVEL", a.getSkillLevel());
        values.put("Teams", a.getTeams());
        values.put("Country", a.getLocation());
        values.put("Zipcode", a.getZipCode());
        values.put("Terms1", a.isTerms1());
        values.put("Terms2", a.isTerms2());
        values.put("Terms3", a.isTerms3());
        db.insert("Athlete",null,values);
    }
    /** newAthlete
        Creates a new athlete from scratch, from the CreateUserActivity
     @param a (Athlete)
     */
    public void addAthlete(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAthlete();
        ContentValues values = new ContentValues();
        values.put("Name", a.getName());
        values.put("Gender", a.getGender());
        values.put("Email", a.getEmail());
        values.put("DOB", a.getDob());
        values.put("LEVEL", a.getSkillLevel());
        values.put("Teams", a.getTeams());
        values.put("Country", a.getLocation());
        values.put("Zipcode", a.getZipCode());
        values.put("Terms1", a.isTerms1());
        values.put("Terms2", a.isTerms2());
        values.put("Terms3", a.isTerms3());
        db.insert("Athlete",null,values);
    }
    /**addWeeks
        Is run instead of addDate when the user sets their weeks for the next sort to be done.
     @param a (Athlete)
     */
    public void addWeeks(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("weeksFreq", a.getWeeks());
        String where = "Name='" + a.getName() + "'";
        try{
            db.update("Athlete",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage();
        }
    }
    /**addDate
     @param a (Athlete)
      Is run instead of addWeeks when the user sets the date for the next sort to be done.
   */
    public void addDate(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("sortTime", a.getDateOf());
        String where = "Name='" + a.getName() + "'";
        try{
            db.update("Athlete",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage();
        }
    }
    /** addPositions
     Inserts the position table using an already created ArrayList of positions.
     @param posList (ArrayList<Position>)
     */
    public void addPositions(ArrayList<Position> posList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String pName = "";
        String imagePath = "";
        String sName = "";
        int picked = 0;
            for (int i = 0; i < posList.size(); i++) {
                pName = posList.get(i).getName();
                imagePath = posList.get(i).getImagePath();
                sName = posList.get(i).getSportName();
                picked = posList.get(i).getPicked();

                String sql = "INSERT INTO Position (Name, imagePath, sportName, picked) VALUES ('" + pName + "', '" + imagePath + "', '" + sName + "'," + picked + ")";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.executeInsert();

            }
        }
    /** getSkills
        Is used to check if the table of skills has items or not.
         Can be used to get a list of all the skills in a table.
     */
    public ArrayList getSkills()
    {
        ArrayList<Skill> skills = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Skill",null);
        if(c != null)
        {
            while(c.moveToNext())
            {
                Skill tempSkill = new Skill(c.getString(0),c.getString(1),c.getString(2),c.getInt(3),c.getString(4),c.getInt(5),c.getString(6),c.getString(7),c.getString(8));
                skills.add(tempSkill);
            }
            c.close();
        }
        return skills;
    }

    /** addSkills
     * Inserts all the skills from the cards xml file into the skill table.
     * @param skillList (ArrayList)
     */
    public void addSkills(ArrayList<Skill> skillList)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Card");
        String name;
        String category;
        String description;
        int levelReq;
        String genderReq;
        int moreInfo;
        String infoPath;
        String sName;
        String pName;
        String sDesc;
        for (int i = 0; i < skillList.size(); i++) {
            name = skillList.get(i).getName();
            category = skillList.get(i).getCategory();
            description = skillList.get(i).getDescription();
            levelReq = skillList.get(i).getLevelReq();
            genderReq = skillList.get(i).getGenderReq();
            moreInfo = skillList.get(i).getMoreInfo();
            infoPath = skillList.get(i).getInfoPath();
            sName = skillList.get(i).getSportName();
            pName = skillList.get(i).getPositionName();
            sDesc = skillList.get(i).getShortDesc();
            String sql = "INSERT INTO Skill (skillName, Category, Description, shortDescription, levelReq, genderReq, moreInfo, infoPath, sportName, positionName) VALUES ('" + name + "', '" + category + "', '" + description + "', '" + sDesc + "', " + levelReq + ",'" + genderReq + "', " + moreInfo + ", '" + infoPath + "', '" +sName + "', '" +pName+ "')";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.executeInsert();

        }
    }
    /** getCards
        Gets an ArrayList of all the cards in the Card table.
     */
    public ArrayList getCards()
    {
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Card",null);
        if(c != null)
        {
            while(c.moveToNext())
            {
                Card tempCard = new Card(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getString(6),c.getString(7),c.getString(8),c.getInt(9),c.getInt(10),c.getString(11),c.getInt(13));
                cards.add(tempCard);
            }
        }
        return cards;
    }
    /** getCard
        Not currently needed, is used to check the number of cards in a particular category
        This is to create a radar chart.
     */
    /*
    public int getCard(String s)
    {
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Card Where category = '" + s +"'",null);
        if(c != null)
        {
            return c.getCount();
        }
        else
            return 0;
    }
    */

    /**
     * Delete methods, just deletes everything inside a table.
     */
    public void deleteSkills()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Skill");
    }
    public void deleteAthlete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Athlete");
    }

    /**Update methods
     * Updates a particular attribute of card
     * @Param Card
     */
    public void updateRating(Card card)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("Rating", card.getRating());
        String where = "cardName='" + card.getName() + "'";
        try{
            db.update("Card",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
        dataToInsert.clear();
    }
    public void updateComment(Card card)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("Comment", card.getComment());
        String where = "cardName='" + card.getName() + "'";
        try{
            db.update("Card",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
        dataToInsert.clear();
    }
    public void updateSelected(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        int num;
        if(card.getSelected())
            num = 1;
        else
            num = 0;
        dataToInsert.put("Selected", num);
        String where = "cardName='" + card.getName() + "'";
        try{
            db.update("Card",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
    }
    public void updatePriority(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("Priority", card.getPriority());
        String where = "cardName='" + card.getName() + "'";
        try{
            db.update("Card",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
    }

    /** updateAll
     * Used when the clear method on the ReviewSortActivity is run.
     * Changes everything the user can change to the default value.
     * @param cards (ArrayList)
     */
    public void updateAll(ArrayList<Card> cards)
    {
        for(int i = 0; i < cards.size(); i++)
        {
            cards.get(i).setRating(0);
            cards.get(i).setSelected(Boolean.FALSE);
            cards.get(i).setPriority(0);
            cards.get(i).setComment("");
            updateRating(cards.get(i));
            updatePriority(cards.get(i));
            updateComment(cards.get(i));
            updateSelected(cards.get(i));
        };
    }

    /**
     * Inserts every record in the Skill table into the Card table, if conditions are met.
     */
    public void createCards(){
        int num = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sName = "";
        String category = "";
        String desc = "";
        int levelReq = 0;
        String genderReq = "";
        int moreInfo = 0;
        String infoPath = "";
        String sportName = "";
        String positionName = "";
        String sDesc = "";
        Athlete a = getAthlete();
        Cursor c = db.rawQuery("Select * FROM Skill", null);
            if(!(c.getCount() < 1)) {
                c.moveToFirst();
                do {
                    num++;
                    sName = c.getString(0);
                    category = c.getString(1);
                    desc = c.getString(2);
                    sDesc = c.getString(3);
                    levelReq = c.getInt(4);
                    genderReq = c.getString(5);
                    moreInfo = c.getInt(6);
                    infoPath = c.getString(7);
                    sportName = c.getString(8);
                    positionName = c.getString(9);
                    /*Checks if the skill level of the user is high enough to add it to the table.
                      If it is not it loops to the next skills
                      If it is, it adds it to the table. */
                    if (a.getSkillLevel() >= levelReq) {
                        String sql = "INSERT INTO Card (num, cardName, Category, Description, shortDescription, moreInfo, infoPath, sportName, positionName) VALUES (" + num + ", '" + sName + "', '" + category + "', '" + desc + "', '" + sDesc + "', " + moreInfo + ", '" + infoPath + "', '" + sportName + "', '" + positionName + "')";
                        SQLiteStatement statement = db.compileStatement(sql);
                        statement.executeInsert();
                    }
                } while (c.moveToNext());
            }
        }

    /**
     * Gets every record in the athlete table which there should only be 1, however does allow for more.
     * Returns the first record of the table as there should only be 1.
     * @return Athlete
     */
    public Athlete getAthlete()
    {
        ArrayList<Athlete> aList = new ArrayList<>();
        String name = "", gender = "", email = "", DOB = "", teams = "", loc = "", zip = "", dateOf = "";
        int sLevel = 0, terms1 = 0, terms2 = 0, terms3 = 0, weeks = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Athlete", null);
            if(c != null)
            {
                c.moveToFirst();
                name = c.getString(0);
                gender = c.getString(1);
                email = c.getString(2);
                DOB = c.getString(3);
                sLevel = c.getInt(4);
                teams = c.getString(5);
                loc = c.getString(6);
                zip = c.getString(7);
                terms1 = c.getInt(8);
                terms2 = c.getInt(9);
                terms3 = c.getInt(10);
                try {
                    dateOf = c.getString(11);
                    weeks = c.getInt(12);
                } catch (Exception e)
                {
                    dateOf = "20/10/2017";
                    weeks = 0;
                }
            }
            c.close();
            aList.add(new Athlete(name,email,DOB,gender,sLevel,teams,loc,zip,terms1,terms2,terms3,dateOf,weeks));
        if(aList.size() != 0)
            return  aList.get(0);
        else
            return null;
    }

    /**
     * Required onUpgrade method, is not yet used but may need to be used in the future.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Sport");
        db.execSQL("DROP TABLE IF EXISTS Position");
        db.execSQL("DROP TABLE IF EXISTS Athlete");
        db.execSQL("DROP TABLE IF EXISTS Skill");
        db.execSQL("DROP TABLE IF EXISTS Card");

        onCreate(db);
    }
}
