package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class SkillsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
    }
    @Override
    public void onStart()
    {
        super.onStart();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    public void goToLevels(View view){
        Intent intent = new Intent(this, LevelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }
    public void goToMain(View view){
        ArrayList<String> skillList = new ArrayList<>();
        int skillLev = getIntent().getIntExtra("LEVEL", 0);
        ToggleButton fielder = (ToggleButton) findViewById(R.id.fielderButton);
        ToggleButton batsman = (ToggleButton) findViewById(R.id.batterButton);
        ToggleButton keeper = (ToggleButton) findViewById(R.id.keeperButton);
        ToggleButton bowler = (ToggleButton) findViewById(R.id.bowlerButton);
        String field =fielder.getText().toString().toUpperCase();
        if (field.equals("I AM A FIELDER"))
            skillList.add("Fielder");
        String bat = batsman.getText().toString().toUpperCase();
        if(bat.equals("I AM A BATTER"))
            skillList.add("Batter");
        String keep = keeper.getText().toString().toUpperCase();
        if(keep.equals("I AM A WICKET KEEPER"))
            skillList.add("Keeper");
        String bowl =bowler.getText().toString().toUpperCase();
        if (bowl.equals("I AM A BOWLER"))
            skillList.add("Bowler");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LEVEL", skillLev);
        intent.putStringArrayListExtra("SKILLLIST", skillList);
        startActivity(intent);
        finish();
    }
}
