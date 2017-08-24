package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.alex.raiseyourgameapp.R.layout.activity_title_screen;

public class TitleScreen extends AppCompatActivity implements View.OnClickListener {
    DBController db;
    MainController mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_title_screen);

        Button button = (Button) findViewById(R.id.changeScreen);
        button.setOnClickListener(this);

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
