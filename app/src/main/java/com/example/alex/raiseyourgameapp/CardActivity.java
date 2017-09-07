package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardactivity);
        Log.d(TAG, "onCreate: Started.");
        category = getIntent().getStringExtra("CATEGORY");
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        rawCards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        for(int i = 0; i<rawCards.size();i++)
        {
            if (rawCards.get(i).getCategory().equals(category))
            {
                cards.add(rawCards.get(i));
            }
        }
        viewPager = (ViewPager) findViewById(R.id.cardPager);
        setupViewPager(viewPager, cards);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        intent.putExtra("CARDLIST", rawCards);
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
        if(undoCards!=null && undoCards.size() == 0) {
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
    public void toSelection(View v)
    {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        intent.putExtra("CARDLIST", rawCards);
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
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
    }
    public void toSelect()
    {
        Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
        intent.putExtra("CARDLIST", rawCards);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }
}
