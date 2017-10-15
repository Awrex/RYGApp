package com.ryg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Alex on 14/08/2017.
 */

public class DBController extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RYG.db";

    private static final String TABLE_SPORT = "Sport";
    private static final String TABLE_POSITION = "Position";

    private static final String TABLE_SKILL = "Skill";
    private static final String TABLE_CARD = "Card";



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

    //Left variables uninitialized due to them being intended for preparedStatements which at this stage are not working.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSport = "CREATE TABLE " + TABLE_SPORT + "(" + SPORT_name + " TEXT PRIMARY KEY, " + SPORT_imagePath+ " TEXT)";
        String createPos = "CREATE TABLE "+ TABLE_POSITION + "(" + POSITION_name + " TEXT PRIMARY KEY, "+ POSITION_level + " INT, "+ POSITION_imagePath + " TEXT, " + POSITION_sportName + " TEXT, " + POSITION_picked + " BOOLEAN)";
        String createAthlete = "CREATE TABLE ? ( ? TEXT PRIMARY KEY, ? TEXT, ? TEXT)";
        String createSkill = "CREATE TABLE ? ( ? TEXT PRIMARY KEY, ? TEXT, ? TEXT, ? INT, ? INT, ? BOOLEAN, ? TEXT, ? TEXT, ? TEXT)";
        String createCard = "CREATE TABLE ? ( ? TEXT PRIMARY KEY, ? TEXT, ? TEXT, ? BOOLEAN, ? TEXT, ? TEXT, ? TEXT, ? INT, ? INT, ? TEXT, ? TEXT)";

        SQLiteStatement stmt = db.compileStatement(createSport);
        stmt.execute();

        stmt = db.compileStatement(createPos);
        stmt.execute();

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
    public ArrayList getPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Position",null);
        if(c!= null) {
            while(c.moveToNext()){
                Position tempPosition = new Position(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
                positions.add(tempPosition);
            }
        }
        c.close();
        return positions;
    }
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
    public void newAthlete(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
    public void addAthlete(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAthlete();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
    public void addWeeks(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("weeksFreq", a.getWeeks());
        String where = "Name='" + a.getName() + "'";
        try{
            db.update("Athlete",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
    }
    public void addDate(Athlete a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("sortTime", a.getDateOf());
        String where = "Name='" + a.getName() + "'";
        try{
            db.update("Athlete",dataToInsert,where,null);
        }catch (Exception e){
            String error =  e.getMessage().toString();
        }
    }
    public void addPositions(ArrayList<Position> posList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String pName = "";
        String level = "";
        String imagePath = "";
        String sName = "";
        int picked = 0;
            for (int i = 0; i < posList.size(); i++) {
                pName = posList.get(i).getName();
                level = posList.get(i).getLevel();
                imagePath = posList.get(i).getImagePath();
                sName = posList.get(i).getSportName();
                picked = posList.get(i).getPicked();

                String sql = "INSERT INTO Position (Name, Level, imagePath, sportName, picked) VALUES ('" + pName + "', '" + level + "', '" + imagePath + "', '" + sName + "'," + picked + ")";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.executeInsert();

            }
        }
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
        }
        return skills;
    }
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
    public ArrayList getCards()
    {
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * FROM Card",null);
        if(c != null)
        {
            while(c.moveToNext())
            {
                Card tempCard = new Card(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getString(6),c.getString(7),c.getString(8),c.getInt(9),c.getInt(10),c.getString(11),c.getString(12),c.getInt(13));
                cards.add(tempCard);
            }
        }
        return cards;
    }
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
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    public void deletePositions()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Position");
    }
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
    public void deleteCards()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Card");
    }
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
                    if (a.getSkillLevel() >= levelReq) {
                        String sql = "INSERT INTO Card (num, cardName, Category, Description, shortDescription, moreInfo, infoPath, sportName, positionName) VALUES (" + num + ", '" + sName + "', '" + category + "', '" + desc + "', '" + sDesc + "', " + moreInfo + ", '" + infoPath + "', '" + sportName + "', '" + positionName + "')";
                        SQLiteStatement statement = db.compileStatement(sql);
                        statement.executeInsert();
                    }
                } while (c.moveToNext());
            }
        }
    public void createCards(int sLev, String gender, ArrayList<String> posList)
    {
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
        Cursor cur = db.rawQuery("Select * FROM Skill Where positionName = 'Global'",null);
        if(cur != null)
        {
            while(cur.moveToNext())
            {
                num++;
                sName = cur.getString(0);
                category = cur.getString(1);
                desc = cur.getString(2);
                sDesc = cur.getString(3);
                moreInfo = cur.getInt(6);
                infoPath = cur.getString(7);
                sportName = cur.getString(8);
                positionName = cur.getString(9);
                String sql = "INSERT INTO Card (num, cardName, Category, Description, shortDescription, moreInfo, infoPath, sportName, positionName) VALUES ("+num+", '" + sName + "', '" + category + "', '"+ desc + "', '"+sDesc+"', " + moreInfo + ", '" + infoPath + "', '" + sportName + "', '" + positionName + "')";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.executeInsert();
            }
        }

        for (int i = 0; i < posList.size(); i++)
        {
            Cursor c = db.rawQuery("Select * FROM Skill Where positionName = '"+posList.get(i)+"'", null);
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
                    if (sLev >= levelReq) {
                                String sql = "INSERT INTO Card (num, cardName, Category, Description, shortDescription, moreInfo, infoPath, sportName, positionName) VALUES (" + num + ", '" + sName + "', '" + category + "', '" + desc + "', '" + sDesc + "', " + moreInfo + ", '" + infoPath + "', '" + sportName + "', '" + positionName + "')";
                                SQLiteStatement statement = db.compileStatement(sql);
                                statement.executeInsert();
                        }
                } while (c.moveToNext());
            }
        }
        cur.close();
    }
    public Athlete getAthlete()
    {
        ArrayList<Athlete> aList = new ArrayList<>();
        String name = "", gender = "", email = "", DOB = "", teams = "", loc = "", zip = "", dateOf = "";
        int sLevel = 0, terms1 = 0, terms2 = 0, terms3 = 0, weeks = 0;
        Blob image = null;
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
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Sport");
        db.execSQL("DROP TABLE IF EXISTS Position");
        db.execSQL("DROP TABLE IF EXISTS Athlete");
        db.execSQL("DROP TABLE IF EXISTS Skill");
        db.execSQL("DROP TABLE IF EXISTS Card");

        onCreate(db);
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
}