package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void goToLevelScreen(View view) {
        Intent intent = new Intent(StartScreen.this, LevelActivity.class);
        startActivity(intent);
    }

    public void goToPositionScreen(View view) {
        Intent intent = new Intent(StartScreen.this, PositionActivity.class);
        startActivity(intent);
    }

    public void goToSortScreen(View view) {
        Intent intent = new Intent(StartScreen.this, ReviewSortActivity.class);
        startActivity(intent);
    }
}