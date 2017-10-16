package com.ryg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView nextArrow;
    RelativeLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        nextArrow = (ImageView) findViewById(R.id.nextArrow);
        l = (RelativeLayout) findViewById(R.id.introLayout);
        l.setOnTouchListener(new OnSwipeTouchListener(IntroActivity.this)
        {
            public void onSwipeLeft()
            {
                switchScreen();
            }
        });
        nextArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nextArrow)
            switchScreen();
    }

    public void switchScreen() {
        Intent intent = new Intent(IntroActivity.this, CreateUserActivity.class);
        startActivity(intent);
        finish();
    }
}
