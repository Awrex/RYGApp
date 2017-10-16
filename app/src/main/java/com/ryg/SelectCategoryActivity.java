package com.ryg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SelectCategoryActivity extends AppCompatActivity {
    private DBController db;
    private int strategycount = 0, mentalcount = 0, physicalcount = 0, wellbeingcount = 0, skillscount = 0, charactercount = 0, tacticalcount = 0;
    private MainController mc = new MainController(this);
    private ArrayAdapter<String> listAdapter;
    private Button strategy, mental, physical, wellbeing, skills, character, all;
    private int max = 0;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> catList = new ArrayList<>();
    private String category;
    private Boolean firstTime = false;
    private TextView cardNum;
    /** Called when the activity is first created. */
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
            MenuItem m = menu.getItem(1);
            m.setIcon(getResources().getDrawable(R.drawable.noavatar));
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectposition);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        Button reviewButton = (Button) findViewById(R.id.reviewSort);
        reviewButton.setVisibility(View.INVISIBLE);
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        menuToolbar.setSubtitle("Tap a card set to sort or tap all");
        db = new DBController(this);
        cards = db.getCards();
        max = cards.size();
        //mainListView = (ListView) findViewById( R.id.posList );
        Athlete a = db.getAthlete();
        for (int i = 0; i < cards.size(); i++) {
            if(cards.get(i).getName().contains("Women") && a.getGender().equals("Male"))
            {
                cards.remove(i);
                max--;
                i--;
            }
            else if(cards.get(i).getName().contains("Men") && a.getGender().equals("Female"))
            {
                cards.remove(i);
                max--;
                i--;
            }
        }
        ArrayList<Position> posList = new ArrayList<>();
        posList = db.getPositions();
        ArrayList<String> positions = new ArrayList<>();
        for(int i = 0; i < posList.size(); i++)
        {
            if (posList.get(i).getPicked() == 1)
            {
                positions.add(posList.get(i).getName());
            }
        }
        for(int i = 0; i < cards.size(); i++)
        {
            if (!catList.contains(cards.get(i).getCategory()))
                if(positions.contains(cards.get(i).getPositionName()) || cards.get(i).getPositionName().equals("Global"))
                    catList.add(cards.get(i).getCategory());
        }
        for(int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getRating() != 0) {
                cards.remove(i);
                i--;
            }
        }
        //listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cardList);
        //mainListView.setAdapter( listAdapter );
        //mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
            //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    category = (String) parent.getItemAtPosition(position);
           //     goToCard();
           // }
     //   });
        /*if(listAdapter.getCount()==0)
        {
            sortGo();
        }
        */
        ArrayList<Card> rawCards = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++)
        {
            if(catList.contains(cards.get(i).getCategory()))
                if(positions.contains(cards.get(i).getPositionName()))
                    rawCards.add(cards.get(i));
                else if(cards.get(i).getPositionName().equals("Global"))
                    rawCards.add(cards.get(i));
        }
        cards = rawCards;
        strategy = (Button) findViewById(R.id.strategyButton);
        strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Tactical";
                goToCard();
            }
        });
        mental = (Button) findViewById(R.id.mentalButton);
        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Mental";
                goToCard();
            }
        });
        physical = (Button) findViewById(R.id.physicalButton);
        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Physical";
                goToCard();
            }
        });
        wellbeing = (Button) findViewById(R.id.wellbeingButton);
        wellbeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Wellbeing";
                goToCard();
            }
        });
        skills = (Button) findViewById(R.id.skillsButton);
        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Skills";
                goToCard();
            }
        });
        character = (Button) findViewById(R.id.characterButton);
        character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Character/Team";
                goToCard();
            }
        });
        all = (Button) findViewById(R.id.allButton);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "All";
                goToCard();
            }
        });
        cardNum = (TextView) findViewById(R.id.numLeft);
        cardNum.setText(Integer.toString(cards.size()));
        if(cards.size() == 0)
            sortGo();
        for (int i = 0; i < cards.size(); i++)
        {
            if(cards.get(i).getCategory().equals("Mental"))
                mentalcount++;
            else if(cards.get(i).getCategory().equals("Physical"))
                physicalcount++;
            else if(cards.get(i).getCategory().equals("Wellbeing"))
                wellbeingcount++;
            else if(cards.get(i).getCategory().equals("Skills"))
                skillscount++;
            else if(cards.get(i).getCategory().equals("Character/Team"))
                charactercount++;
            else if(cards.get(i).getCategory().equals("Tactical"))
                tacticalcount++;
        }
        if(mentalcount == 0) {
            mental.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            mental.setText("");
            mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(physicalcount == 0) {
            physical.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            physical.setText("");
            physical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(wellbeingcount == 0) {
            wellbeing.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            wellbeing.setText("");
            wellbeing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(skillscount == 0) {
            skills.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            skills.setText("");
            skills.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(charactercount == 0) {
            character.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            character.setText("");
            character.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(tacticalcount == 0) {
            strategy.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyed_button));
            strategy.setText("");
            strategy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(cards.size() < max){
            reviewButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Select a circle to sort that competency, the colour of the circle will be grey when the competency has been fully sorted.")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
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
            Intent intent = new Intent(getBaseContext(), CreateUserActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        startActivity(getIntent());
    }

    public void goToCard() {
        Intent intent = new Intent(getBaseContext(), FirstSortActivity.class);
        db = new DBController(this);

        intent.putExtra("CARDLIST", cards);
        intent.putExtra("CATEGORY",category);
        startActivity(intent);
        finish();
    }
    public void sortGo(){
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    public void selectPos(View v){
        Intent intent = new Intent(getBaseContext(), SelectPositionsActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToSort(View v) {
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TitleScreenActivity.class);
        startActivity(intent);
        finish();
    }
}