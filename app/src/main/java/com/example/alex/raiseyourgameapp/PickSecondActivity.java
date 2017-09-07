package com.example.alex.raiseyourgameapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PickSecondActivity extends AppCompatActivity {
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayAdapter<String> workOnAdapter;
    private ArrayAdapter<String> mediumAdapter;
    private ArrayAdapter<String> strengthAdapter;
    private String selected;
    private int num = 0;
    private int workon = 0;
    private int med = 0;
    private int high = 0;
    private TextView selectedItem;
    private ListView workonList;
    private ListView mediumList;
    private ListView strengthList;
    private DBController db;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picksecondsort);
        db = new DBController(this);
        cardList = db.getCards();
        workOnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){
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
                        view.setBackgroundColor(Color.RED);
                    }
                }
                return view;
            }

        };
        mediumAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        strengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        workonList = (ListView) findViewById(R.id.workOnList);
        mediumList = (ListView) findViewById(R.id.mediumList);
        strengthList = (ListView) findViewById(R.id.strengthList);
        selectedItem = (TextView) findViewById(R.id.selectedView);
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
                cardList.get(i).setSelected(Boolean.FALSE);
                mediumAdapter.add(name);
                med++;
            }
            if (cardList.get(i).getRating() == 3) {
                name = cardList.get(i).getName();
                cardList.get(i).setSelected(Boolean.FALSE);
                strengthAdapter.add(name);
                high++;
            }
        }
        selectedItem.setText(Integer.toString(num));
        if(num > 10)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You currently have " + Integer.toString(workon) + " work on cards selected for your second sort!\nIt's advised you bring this number down.")
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
        workonList.setAdapter(workOnAdapter);
        mediumList.setAdapter(mediumAdapter);
        strengthList.setAdapter(strengthAdapter);
        workonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected) == true) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                }
                selectedItem.setText(Integer.toString(num));
            }
        });
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
        mediumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected)) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                }
                selectedItem.setText(Integer.toString(num));
            }
        });
        mediumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        strengthList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                for(int i = 0; i<cardList.size();i++) {
                    if (cardList.get(i).getName().equals(selected)) {
                        if (cardList.get(i).getSelected()) {
                            cardList.get(i).setSelected(Boolean.FALSE);
                            num--;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            cardList.get(i).setSelected(Boolean.TRUE);
                            num++;
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                }
                selectedItem.setText(Integer.toString(num));
            }
        });
        strengthList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
    }
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(getBaseContext(), StartScreen.class);
                startActivity(intent);
                finish();
        }
        public void back(View v){
            Intent intent = new Intent(getBaseContext(), StartScreen.class);
                startActivity(intent);
                finish();
        }
        public void sortCards(View v){
            Intent intent = new Intent(getBaseContext(), SecondSort.class);
            intent.putExtra("CARDLIST",cardList);
            startActivity(intent);
                finish();
        }
    }
