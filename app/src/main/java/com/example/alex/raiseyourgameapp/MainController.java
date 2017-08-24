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
    public void createPositions(){
        ArrayList<Position> posList = new ArrayList<>();
        posList.add(new Position("Fielder", "0", "", "Cricket", 0));
        posList.add(new Position("Bowler", "0", "", "Cricket", 0));
        posList.add(new Position("Catcher", "0", "", "Cricket", 0));
        posList.add(new Position("Keeper", "0", "", "Cricket", 0));
        posList.add(new Position("Captaincy", "0", "", "Cricket", 0));
        db.addPositions(posList);
    }

    public void createAthlete(String name) {
        String lName = "Boris";
        String Gender = "MALE";
        db.createAthlete(lName, name, Gender);
    }
    public void createSkills() {
        ArrayList<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("LifeBalance","Wellbeing","Actively consider the balance between personal/professional life and cricket.\n" +
                "Have review processes and practices in place that allow you to identify and make small or bold adjustments that ensure that your purpose, goals and/or aspirations are achieved.\n" +
                "Have interests outside of cricket that allow some variety, challenge and stimulation.",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("SupportNetworks","AvoidingInjury","Has a number of positive, knowledgeable people supporting their development.\n" +
                "Has a stable, safe, supportive home environment that is conducive to high performance.\n" +
                "Has access to the resources needed, e.g. transport, equipment, recovery, nutrition.\n" +
                "Is able to create the time necessary to train.\n.",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("AvoidingInjury","Wellbeing","All basic movement patterns should be pain free, free of dysfunction and ready to be loaded safely to maximise athletic potential in all planes and landing positions.\n" +
                "Have not had a major injury (or a series of minor injuries) in the past year.\n" +
                "Manages workload for repetitive actions (especially bowlers and keepers) proactively and sensibly.\n" +
                "Does not have a history of being ‘injury prone’.\n" +
                "Has identified areas of potential weakness (muscle balance assessment?) and incorporated strengthening into training.\n" +
                "Is proactive in dealing with any niggles or injuries, seeing a physio quickly and communicating well with the coach.\n" +
                "When recovering from an injury, follows the physiotherapist’s advice and is diligent with the rehab programme set.\n",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("Catching","Fielding","Makes good decisions and utilises the correct technique for the type of catch (reverse cup or orthodox catching).\n" +
                "Strong at flat catching (flat hard ball) and high catching in the outfield.\n" +
                "Strong at reflex catching, anticipation, e.g. in the slips.  Good reactions, technique.",0,"ALL",0,"","Cricket","Fielder"));
        skillList.add(new Skill("ProtectsTeamCulture","Character/Team","Adheres to the appropriate (for the setting) and effective team values, during both good and bad times.\n" +
                "Aware of self and impact on others.\n" +
                "Acts in line with those values and encourages others to do the same.\n",2,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("Throwing","Fielding","Able to throw the ball on the full from the boundary accurately, with good safe technique. \n" +
                "Utilises different techniques for throwing to bowlers end vs keepers end (shoulder throw, cross-over throw, back hand flick, underarm).\n" +
                "Ability to accurately throw the stumps down.\n" +
                "Makes good decisions about which end to throw to in pressure situations.",0,"ALL",0,"","Cricket","Fielder"));
        skillList.add(new Skill("Strategy","Bowling","Understanding game situation, and adjusting bowling strategy to suit the conditions.\n" +
                "Following pre-set plan provided by the coach, with any changes suggested by the captain.\n" +
                "Bowling to the field.\n" +
                "Makes good decisions about which end to throw to in pressure situations.",1,"ALL",0,"","Cricket","Bowler"));
        db.addSkills(skillList);

    }
    public ArrayList<Card> getCards(int skillLevel, ArrayList<String> posList)
    {
        db.deleteCards();
        db.createCards(skillLevel, "MALE", posList);
        ArrayList<Card> cardList = db.getCards();
        return cardList;
    }
}
