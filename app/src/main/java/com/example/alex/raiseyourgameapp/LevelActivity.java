package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void goToSkillsBeginner(View view){
        int num = 0;
        Intent intent = new Intent(this, PositionActivity.class);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
    public void goToSkillsIntermediate(View view){
        int num = 1;
        Intent intent = new Intent(this, PositionActivity.class);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
    public void goToSkillsPro(View view){
        int num = 2;
        Intent intent = new Intent(this, PositionActivity.class);
        intent.putExtra("LEVEL",num);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        finish();
    }
}
