package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class StartScreen extends AppCompatActivity {

    DBController db;
    MainController mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void goToLevelScreen(View view) {
        Intent intent = new Intent(StartScreen.this, LevelActivity.class);
        startActivity(intent);
        db = new DBController(this);
        mc = new MainController(this);
        mc.createDB();
        mc.createPositions();
        db.deleteSkills();
        mc.createSkills(getBaseContext());
    }

    public void goToPositionScreen(View view) {
        Intent intent = new Intent(StartScreen.this, PositionActivity.class);
        startActivity(intent);
    }

    public void goToSortScreen(View view) {
        Intent intent = new Intent(StartScreen.this, ReviewSortActivity.class);
        startActivity(intent);
    }

    public void goToOutput(View view){
        Intent intent = new Intent(StartScreen.this, OutputActivity.class);
        startActivity(intent);
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
}