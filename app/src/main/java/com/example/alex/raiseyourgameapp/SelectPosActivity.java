package com.example.alex.raiseyourgameapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectPosActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> cardList = new ArrayList<>();
    private String category;
    private Boolean firstTime = false;
    private TextView cardNum;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_position);
        cards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.posList );
                // Create ArrayAdapter using the position list.
        firstTime = getIntent().getBooleanExtra("FIRSTTIME",true);
        if(firstTime)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Select the category you would like to sort by tapping on the word.")
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

        // Add more positions. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
//        listAdapter.add( "Ceres" );
//        listAdapter.add( "Pluto" );
//        listAdapter.add( "Haumea" );
//        listAdapter.add( "Makemake" );
//        listAdapter.add( "Eris" );
        for (int i = 0; i < cards.size(); i++) {
            if (!cardList.contains(cards.get(i).getCategory()))
                cardList.add(cards.get(i).getCategory());
        }
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cardList);
        mainListView.setAdapter( listAdapter );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = (String) parent.getItemAtPosition(position);
                goToCard();
            }
        });
        if(listAdapter.getCount()==0)
        {
            sortGo();
        }
        cardNum = (TextView) findViewById(R.id.numLeft);
        cardNum.setText(Integer.toString(cards.size()));
    }
    public void goToCard() {
        Intent intent = new Intent(getBaseContext(), CardActivity.class);
        intent.putExtra("CARDLIST", cards);
        intent.putExtra("CATEGORY",category);
        intent.putExtra("FIRSTTIME",firstTime);
        startActivity(intent);
        finish();
    }
    public void sortGo(){
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToSort(View v) {
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }

}