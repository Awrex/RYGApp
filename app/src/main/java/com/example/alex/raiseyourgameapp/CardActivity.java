package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity implements FragmentRemover {
    private static final String TAG = "CardActivity";
    private StatePagerAdapter cardAdapter;
    private ViewPager viewPager;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> sortedCards = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Log.d(TAG, "onCreate: Started.");

        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        cards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager, cards);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TitleScreen.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void toTitle()
    {
        Intent intent = new Intent(getBaseContext(), TitleScreen.class);
        startActivity(intent);
        finish();
    }
    public void sortLater()
    {
        int pos = viewPager.getCurrentItem();
        Card tempCard = cards.get(pos);
        cards.remove(pos);
        cards.add(tempCard);
        setupViewPager(viewPager, cards);
        viewPager.setCurrentItem(pos);
    }
    @Override
    public void onFragmentRemove(){
        int pos = viewPager.getCurrentItem();
        if (pos == cards.size()) { pos -= 1; }
        sortedCards.add(cards.get(pos));
        cards.remove(pos);
        setupViewPager(viewPager, cards);
        viewPager.setCurrentItem(pos);
    }

    @Override
    public void undoSort()
    {
        if (!sortedCards.isEmpty()) {
            Card tempCard = sortedCards.get(sortedCards.size() - 1);
            cards.add(tempCard.getNum(), tempCard);
            sortedCards.remove(sortedCards.size() - 1);
            setupViewPager(viewPager, cards);
            viewPager.setCurrentItem(tempCard.num);
        }
    }
    private void setupViewPager(ViewPager viewPager, ArrayList<Card> cList){
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        for (int i = 0; i<cList.size(); i++) {
            CardFragment cf = new CardFragment();
            cf.addCard(cList.get(i),cList.get(i).getName());
            cardAdapter.addItem(cf,cList.get(i).getName());
        }
        viewPager.setAdapter(cardAdapter);

    }
}
