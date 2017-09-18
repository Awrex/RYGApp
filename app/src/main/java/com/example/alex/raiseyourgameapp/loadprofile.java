package com.example.alex.raiseyourgameapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class loadprofile extends AppCompatActivity {
    DBController db;
    Athlete a;
    TextView testText;
    ImageView testImg;
    String fileName = "userImage.png";
    File f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.alex.raiseyourgameapp.R.layout.activity_loadprofile);
        db = new DBController(this);
        a = new Athlete();
        a = db.getAthlete();
        String path = getIntent().getStringExtra("DIR");
        testText = (TextView) findViewById(R.id.testName);
        testImg = (ImageView) findViewById(R.id.testImage);
        if (a != null)
        {
            Bitmap bmp = null;
            try {
                f = new File(path,"Profile.jpg");
                bmp = BitmapFactory.decodeStream(new FileInputStream(f));
                testImg.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        testText.setText(a.getFirstName());
    }
}
