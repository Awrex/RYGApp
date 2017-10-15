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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReviewSort extends AppCompatActivity {
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayAdapter<String> selectedAdapter;
    private ArrayAdapter<String> workOnAdapter;
    private ArrayAdapter<String> mediumAdapter;
    private ArrayAdapter<String> strengthAdapter;
    private TextView workOns, mediums, strengths;
    private String selected;
    private Button refresh, next, back, reset;
    int num = 0;
    int workon = 0;
    int med = 0;
    int high = 0;
    private ListView workonList;
    private ListView mediumList;
    private ListView strengthList;
    private DBController db;
    private String name;

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
            MenuItem m = menu.getItem(1);
            m.setIcon(getResources().getDrawable(R.drawable.noavatar));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewsort);
        db = new DBController(this);
        cardList = db.getCards();
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        workOnAdapter = new ArrayAdapter<String>(this, R.layout.mytextview){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = "";
                if(workOnAdapter.getCount() != 0) {
                    s = workOnAdapter.getItem(position);
                }
                for(int i = 0; i < cardList.size(); i++)
                {
                    if(s.equals(cardList.get(i).getName()))
                    {
                        cardList.get(i).setSelected(Boolean.TRUE);
                        view.setBackgroundColor(Color.RED);
                        view.getBackground().setAlpha(190);
                        db.updateSelected(cardList.get(i));
                    }
                }
                return view;
            }
        };
        mediumAdapter = new ArrayAdapter<String>(this, R.layout.mytextview){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = "";
                if (mediumAdapter.getCount() != 0) {
                    s = mediumAdapter.getItem(position);
                }
                for (int i = 0; i < cardList.size(); i++) {
                    if (s.equals(cardList.get(i).getName())) {
                        if(cardList.get(i).getSelected()) {
                            view.setBackgroundColor(Color.RED);
                            view.getBackground().setAlpha(190);
                        }
                        else if (!cardList.get(i).getSelected()) {
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                }
                updateTextViews();
                return view;
            }
        };
        strengthAdapter = new ArrayAdapter<String>(this, R.layout.mytextview){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = "";
                if (strengthAdapter.getCount() != 0) {
                    s = strengthAdapter.getItem(position);
                }
                for (int i = 0; i < cardList.size(); i++) {
                    if (s.equals(cardList.get(i).getName())) {
                        if(cardList.get(i).getSelected()) {
                            view.setBackgroundColor(Color.RED);
                            view.getBackground().setAlpha(190);
                        }
                        else if (!cardList.get(i).getSelected()) {
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                }
                updateTextViews();
                return view;
            }
        };
        refresh = (Button) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        next = (Button) findViewById(R.id.toSecond);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortCards();
            }
        });

        back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        workOns = (TextView) findViewById(R.id.workOnCount);
        mediums = (TextView) findViewById(R.id.mediumCount);
        strengths = (TextView) findViewById(R.id.strengthCount);

        workonList = (ListView) findViewById(R.id.workOnList);
        mediumList = (ListView) findViewById(R.id.mediumList);
        strengthList = (ListView) findViewById(R.id.strengthList);
        workon = 0;
        med = 0;
        high = 0;
        for (int i = 0; i < cardList.size(); i++) {
            if(cardList.get(i).getRating() == 0){
                cardList.get(i).setSelected(Boolean.FALSE);
            }
            if (cardList.get(i).getRating() == 1) {
                name = cardList.get(i).getName();
                cardList.get(i).setSelected(Boolean.TRUE);
                workOnAdapter.add(name);
                num++;
                workon++;
            }
            if (cardList.get(i).getRating() == 2) {
                name = cardList.get(i).getName();
                if(cardList.get(i).getSelected())
                    med++;
                mediumAdapter.add(name);
            }
            if (cardList.get(i).getRating() == 3) {
                name = cardList.get(i).getName();
                if(cardList.get(i).getSelected())
                    high++;
                strengthAdapter.add(name);
            }
        }
        workonList.setAdapter(workOnAdapter);
        mediumList.setAdapter(mediumAdapter);
        strengthList.setAdapter(strengthAdapter);
        workonList.setOnDragListener(new listListener(1,"workon"));
        mediumList.setOnDragListener(new listListener(2,"medium"));
        strengthList.setOnDragListener(new listListener(3,"strength"));
        workonList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                view.setBackgroundColor(Color.TRANSPARENT);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected) == true) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            workon--;
                            db.updateSelected(cardList.get(i));
                        }
                    }
                }
                view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = workOnAdapter;
                updateTextViews();
                return true;
            }
        });
        mediumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                view.setBackgroundColor(Color.TRANSPARENT);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = mediumAdapter;
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected) == true) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            med--;
                            db.updateSelected(cardList.get(i));
                        }
                    }
                }
                updateTextViews();
                return true;
            }
        });
        strengthList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                view.setBackgroundColor(Color.TRANSPARENT);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                selectedAdapter = strengthAdapter;
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected) == true) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            high--;
                            db.updateSelected(cardList.get(i));
                        }
                    }
                }
                updateTextViews();
                return true;
            }
        });
        workonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected) == true) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            workon--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            workon++;
                            view.setBackgroundColor(Color.RED);
                            view.getBackground().setAlpha(190);
                        }
                        db.updateSelected(cardList.get(i));
                    }
                }
                updateTextViews();
            }
        });
