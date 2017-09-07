package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity {
    private Button l;
    private Button m;
    private Button h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        l = (Button) findViewById(R.id.beginnerBtn);
        m = (Button) findViewById(R.id.intermediateBtn);
        h = (Button) findViewById(R.id.proBtn);
        l.setText(Html.fromHtml("<b><big>" + "Emerging Talent"+"</b></big><br/>" +"<small>"+"New Team Member"+"</small>"));
        m.setText(Html.fromHtml("<b><big>" + "Team Member"+"</b></big><br/>" +"<small>"+"Gathering Experience"+"</small>"));
        h.setText(Html.fromHtml("<b><big>" + "Senior Player"+"</b></big><br/>" +"<small>"+"Leader"+"</small>"));
    }

    public void goToSkillsBeginner(View view){
        int num = 1;
        Intent intent = new Intent(this, PositionActivity.class);
//        Intent activityChosen = new Intent(this, StartScreen.class);
//        View b1 = findViewById(R.id.button7);
//        b1.setVisibility(View.VISIBLE);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
    public void goToSkillsIntermediate(View view){
        int num = 2;
        Intent intent = new Intent(this, PositionActivity.class);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
    public void goToSkillsPro(View view){
        int num = 3;
        Intent intent = new Intent(this, PositionActivity.class);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
}
