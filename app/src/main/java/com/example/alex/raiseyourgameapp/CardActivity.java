package com.example.alex.raiseyourgameapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {
    private static final String TAG = "CardActivity";
    private StatePagerAdapter cardAdapter;
    private ViewPager viewPager;
    private ArrayList<Card> cards = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Log.d(TAG, "onCreate: Started.");

        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        cards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager, cards);

    }

    private void setupViewPager(ViewPager viewPager, ArrayList<Card> cList){
        StatePagerAdapter adapter = new StatePagerAdapter(getSupportFragmentManager());
        for (int i = 0; i<cList.size(); i++) {
            CardFragment cf = new CardFragment();
            cf.addCard(cList.get(i));
            adapter.addItem(cf,cList.get(i).getName());
        }
        viewPager.setAdapter(adapter);

    }
}
