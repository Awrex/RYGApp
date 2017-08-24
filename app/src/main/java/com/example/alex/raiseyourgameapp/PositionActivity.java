package com.example.alex.raiseyourgameapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 24/08/2017.
 */

public class PositionActivity extends AppCompatActivity {
    private ArrayList<Position> posList;
    private MainController mc = new MainController(this);
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<String> selectedPositions = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView selectionList;
    private DBController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(this);
        setContentView(R.layout.activity_position);
        posList = new ArrayList<>();
        posList = db.getPositions();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        selectionList = (ListView) findViewById(R.id.posListView);
        for (int i = 0; i < posList.size(); i++) {
            adapter.add(posList.get(i).getName());
        }
        selectionList.setAdapter(adapter);
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                String text = "";
                TextView textList = (TextView) findViewById(R.id.listText);

                if (!selectedPositions.contains(item))
                    selectedPositions.add(item);
                else if (selectedPositions.contains(item))
                    selectedPositions.remove(item);
                for (int i = 0; i < selectedPositions.size(); i++)
                    text += selectedPositions.get(i);

                textList.setText(text);
            }

         });
        }
    public void goToLevels(View view){
        Intent intent = new Intent(this, LevelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }
        public void goMain(View view)
        {
            String text = "";
            for (int i = 0; i < selectedPositions.size(); i++)
                text += selectedPositions.get(i) + "\n";


            new AlertDialog.Builder(this)
                    .setTitle("Positions Selected")
                    .setMessage("Your current positions selected are:\n" + text + "\nWould you like to continue?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialog, int button) {
              int skillLev = getIntent().getIntExtra("LEVEL", 0);
              cardList = mc.getCards(skillLev, selectedPositions);
              Intent intent = new Intent(getBaseContext(), CardActivity.class);
              intent.putExtra("CARDLIST", cardList);
              startActivity(intent);
              finish();
          }
        }).setNegativeButton(android.R.string.no, null).show();

        }
    }
