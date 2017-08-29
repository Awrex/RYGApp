package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

import java.util.ArrayList;


import static com.example.alex.raiseyourgameapp.R.layout.activity_title_screen;

public class TitleScreen extends AppCompatActivity implements View.OnClickListener {
    DBController db;
    MainController mc;

    private static int SPLASH_TIMEOUT = 4000; // screen timeout before loading getSkills
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_title_screen);

        Button button = (Button) findViewById(R.id.changeScreen);
        button.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent levelIntent = new Intent(TitleScreen.this, LevelActivity.class);
                startActivity(levelIntent);
                finish();
            }
        },SPLASH_TIMEOUT);

    }
    public void onClick(View v) {
        Intent intent = new Intent(TitleScreen.this, LevelActivity.class);
        db = new DBController(this);
        mc = new MainController(this);
        mc.createDB();
        db.deleteSkills();
        ArrayList<Position> positions = db.getPositions();
        if (positions.size() == 0)
            mc.createPositions();
        ArrayList<Skill> skills = db.getSkills();
        if(skills.size() == 0)
            mc.createSkills();
        startActivity(intent);
        finish();
    }
}
