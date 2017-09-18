package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class introduction extends AppCompatActivity implements View.OnClickListener {
    ImageView nextArrow;
    ConstraintLayout l;
    DBController db;
    MainController mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        nextArrow = (ImageView) findViewById(R.id.nextArrow);
        l = (ConstraintLayout) findViewById(R.id.introLayout);
        l.setOnTouchListener(new OnSwipeTouchListener(introduction.this)
        {
            public void onSwipeLeft()
            {
                switchScreen();
            }
        });
        nextArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nextArrow)
            switchScreen();
    }

    public void switchScreen() {
        db = new DBController(this);
        mc = new MainController(this);
        mc.createDB();
        mc.createPositions();
        db.deleteSkills();
        mc.createSkills(getBaseContext());
        Intent intent = new Intent(introduction.this, CreateUser.class);
        startActivity(intent);
        finish();
    }
}
