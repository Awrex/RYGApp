package com.ryg;

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
    Context con;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
   MainController(Context con) {
   db = new DBController(con);
       this.con = con;
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
                    s.setGenderReq(el.getElementsByTagName("gender").item(0).getTextContent());
                    s.setInfoPath(el.getElementsByTagName("infoPath").item(0).getTextContent());
                    if(s.getInfoPath().equals("no")) {
                        s.setMoreInfo(0);
                    }
                    else
                        s.setMoreInfo(1);
                    s.setSportName(con.getResources().getString(R.string.sport_name));
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
    public void createPositions(Context c){
        ArrayList<Position> positions = new ArrayList<>();
        positions = db.getPositions();
        String[] positionNames = c.getResources().getStringArray(R.array.positions);;
        if(positions.isEmpty() || positions == null) {
            ArrayList<Position> posList = new ArrayList<>();
            for (int i = 0; i < positionNames.length; i++) {
                posList.add(new Position(positionNames[i], "0", "", c.getResources().getString(R.string.sport_name), 0));
            }
            db.addPositions(posList);
        }
    }
    public void addAthlete(Context c, Athlete athlete){
        db.newAthlete(athlete);
    }
}
