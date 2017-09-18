package com.example.alex.raiseyourgameapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Athlete a;
    String fileName = "userImage.png";
    File f;
    TextView userText;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(this);
        setContentView(R.layout.activity_position);
        posList = new ArrayList<>();
        posList = db.getPositions();
        a = new Athlete();
        a = db.getAthlete();
        String path = getIntent().getStringExtra("DIR");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        selectionList = (ListView) findViewById(R.id.posListView);
        selectionList.setBackgroundColor(Color.LTGRAY);
        selectionList.setAlpha((float)0.5);
        userText = (TextView) findViewById(R.id.personName);
        profile = (ImageView) findViewById(R.id.profilePic);
        for (int i = 0; i < posList.size(); i++) {
            adapter.add(posList.get(i).getName());
        }

        selectionList.setAdapter(adapter);
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                String text = "";
                if (!selectedPositions.contains(item)) {
                    selectedPositions.add(item);
                    parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                }
                else if (selectedPositions.contains(item)) {
                    selectedPositions.remove(item);
                    parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                }
            }

         });
        if (a != null)
        {
            Bitmap bmp = null;
            try {
                f = new File(path,"Profile.jpg");
                bmp = BitmapFactory.decodeStream(new FileInputStream(f));
                profile.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        userText.setText(a.getFirstName() + " " + a.getLastName());
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
              Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
              intent.putExtra("CARDLIST", cardList);
              startActivity(intent);
              finish();
          }
        }).setNegativeButton(android.R.string.no, null).show();

        }
    }
