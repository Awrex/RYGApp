package com.example.alex.raiseyourgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ObsoletedReviewSort extends AppCompatActivity {
    private View row;
    private int pos;
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<String> selectedPositions = new ArrayList<>();
    private ArrayAdapter<String> workOnAdapter;
    private ArrayAdapter<String> mediumAdapter;
    private ArrayAdapter<String> strengthAdapter;
    private String selected;
    private int num = 0;
    private Boolean itemSelected = false;
    private TextView selectedItem;
    private ListView workonList;
    private ListView mediumList;
    private ListView strengthList;
    private DBController db;
    private String name;
    private Button toSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obsoleted_reviewsort);
        db = new DBController(this);
        cardList = db.getCards();
        toSecond = (Button) findViewById(R.id.toSecond);
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ReviewSort.class);
                startActivity(intent);
                finish();
            }
        });
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
                        view.setBackgroundColor(cardList.get(i).getColour());
                        view.getBackground().setAlpha(170);
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
                if(mediumAdapter.getCount() != 0) {
                    s = mediumAdapter.getItem(position);
                }
                for(int i = 0; i < cardList.size(); i++)
                {
                    if(s.equals(cardList.get(i).getName()))
                    {
                        view.setBackgroundColor(cardList.get(i).getColour());
                        view.getBackground().setAlpha(170);
                    }
                }
                return view;
            }

        };
        strengthAdapter = new ArrayAdapter<String>(this, R.layout.mytextview){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = "";
                if(strengthAdapter.getCount() != 0) {
                    s = strengthAdapter.getItem(position);
                }
                for(int i = 0; i < cardList.size(); i++)
                {
                    if(s.equals(cardList.get(i).getName()))
                    {
                        view.setBackgroundColor(cardList.get(i).getColour());
                        view.getBackground().setAlpha(170);
                    }
                }
                return view;
            }

        };
        workonList = (ListView) findViewById(R.id.workOnList);
        mediumList = (ListView) findViewById(R.id.mediumList);
        strengthList = (ListView) findViewById(R.id.strengthList);
        selectedItem = (TextView) findViewById(R.id.selectedView);
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getRating() == 1) {
                name = cardList.get(i).getName();
                /*char[] n = name.toCharArray();
                String newName = "";
                newName += n[0];
                for (int j = 1; j < n.length; j++) {
                    if (Character.isUpperCase(n[j]))
                        newName += " " + n[j];
                    else
                        newName += n[j];
                }
                name = newName;*/
                workOnAdapter.add(name);
            }

            if (cardList.get(i).getRating() == 2) {
                name = cardList.get(i).getName();
                /*char[] n = name.toCharArray();
                String newName = "";
                newName += n[0];
                for (int j = 1; j < n.length; j++) {
                    if (Character.isUpperCase(n[j]))
                        newName += " " + n[j];
                    else
                        newName += n[j];
                }
                name = newName;*/
                mediumAdapter.add(name);
            }
            if (cardList.get(i).getRating() == 3) {
                name = cardList.get(i).getName();
                /*char[] n = name.toCharArray();
                String newName = "";
                newName += n[0];
                for (int j = 1; j < n.length; j++) {
                    if (Character.isUpperCase(n[j]))
                        newName += " " + n[j];
                    else
                        newName += n[j];
                }
                name = newName;*/
                strengthAdapter.add(name);
            }
        }
        workonList.setAdapter(workOnAdapter);
        mediumList.setAdapter(mediumAdapter);
        strengthList.setAdapter(strengthAdapter);
        workonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                num = 1;
                selectedItem.setText(selected);
                itemSelected = true;

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
                num = 2;
                selectedItem.setText(selected);
                itemSelected = true;
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
                num = 3;
                selectedItem.setText(selected);
                itemSelected = true;
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
        public void addWorkOn(View v) {
            if (itemSelected && num != 1) {
                workOnAdapter.add(selected);
                workOnAdapter.notifyDataSetChanged();
                itemSelected = false;
                if(num == 2){
                    mediumAdapter.remove(selected);
                    mediumAdapter.notifyDataSetChanged();
                }
                if(num == 3){
                    strengthAdapter.remove(selected);
                    strengthAdapter.notifyDataSetChanged();
                }
                for(int i = 0; i<cardList.size();i++)
                {
                    if(selected.equals(cardList.get(i).getName()))
                    {
                        Card c = cardList.get(i);
                        c.setRating(1);
                        db.updateRating(c);
                    }
                }
                selectedItem.setText("");
            }

        }
        public void medWorkOn(View v) {
            if (itemSelected && num != 2) {
            mediumAdapter.add(selected);
            mediumAdapter.notifyDataSetChanged();
            itemSelected = false;
                if(num == 3){
                    strengthAdapter.remove(selected);
                    strengthAdapter.notifyDataSetChanged();
                }
                if(num == 1){
                    workOnAdapter.remove(selected);
                    workOnAdapter.notifyDataSetChanged();
                }
                for(int i = 0; i<cardList.size();i++)
                {
                    if(selected.equals(cardList.get(i).getName()))
                    {
                        Card c = cardList.get(i);
                        c.setRating(2);
                        db.updateRating(c);
                    }
                }
                selectedItem.setText("");
            }

        }
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(getBaseContext(), TitleScreen.class);
                startActivity(intent);
                finish();
        }
        public void back(View v){
            Intent intent = new Intent(getBaseContext(), SelectPosActivity.class);
            startActivity(intent);
            finish();
        }
        public void strWorkOn(View v) {
            if (itemSelected && num != 3 && itemSelected) {
            strengthAdapter.add(selected);
            strengthAdapter.notifyDataSetChanged();
            itemSelected = false;
                if(num == 2){
                    mediumAdapter.remove(selected);
                    mediumAdapter.notifyDataSetChanged();
                }
                if(num == 1){
                    workOnAdapter.remove(selected);
                    workOnAdapter.notifyDataSetChanged();
                }
                for(int i = 0; i<cardList.size();i++)
                {
                    if(selected.equals(cardList.get(i).getName()))
                    {
                        Card c = cardList.get(i);
                        c.setRating(3);
                        db.updateRating(c);
                    }
                }
                selectedItem.setText("");
            }

        }

}