/*
        workonList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), cardDesc.class);
                intent.putExtra("TITLE",selected);
                for(int i = 0; i<cardList.size();i++)
                {
                    if(cardList.get(i).getName().equals(selected))
                    {
                        intent.putExtra("SHORT",cardList.get(i).getShortDesc());
                        intent.putExtra("COMMENT",cardList.get(i).getComment());
                    }
                }
                startActivity(intent);
                return true;
            }
        });
       */
        mediumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected)) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            med--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            med++;
                            view.setBackgroundColor(Color.RED);
                            view.getBackground().setAlpha(190);
                        }
                        db.updateSelected(cardList.get(i));
                    }

                }
                updateTextViews();
            }
        });
        /*mediumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), cardDesc.class);
                intent.putExtra("TITLE",selected);
                for(int i = 0; i<cardList.size();i++)
                {
                    if(cardList.get(i).getName().equals(selected))
                    {
                        intent.putExtra("SHORT",cardList.get(i).getShortDesc());
                        intent.putExtra("COMMENT",cardList.get(i).getComment());
                    }
                }
                startActivity(intent);
                return true;
            }
        });*/
        strengthList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected)) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            high--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            high++;
                            view.setBackgroundColor(Color.RED);
                            view.getBackground().setAlpha(190);
                        }
                        db.updateSelected(cardList.get(i));
                    }
                }
                updateTextViews();
            }
        });
        /*strengthList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), cardDesc.class);
                intent.putExtra("TITLE",selected);
                for(int i = 0; i<cardList.size();i++)
                {
                    if(cardList.get(i).getName().equals(selected))
                    {
                        intent.putExtra("SHORT",cardList.get(i).getShortDesc());
                        intent.putExtra("COMMENT",cardList.get(i).getComment());
                    }
                }
                startActivity(intent);
                return true;
            }
        });*/
        updateTextViews();

    }
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(getBaseContext(), SelectCategory.class);
                startActivity(intent);
                finish();
        }
        public void reset(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to clear your sort and start again?")
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int button) {
                            db.updateAll(cardList);
                            back();
                        }
                    }).setNegativeButton(android.R.string.no, null).show();;
        }
        public void back(){
            Intent intent = new Intent(getBaseContext(), SelectCategory.class);
                startActivity(intent);
                finish();
        }
        public void sortCards(){
            Intent intent = new Intent(getBaseContext(), SecondSort.class);
            intent.putExtra("CARDLIST",cardList);
            startActivity(intent);
                finish();
        }
    public void refresh()
    {
        finish();
        startActivity(getIntent());
    }
    public void updateTextViews()
    {
        workOns.setText("Selected: " + workon);
        mediums.setText("Selected: " + med);
        strengths.setText("Selected: " + high);
    }

    class listListener implements View.OnDragListener {
        int rating;
        String name;

        listListener(int rate, String tag) {
            rating = rate;
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
                    for (int i = 0; i < cardList.size(); i++) {
                        if (selected.equals(cardList.get(i).getName())) {
                            Card c = cardList.get(i);
                            c.setRating(rating);
                            db.updateRating(c);
                            b = Boolean.TRUE;
                            db.updateSelected(c);
                        }
                    }
                    if (name.equals("workon")) {
                        for (int i = 0; i < cardList.size(); i++) {
                            if (cardList.get(i).getName().equals(selected) == true) {
                                cardList.get(i).setSelected(Boolean.TRUE);
                                num++;
                                workon++;
                                b = Boolean.TRUE;
                                db.updateSelected(cardList.get(i));
                            }
                        }
                    }

                    updateTextViews();
                    adapt.notifyDataSetChanged();
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    list.setBackgroundColor(Color.LTGRAY);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    list.setBackgroundColor(Color.TRANSPARENT);
                default:
                    break;
            }
            if (b) {
                selectedAdapter.remove(selected);
            }
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Tap a competency to select the card to add it to your second sort.\n Hold and drag to move competency to new category).")
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
            Intent intent = new Intent(getBaseContext(), CreateUser.class);
            startActivityForResult(intent, 1);
        }
        return true;
    }
}
