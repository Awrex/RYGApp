package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.alex.raiseyourgameapp.R.layout.activity_title_screen;

public class TitleScreen extends AppCompatActivity implements View.OnClickListener {
    DBController db;
    MainController mc;
    ImageView nextButton;
    ConstraintLayout l;
    private static int SPLASH_TIMEOUT = 3500; // screen timeout before loading getSkills

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_title_screen);
        nextButton = (ImageView) findViewById(R.id.nextButton);
        l = (ConstraintLayout) findViewById(R.id.titleLayout);
        l.setOnTouchListener(new OnSwipeTouchListener(TitleScreen.this)
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
                Intent intent = new Intent(TitleScreen.this, SelectPositions.class);
                startActivity(intent);
                finish();
            }
                else
                {
                    firstTime();
                    Intent intent = new Intent(TitleScreen.this, introduction.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch(RuntimeException e)
            {
                Intent intent = new Intent(TitleScreen.this, introduction.class);
                startActivity(intent);
                finish();
            }
    }
    public void reset(View v){
        db = new DBController(this);
        mc = new MainController(this);
        getBaseContext().deleteDatabase("RYG.db");
        mc.createDB();
        db.deleteSkills();
        ArrayList<Position> positions = db.getPositions();
        if (positions.size() == 0)
            mc.createPositions();
        ArrayList<Skill> skills = db.getSkills();
        if(skills.size() == 0)
            mc.createSkills(getBaseContext());
    }
    public void firstTime(){
        db = new DBController(this);
        mc = new MainController(this);
        getBaseContext().deleteDatabase("RYG.db");
        mc.createDB();
        db.deleteSkills();
        ArrayList<Position> positions = db.getPositions();
        if (positions.size() == 0)
            mc.createPositions();
        ArrayList<Skill> skills = db.getSkills();
        if(skills.size() == 0)
            mc.createSkills(getBaseContext());
    }
}
