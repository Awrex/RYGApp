package com.ryg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.ryg.R.layout.activity_title_screen;

/** TitleScreenActivity
 * Created By Alex Stewart
 * The title screen.
 * Allows the user to reset everything aside from their user data.
 * Allows the user to continue on to either the SelectPositionActivity class or start from the introduction.
 */
public class TitleScreenActivity extends AppCompatActivity implements View.OnClickListener {
    DBController db;
    MainController mc;
    ImageView nextButton;
    ConstraintLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_title_screen);
        nextButton = (ImageView) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
    }

    /**
     * onClick override, checks what buttons have been pressed and acts on them accordingly.
     * @param v
     */
    @Override
    public void onClick(View v) {
        //launches the switchScreen method.
        if(v.getId() == R.id.nextButton)
            switchScreen();
    }

    /** switchScreen
     * Checks whether the athlete already exists, if it doesn't it creates the database and sends the user to the introduction.
     * If it does exist it sends the user to the SelectPositionsActivity class.
     */
    public void switchScreen() {
        try {
            db = new DBController(this);
            Athlete athlete = db.getAthlete();
            if(athlete != null){
                Intent intent = new Intent(TitleScreenActivity.this, SelectPositionsActivity.class);
                startActivity(intent);
                finish();
            }
                else
                {
                    firstTime();
                    Intent intent = new Intent(TitleScreenActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch(RuntimeException e)
            {
                Intent intent = new Intent(TitleScreenActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
    }

    /** reset
     * When the reset button is pressed, it saves the current athlete saved in the database.
     * Then it deletes the database and recreates it.
     * Then it inserts the athlete back into the database.
     * @param v (Button)
     */
    public void reset(View v){
        db = new DBController(getBaseContext());
        mc = new MainController(getBaseContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will remove your sort and you will have to start from the beginning, do you wish to continue?")
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int button) {
                        Athlete a = new Athlete();
                        a.setName("temp");
                        try {
                            a = db.getAthlete();
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        getBaseContext().deleteDatabase("RYG.db");
                        mc.createDB();
                        ArrayList<Skill> skills = db.getSkills();
                        if(skills.size() == 0)
                            mc.createSkills(getBaseContext());
                        ArrayList<Position> positions = db.getPositions();
                        if (positions.size() == 0)
                            mc.createPositions(getApplicationContext());
                        mc.createPositions(getBaseContext());
                        Log.e("Size: "+positions.size(),"position size");
                        if(!a.getName().equals("temp"))
                        {
                            mc.addAthlete(getApplicationContext(),a);
                        }
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    /**
     * firstTime
     * Is run when an Athlete does not already exist in the database.
     * It just creates a new database.
     */
    public void firstTime(){
        db = new DBController(this);
        mc = new MainController(this);
        getBaseContext().deleteDatabase("RYG.db");
        mc.createDB();
        db.deleteSkills();
        ArrayList<Skill> skills = db.getSkills();
        if(skills.size() == 0)
            mc.createSkills(getBaseContext());
        ArrayList<Position> positions = db.getPositions();
        if (positions.size() == 0)
            mc.createPositions(this);
    }
}
