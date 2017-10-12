package com.example.alex.raiseyourgameapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CardActivity extends AppCompatActivity{
    private static final String TAG = "CardActivity";
    private StatePagerAdapter cardAdapter;
    private ViewPager viewPager;
    private DBController db = new DBController(this);
    private ArrayList<Card> rawCards = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> sortedCards = new ArrayList<>();
    private ArrayList<Card> undoCards = new ArrayList<>();
    private String category;
    private Boolean firstTime;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Log.d(TAG, "onCreate: Started.");
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        menuToolbar.setSubtitle("Rate the competencies below");
        category = getIntent().getStringExtra("CATEGORY");
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        rawCards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        for(int i = 0; i<rawCards.size();i++)
        {
            if(category.equals("Skills") && !rawCards.get(i).getCategory().equals("Mental") && !rawCards.get(i).getCategory().equals("Physical")&& !rawCards.get(i).getCategory().equals("Wellbeing")&& !rawCards.get(i).getCategory().equals("Skills")&& !rawCards.get(i).getCategory().equals("Character/Team"))
                cards.add(rawCards.get(i));
            else if (rawCards.get(i).getCategory().equals(category))
            {
                cards.add(rawCards.get(i));
            }
            else if (category.equals("All"))
            {
                cards.add(rawCards.get(i));
            }
        }
        viewPager = (ViewPager) findViewById(R.id.cardPager);
        setupViewPager(viewPager, cards);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The cards will appear in front of you in the middle of the screen.\nYou can sort them by using the buttons at the top, and can undo with the arrow button on the bottom.\nYou can also leave comments and swipe left to go to the next card at any time.")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            firstTime = Boolean.FALSE;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if(id==R.id.userIcon)
        {
            Intent intent = new Intent(getBaseContext(), CreateUser.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        intent.putExtra("CARDLIST", rawCards);
        intent.putExtra("FIRSTTIME", firstTime);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }

    public void sortLater(View v){ setRating(v,4); }

    public void workOn(View v){setRating(v,1);}
    public void med(View v){setRating(v,2);}
    public void strength(View v){setRating(v,3);}

    public void setRating(View v,int rate){
        int pos = viewPager.getCurrentItem();
        if(cards.size()!=0 && cards != null) {
            if (pos == cards.size()) {
                pos -= 1;
            }
            Card tempCard = cards.get(pos);
            rawCards.remove(tempCard);
            tempCard.setNum(pos);
            tempCard.setRating(rate);
            sortedCards.add(tempCard);
            undoCards.add(tempCard);
            cards.remove(pos);
            setupViewPager(viewPager, cards);
            viewPager.setCurrentItem(pos);
            if (cards.size()==0)
            {
                toSelect();
            }
        }
    }
    public void undoSort(View v)
    {
            int currentPos = 0;
        if(undoCards!=null && undoCards.size() != 0) {
            Card tempCard = undoCards.get(undoCards.size() - 1);
            currentPos = tempCard.getNum();
            cards.add(currentPos, tempCard);
            rawCards.add(currentPos, tempCard);
            undoCards.remove(undoCards.size() - 1);
            setupViewPager(viewPager, cards);
            viewPager.setCurrentItem(currentPos);
        }
    }
    private void setupViewPager(ViewPager viewPager, ArrayList<Card> cList){
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        for (int i = 0; i<cList.size(); i++) {
            CardFragment cf = new CardFragment();
                cf.addCard(cList.get(i), cList.get(i).getName());
                cardAdapter.addItem(cf, cList.get(i).getName());
        }
        viewPager.setAdapter(cardAdapter);
    }
    public void leaveComment(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setTitle("Enter your comment");
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Card c;
                c = cards.get(viewPager.getCurrentItem());
                c.setComment(input.getText().toString());
                db.updateComment(c);
                cardAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void toSelection(View v)
    {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }
    public void toSort(View v)
    {
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        sortedCards.clear();
        Intent intent = new Intent(getBaseContext(), ReviewSort.class);
        startActivity(intent);
        finish();
    }
    public void toSelect()
    {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }
}
