package com.example.alex.raiseyourgameapp;

import android.app.Activity;
import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Alex on 14/08/2017.
 */

public class MainController extends Activity {
    private MainController mc;
    private DBController db;
    private ArrayList<Skill> sk;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
   MainController(Context con) {
   db = new DBController(con);
    }

    MainController() {

    }
    public void createSkills(Context c)
    {
        dbFactory = DocumentBuilderFactory.newInstance();

        sk = new ArrayList<>();
        try{
            InputStream is = c.getResources().openRawResource(R.raw.cards);
            dbFactory.setNamespaceAware(true);
            dbFactory.setIgnoringElementContentWhitespace(true);
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(is);
            doc.normalize();
            doc.normalizeDocument();
            Element e = doc.getDocumentElement();
            NodeList list = doc.getElementsByTagName("card");
            for(int i = 0; i< list.getLength(); i++)
            {
                Node n = list.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)n;
                    Skill s = new Skill();
                    s.setName(el.getElementsByTagName("title").item(0).getTextContent());
                    s.setCategory(el.getElementsByTagName("category").item(0).getTextContent());
                    s.setDescription(el.getElementsByTagName("description").item(0).getTextContent());
                    s.setShortDesc(el.getElementsByTagName("shortDescription").item(0).getTextContent());
                    s.setLevelReq(Integer .parseInt(el.getElementsByTagName("levelReq").item(0).getTextContent()));
                    s.setPositionName(el.getElementsByTagName("positionName").item(0).getTextContent());
                    s.setMoreInfo(0);
                    s.setInfoPath("");
                    s.setGenderReq("ALL");
                    s.setSportName("Cricket");
                    s.breakName();
                    sk.add(s);

                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.addSkills(sk);
    }
    public ArrayList getCards() {
        return db.getCards();
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
        posList.add(new Position("Batter", "0", "", "Cricket", 0));
        posList.add(new Position("Captaincy", "0", "", "Cricket", 0));
        db.addPositions(posList);
    }

    public void createAthlete(String name) {
        String lName = "Boris";
        String Gender = "MALE";
        db.createAthlete(lName, name, Gender);
    }
    /*
    public void createSkills() {
        ArrayList<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("LifeBalance","Wellbeing","Actively consider the balance between personal/professional life and cricket.\n\n" +
                "Have review processes and practices in place that allow you to identify and make small or bold adjustments that ensure that your purpose, goals and/or aspirations are achieved.\n\n" +
                "Have interests outside of cricket that allow some variety, challenge and stimulation.",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("SupportNetworks","Wellbeing","Has a number of positive, knowledgeable people supporting their development.\n\n" +
                "Has a stable, safe, supportive home environment that is conducive to high performance.\n\n" +
                "Has access to the resources needed, e.g. transport, equipment, recovery, nutrition.\n\n" +
                "Is able to create the time necessary to train.\n\n",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("AvoidingInjury","Wellbeing","All basic movement patterns should be pain free, free of dysfunction and ready to be loaded safely to maximise athletic potential in all planes and landing positions.\n" +
                "Have not had a major injury (or a series of minor injuries) in the past year.\n\n" +
                "Manages workload for repetitive actions (especially bowlers and keepers) proactively and sensibly.\n\n" +
                "Does not have a history of being ‘injury prone’.\n\n" +
                "Has identified areas of potential weakness (muscle balance assessment?) and incorporated strengthening into training.\n\n" +
                "Is proactive in dealing with any niggles or injuries, seeing a physio quickly and communicating well with the coach.\n\n" +
                "When recovering from an injury, follows the physiotherapist’s advice and is diligent with the rehab programme set.\n\n",0,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("ProtectsTeamCulture","Character/Team","Adheres to the appropriate (for the setting) and effective team values, during both good and bad times.\n\n" +
                "Aware of self and impact on others.\n\n" +
                "Acts in line with those values and encourages others to do the same.\n\n",2,"ALL",0,"","Cricket","Global"));
        skillList.add(new Skill("Catching","Fielding","Makes good decisions and utilises the correct technique for the type of catch (reverse cup or orthodox catching).\n\n" +
                "Strong at flat catching (flat hard ball) and high catching in the outfield.\n\n" +
                "Strong at reflex catching, anticipation, e.g. in the slips.  Good reactions, technique.",0,"ALL",0,"","Cricket","Fielder"));
        skillList.add(new Skill("Throwing","Fielding","Able to throw the ball on the full from the boundary accurately, with good safe technique. \n\n" +
                "Utilises different techniques for throwing to bowlers end vs keepers end (shoulder throw, cross-over throw, back hand flick, underarm).\n\n" +
                "Ability to accurately throw the stumps down.\n\n" +
                "Makes good decisions about which end to throw to in pressure situations.",0,"ALL",0,"","Cricket","Fielder"));
        skillList.add(new Skill("Strategy","Captaincy","•Strong working relationship with the coach. \n\n  Strong tactical knowledge and experience.\n\n Makes sound strategic decisions under pressure.\n\n Uses scouting to inform strategy.\n\n  Can think on feet.\n  Composed.\n  Sees opportunities.\n  Reads situations and conditions well.\n  Knows rules inside out."
                ,1,"ALL",0,"","Cricket","Captaincy"));
        skillList.add(new Skill("Recovery","Physical","24 hours post game repair is very important.  The athlete follows recovery protocols consistently with discipline e.g.:\n" +
                "•\tRepair damaged muscles through immediate intake of protein*\n\n" +
                "•\tReplenish energy stores with carbohydrate*\n\n" +
                "•\tActivities to remove waste products from the body (through increasing blood circulation to get rid of lactate and adrenaline), which may include some or all of the following:\n\n" +
                "•\tBetter/deeper and longer sleep\n" +
                "•\tPool recovery\n" +
                "•\tIce baths (e.g. 5 mins at 10-13 degrees followed by a hot shower to increase blood circulation again).\n" +
                "* see nutritional guidelines for how much, and how soon after exercise.\n\n"
                ,0,"ALL",0,"","Cricket","Global"));


        db.addSkills(skillList);

    }
    */
    public ArrayList<Card> getCards(int skillLevel, ArrayList<String> posList)
    {
        db.deleteCards();
        db.createCards(skillLevel, "MALE", posList);
        ArrayList<Card> cardList = db.getCards();
        return cardList;
    }
}
