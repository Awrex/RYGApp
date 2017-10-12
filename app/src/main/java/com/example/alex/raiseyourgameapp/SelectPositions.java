package com.example.alex.raiseyourgameapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class SelectPositions extends AppCompatActivity {
    private ArrayList<Position> posList;
    private MainController mc = new MainController(this);
    private ArrayList<Card> cardList = new ArrayList<>();
    ArrayList<String> oldPositions = new ArrayList<>();
    private ArrayList<String> selectedPositions = new ArrayList<>();
    private ArrayList<String> deletedPositions = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView selectionList;
    private Button reviewButton, secondButton, outputButton;
    private boolean loaded= Boolean.FALSE, review= Boolean.FALSE, second= Boolean.FALSE, output = Boolean.FALSE;
    private DBController db;
    private Athlete a;
    String fileName = "userImage.png";
    File f;
    TextView userText;
    ImageView profile;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.infotoolbar,menu);

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            String dir = directory.getAbsolutePath();
            File f = new File(dir,"Profile.png");
            MenuItem m = menu.getItem(1);
            Bitmap bmp;
            bmp = BitmapFactory.decodeStream(new FileInputStream(f));
            if(bmp == null)
                m.setIcon(getResources().getDrawable(R.drawable.noavatar));
            else
                m.setIcon(new BitmapDrawable(getResources(),bmp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        startActivity(getIntent());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(this);
        setContentView(R.layout.activity_position);
        posList = new ArrayList<>();
        posList = db.getPositions();
        a = new Athlete();
        a = db.getAthlete();
        reviewButton = (Button)findViewById(R.id.reviewMenu);
        secondButton = (Button)findViewById(R.id.secondMenu);
        outputButton = (Button)findViewById(R.id.outputMenu);
        reviewButton.setEnabled(false);
        reviewButton.setVisibility(View.INVISIBLE);
        secondButton.setEnabled(false);
        secondButton.setVisibility(View.INVISIBLE);
        outputButton.setEnabled(false);
        outputButton.setVisibility(View.INVISIBLE);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = adapter.getItem(position);
                for(int i = 0; i < posList.size(); i++)
                {
                    if(s.equals(posList.get(i).getName()))
                    {
                        if(posList.get(i).getPicked() == 1){
                            if(!selectedPositions.contains(posList.get(i).getName()))
                                selectedPositions.add(posList.get(i).getName());
                            loaded = Boolean.TRUE;
                            view.setBackgroundColor(Color.GREEN);
                        }
                    }
                }
                return view;
        }
        };
        selectionList = (ListView) findViewById(R.id.posListView);
        selectionList.setBackgroundColor(Color.LTGRAY);
        selectionList.setAlpha((float)0.5);
        try{
            ArrayList<Card> cardList = db.getCards();
            for(int i = 0; i < cardList.size(); i++)
            {
                if(cardList.get(i).getRating()!=0) {
                    reviewButton.setEnabled(true);
                    reviewButton.setVisibility(View.VISIBLE);
                }
                if(cardList.get(i).getPriority()!=0) {
                    secondButton.setEnabled(true);
                    secondButton.setVisibility(View.VISIBLE);
                    outputButton.setEnabled(true);
                    outputButton.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < posList.size(); i++) {
            adapter.add(posList.get(i).getName());
        }
        oldPositions = selectedPositions;
        selectionList.setAdapter(adapter);
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                String text = "";
                if (!selectedPositions.contains(item)) {
                    selectedPositions.add(item);
                    for(int i = 0; i<posList.size(); i++)
                    {
                        if(posList.get(i).getName().equals(item)){
                            posList.get(i).setPicked(1);
                            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                            db.updatePosition(posList.get(i));
                        }
                    }
                }
                else if (selectedPositions.contains(item)) {
                    selectedPositions.remove(item);
                    for(int i = 0; i<posList.size(); i++)
                    {
                        if(posList.get(i).getName().equals(item)){
                            posList.get(i).setPicked(0);
                            parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                            db.updatePosition(posList.get(i));
                        }
                    }

                }
            }

         });
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        menuToolbar.setSubtitle("Select cards to sort");
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        }
        public void goMain(View view)
        {
            String text = "";
            for (int i = 0; i < selectedPositions.size(); i++)
                text += selectedPositions.get(i) + "\n";
            new AlertDialog.Builder(this)
                    .setTitle("Positions Selected")
                    .setMessage("Your current positions selected are:\n" + text + "\nWould you like to continue?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialog, int button) {
              ArrayList<String> newPositions = new ArrayList<>();
              ArrayList<Position> positionList = new ArrayList<>();
              positionList = db.getPositions();
              for(int i = 0; i<positionList.size(); i++) {
                  if (positionList.get(i).getPicked() == 1) {
                      if (!oldPositions.contains(positionList.get(i).getName()))
                          newPositions.add(positionList.get(i).getName());
                  }
              }
              for(int i = 0; i<positionList.size(); i++) {
                  if (positionList.get(i).getPicked() == 0) {
                      if (oldPositions.contains(positionList.get(i).getName()))
                          deletedPositions.add(positionList.get(i).getName());
                  }
              }
              if(!loaded) {
                  db.deleteSkills();
                  mc.createSkills(getBaseContext());
                  db.updatePositions(selectedPositions);
                  db.createCards();
              }
              else
                  db.updatePositions(selectedPositions);

              Intent intent = new Intent(getBaseContext(),SelectCategory.class);
              startActivity(intent);
              finish();
          }
        }).setNegativeButton(android.R.string.no, null).show();

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Tap on the positions you play, then press the circle select button to sort for those positions.\n\n Otherwise you may tap on any of the other icons available to go to the other screens.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if(id==R.id.userIcon)
        {
            Intent intent = new Intent(getBaseContext(), CreateUser.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TitleScreen.class);
        startActivity(intent);
        finish();
    }
    public void toReview(View v){
        Intent intent = new Intent(getBaseContext(), ReviewSort.class);
        startActivity(intent);
        finish();
    }
    public void toSecond(View v){
        Intent intent = new Intent(getBaseContext(), SecondSort.class);
        startActivity(intent);
        finish();
    }
    public void toOutput(View v){
        Intent intent = new Intent(getBaseContext(), OutputActivity.class);
        startActivity(intent);
        finish();
    }
}
