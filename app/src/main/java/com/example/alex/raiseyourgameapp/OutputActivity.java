package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class OutputActivity extends AppCompatActivity {
    DBController db;
    ArrayList<Card> cardList;
    ArrayList<String> categories;
    ArrayList<Integer> categoryNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        db = new DBController(this);
        categories = new ArrayList<>();
        categoryNum = new ArrayList<>();
        RadarChart chart = (RadarChart) findViewById(R.id.radarCht);
        cardList = new ArrayList<>();
        cardList = db.getCards();
        for(int i = 0; i < cardList.size(); i++)
        {
            if(!categories.contains(cardList.get(i).getCategory()))
            {
                categories.add(cardList.get(i).getCategory());
                categoryNum.add(db.getCard(cardList.get(i).getCategory()));
            }
        }
        ArrayList<Entry> entries = new ArrayList<>();
        int i = 0;
        while( i < categories.size())
        {
            int total = 0;
            for(int j = 0; j<cardList.size(); j++)
            {
                if(cardList.get(j).getCategory().equals(categories.get(i)))
                {
                    total += cardList.get(j).getRating();
                }
            }
            total = total/categoryNum.get(i)*4;
            if(total == 0)
                total = 1;
            entries.add(new Entry((float)total,i));
            i++;
        }


        RadarDataSet dataset1 = new RadarDataSet(entries,"Athlete");
        dataset1.setColor(Color.BLUE);
        dataset1.setDrawFilled(true);

        ArrayList<RadarDataSet> datasets = new ArrayList<RadarDataSet>();
        datasets.add(dataset1);

        RadarData data = new RadarData(categories, datasets);
        chart.setData(data);
        String desc = "";
        chart.setDescription(desc);
        chart.setWebLineWidthInner(0.5f);
        chart.setDescriptionColor(Color.RED);

        chart.invalidate();
        chart.animate();
    }
    public void toMenu(View v)
    {
       Intent intent = new Intent(getBaseContext(),StartScreen.class);
        startActivity(intent);
        finish();
    }
}
