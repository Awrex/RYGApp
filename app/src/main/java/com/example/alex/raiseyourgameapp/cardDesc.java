package com.example.alex.raiseyourgameapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class cardDesc extends Activity {
    private Card card;
    private TextView title;
    private TextView shortDesc;
    private TextView comment;
    public void addCard(Card c){
        card = c;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String t = getIntent().getStringExtra("TITLE");
        String s = getIntent().getStringExtra("SHORT");
        String c = getIntent().getStringExtra("COMMENT");
        setContentView(R.layout.popup_card_desc);
        title = (TextView) findViewById(R.id.shortTitleView);
        shortDesc = (TextView) findViewById(R.id.shortDescView);
        comment = (TextView) findViewById(R.id.commentView);
        title.setText(t);
        shortDesc.setText(s);
        comment.setText(c);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

