package com.ryg;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFrag extends Activity {
    private String mTag;
    private Athlete a;
    private Card card;
    private TextView dateOf;
    private Calendar cal;
    private EditText freqWeeks;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button calClick, setWeek;
    int year = 0, month = 0, day = 0;
    private DBController db;

    public void addCard(Card c,String t){
        card = c;
        mTag = t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(getBaseContext());
        setContentView(R.layout.popup_date_set);
        cal = Calendar.getInstance();
        dateOf = (TextView) findViewById(R.id.dateText);
        freqWeeks = (EditText) findViewById(R.id.weeksEdit);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.shortConstraint);
        r.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
        setWeek = (Button) findViewById(R.id.weekSet);
        calClick = (Button) findViewById(R.id.calendarButton);
        a = db.getAthlete();
        Calendar c = Calendar.getInstance();
        dateOf.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));
        try{
            dateOf.setText(a.getDateOf());
            freqWeeks.setText(Integer.toString(a.getWeeks()));
        }catch(Exception e)
        {
            dateOf.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));
            e.printStackTrace();
        }
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                year = y;
                month = m;
                day = d;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);
                String date = day + "/" + (month+1) + "/" + year;
                cal.set(y,m,d);
                a.setDateOf(date);
                db.addDate(a);
                a.setWeeks(0);
                db.addWeeks(a);
                freqWeeks.setText("0");
                dateOf.setText(date);
            }
        };
        setWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar c = Calendar.getInstance();
                    int num = 0;
                    num = Integer.parseInt(freqWeeks.getText().toString());
                    a.setWeeks(num);
                    num = num * 7;
                    c.add(Calendar.DAY_OF_YEAR, num);
                    Date d = c.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                    String date = format1.format(d);
                    dateOf.setText(date);
                    a.setDateOf(dateOf.getText().toString());
                    db.addDate(a);
                    db.addWeeks(a);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        calClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                if(!dateOf.getText().toString().equals("DATE")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date d = sdf.parse(dateOf.getText().toString());
                        cal.setTime(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),android.R.style.Theme_Holo_Dialog,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,(int)(height*.5));
        getWindow().setBackgroundDrawable(null);
    }
    public void close(View v) {
        finish();
    }
}
