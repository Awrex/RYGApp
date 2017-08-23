package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.alex.raiseyourgameapp.R.layout.activity_title_screen;

public class TitleScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_title_screen);

        Button button = (Button) findViewById(R.id.changeScreen);
        button.setOnClickListener(this);
    }
    public void onClick(View v) {
        Intent intent = new Intent(TitleScreen.this, LevelActivity.class);
        startActivity(intent);
        finish();
    }
}
