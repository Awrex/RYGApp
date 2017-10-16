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
        l = (ConstraintLayout) findViewById(R.id.titleLayout);
        l.setOnTouchListener(new OnSwipeTouchListener(TitleScreenActivity.this)
        {
            public void onSwipeLeft()
            {
                switchScreen();
            }
        });
        nextButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nextButton)
            switchScreen();
    }

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
