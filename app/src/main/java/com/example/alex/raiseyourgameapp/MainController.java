package com.example.alex.raiseyourgameapp;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Alex on 14/08/2017.
 */

public class MainController extends Activity {
    private MainController mc;
    private DBController db;

    MainController(Context con) {
        db = new DBController(con);
    }

    MainController() {

    }
    public void createCards(int SkillLevel, ArrayList<String> PosList)
    {

    }
    public void createDB() {
        db.getWritableDatabase();
    }

    public void createAthlete(String name) {
        String lName = "Boris";
        String Gender = "MALE";
        db.createAthlete(lName, name, Gender);
    }

    public ArrayList<Card> getCards(int skillLevel, ArrayList<String> posList)
    {
        db.deleteCards();
        db.createCards(skillLevel, "MALE", posList);
        ArrayList<Card> cardList = db.getCards();
        return cardList;
    }
}
