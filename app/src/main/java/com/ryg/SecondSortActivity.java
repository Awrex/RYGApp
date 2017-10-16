package com.ryg;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SecondSortActivity extends AppCompatActivity {
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> otherList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter, lowAdapter, medAdapter, highAdapter, selectedAdapter;
    private ListView list;
    private ListView low, medium, high;
    private DBController db;
    private ImageView textBox;
    private MainController mc;
    private String selected;

    class ImageListener implements View.OnDragListener {
        private RelativeLayout r;
        public ImageListener(RelativeLayout rel){r = rel;}
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.DKGRAY);
                    v.getBackground().setAlpha(40);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case DragEvent.ACTION_DROP:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    TextView target = new TextView(getBaseContext());
                    TextView drag = (TextView) event.getLocalState();
                    target.setText(drag.getText());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)event.getX() -80;
                    params.topMargin = (int)event.getY()- 70;
                    target.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                            selected = "";
                            v.startDrag(data, shadowBuilder, v, 0);
                            v.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    });
                    r.addView(target,params);
                    arrayAdapter.remove(selected);
                    v.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cardtoolbar,menu);
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            String dir = directory.getAbsolutePath();
            File f = new File(dir,"Profile.png");
            MenuItem m = menu.getItem(2);
            Bitmap bmp;
            bmp = BitmapFactory.decodeStream(new FileInputStream(f));
            if(bmp == null)
                m.setIcon(getResources().getDrawable(R.drawable.noavatar));
            else
                m.setIcon(new BitmapDrawable(getResources(),bmp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MenuItem m = menu.getItem(2);
            m.setIcon(getResources().getDrawable(R.drawable.noavatar));
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(this);
        setContentView(R.layout.activity_second_sort);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        try{
            Athlete a = db.getAthlete();
            if(!a.getDateOf().equals(null))
                menuToolbar.setSubtitle("Next sort: " + a.getDateOf());
            else
                menuToolbar.setSubtitle("Click on calendar to schedule next sort.");
        } catch(Exception e)
        {
            menuToolbar.setSubtitle("Schedule next sort on calendar");
        }
        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DateFragment.class);
                startActivityForResult(intent, 1);
            }
        });
        mc = new MainController(getBaseContext());
        low = (ListView) findViewById(R.id.lowList);
        medium = (ListView) findViewById(R.id.medList);
        high = (ListView) findViewById(R.id.highList);
        otherList = db.getCards();
        cardList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.mytextview);
        lowAdapter = new ArrayAdapter<String>(this, R.layout.mytextview);
        medAdapter = new ArrayAdapter<String>(this, R.layout.mytextview);
        highAdapter = new ArrayAdapter<String>(this, R.layout.mytextview);
        for(int i = 0; i<otherList.size(); i++)
        {
            if(otherList.get(i).getRating() != 4) {
                if (otherList.get(i).getSelected()) {
                    cardList.add(otherList.get(i));
                    switch(otherList.get(i).getPriority())
                    {
                        case 0:
                            arrayAdapter.add(otherList.get(i).getName());
                            break;
                        case 1:
                            lowAdapter.add(otherList.get(i).getName());
                            break;
                        case 2:
                            medAdapter.add(otherList.get(i).getName());
                            break;
                        case 3:
                            highAdapter.add(otherList.get(i).getName());
                            break;
                    }
                }
            }
        }
        list = (ListView) findViewById(R.id.list2);
        findViewById(R.id.list2).setOnDragListener(new listListener("Main"));
        list.setAdapter(arrayAdapter);
        low.setAdapter(lowAdapter);
        medium.setAdapter(medAdapter);
        high.setAdapter(highAdapter);
        list.setOnDragListener(new listListener("list"));
        low.setOnDragListener(new listListener("low"));
        medium.setOnDragListener(new listListener("medium"));
        high.setOnDragListener(new listListener("high"));
        list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = arrayAdapter;
                return true;
            }

        });
        low.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                boolean b = view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = lowAdapter;
                for(int i = 0; i<cards.size();i++) {
                    if (cards.get(i).getName().equals(selected) == true) {
                        cards.get(i).setPriority(0);
                        }
                    }
                return true;
            }
        });
        medium.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                boolean b = view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = medAdapter;
                for(int i = 0; i<cards.size();i++) {
                    if (cards.get(i).getName().equals(selected) == true) {
                        cards.get(i).setPriority(0);
                    }
                }
                return true;
            }
        });
        high.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = highAdapter;
                for(int i = 0; i<cards.size();i++) {
                    if (cards.get(i).getName().equals(selected) == true) {
                        cards.get(i).setPriority(0);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Prioritise the competencies you have chosen to work on for your next training block.\n This is done via dragging the item and placing it on the right from top to bottom. Top being the highest priority and bottom being the lowest.\nMake sure to click on the calendar on the top of the screen to schedule the next sort.")
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
        if(id==R.id.calendarInfo)
        {
            Intent intent = new Intent(getBaseContext(), DateFragment.class);
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

    public void toOutput(View v)

    {
        Athlete a = db.getAthlete();
        String s = "";
        if (a.getSkillLevel() == 1)
            s = "Emerging Talent / New Team Member";
        else if(a.getSkillLevel() == 2)
            s = "Team Member / Gaining Experience";
        else if(a.getSkillLevel() == 3)
            s = "Senior Player / Leader";

        Intent intent = new Intent(getBaseContext(), OutputActivity.class);
        ArrayList<String> strengths = new ArrayList<>();
        ArrayList<String> mediums = new ArrayList<>();
        ArrayList<String> workons = new ArrayList<>();
        ArrayList<String> pos = new ArrayList<>();
        for(int i = 0; i < cardList.size(); i++)
        {
            if(!pos.contains(cardList.get(i).getPositionName()))
            {
                if(!cardList.get(i).getPositionName().equals("Global"))
                    pos.add(cardList.get(i).getPositionName());
            }
        }
        for(int i = 0; i< cardList.size(); i++)
        {
            if(cardList.get(i).getRating() == 1)
            {
                workons.add(cardList.get(i).getName());
            }
            if(cardList.get(i).getRating() == 2)
            {
                mediums.add(cardList.get(i).getName());
            }
            if(cardList.get(i).getRating() == 3)
            {
                strengths.add(cardList.get(i).getName());
            }
        }
        ArrayList<String> high = new ArrayList<>();
        ArrayList<String> middle = new ArrayList<>();
        ArrayList<String> low = new ArrayList<>();
        for(int i = 0; i< cardList.size(); i++)
        {
            if(cardList.get(i).getPriority() == 1)
            {
                low.add(cardList.get(i).getName());
            }
            if(cardList.get(i).getPriority() == 2)
            {
                middle.add(cardList.get(i).getName());
            }
            if(cardList.get(i).getPriority() == 3)
            {
                high.add(cardList.get(i).getName());
            }
        }
        if(a.getTerms2()) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate;
            try {
                startDate = df.parse(a.getDob());
                String newDateString = df.format(startDate);
                System.out.println(newDateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                if (!a.getTerms1()) {
                    AthleteInfo info = new AthleteInfo("NO MARKETING " + a.getEmail(), cal, a.getGender(), s, a.getTeams(), pos, strengths, mediums, workons, high, middle, low, a.getLocation(), a.getZipCode());
                    info.createInfo();
                }
                else if(a.getTerms1()) {
                        AthleteInfo info = new AthleteInfo(a.getEmail(), cal, a.getGender(), s, a.getTeams(), pos, strengths, mediums, workons, high, middle, low, a.getLocation(), a.getZipCode());
                        info.createInfo();
                    }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
        finish();
    }
    class listListener implements View.OnDragListener{
        String name;
        listListener(String tag){
            name = tag;
        }
        @Override
        public boolean onDrag(View v, DragEvent event) {
            Boolean b = Boolean.FALSE;
            int action = event.getAction();
            ListView list = (ListView) v;
            ArrayAdapter<String> adapt = (ArrayAdapter<String>) list.getAdapter();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    list.setBackgroundColor(Color.TRANSPARENT);
                    TextView drag = (TextView) event.getLocalState();
                    adapt.add(selected);
                    list.setAdapter(adapt);
                    for(int i = 0; i<cardList.size(); i++)
                    {
                        if(selected.equals(cardList.get(i).getName()))
                        {
                            Card c = cardList.get(i);
                            if(name.equals("low"))
                            {
                                c.setPriority(1);
                            }
                            else if(name.equals("medium")) {
                                c.setPriority(2);
                            }
                                else if(name.equals("high")){
                                    c.setPriority(3);
                                }
                                else {
                                    c.setPriority(0);
                                }
                            db.updatePriority(c);
                            b = Boolean.TRUE;
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    list.setBackgroundColor(Color.LTGRAY);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    list.setBackgroundColor(Color.TRANSPARENT);
                default:
                    break;
            }
            if(b)
                selectedAdapter.remove(selected);
            return true;
        }
    }
    public void toSelection(View v){
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        startActivity(intent);
        finish();
    }
    public void toSort(View v){
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        startActivity(intent);
        finish();
    }
}
