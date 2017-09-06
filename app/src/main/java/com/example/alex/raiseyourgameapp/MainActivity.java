package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainController mc = new MainController(this);
    private ArrayList<Card> cardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText athleteName =  (EditText) findViewById(R.id.nameAdd);
        final EditText searchName = (EditText) findViewById(R.id.nameCheck);
        final TextView testView = (TextView) findViewById(R.id.testView);
        Button but1 = (Button) findViewById(R.id.databaseCreate);
        but1.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
            mc.createDB();
        }
        });
        Button but2 = (Button) findViewById(R.id.athleteAdd);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.createAthlete(athleteName.getText().toString());
            }
        });
        Button but3 = (Button) findViewById(R.id.checkName);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int skillLev = getIntent().getIntExtra("LEVEL", 0);
                ArrayList<String> posList = getIntent().getStringArrayListExtra("SKILLLIST");
            }
        });}
        public void sendCards(ArrayList<Card> cards)
        {
            Intent intent = new Intent(this, CardActivity.class);
            intent.putExtra("CARDLIST", cards);
            startActivity(intent);
            finish();
        }
        public void getCards()
        {


        }
    }
