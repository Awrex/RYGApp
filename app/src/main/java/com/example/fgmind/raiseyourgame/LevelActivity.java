package com.example.fgmind.raiseyourgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void goToPosition(View view){

        Intent intent = new Intent(this, PositionActivity.class);
        startActivity(intent);
    }

}
