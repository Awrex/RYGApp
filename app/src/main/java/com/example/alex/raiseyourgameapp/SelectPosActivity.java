package com.example.alex.raiseyourgameapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectPosActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> cardList = new ArrayList<>();
    private String category;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_position);
        cards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.posList );
                // Create ArrayAdapter using the position list.


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
    }
    public void goToCard() {
        Intent intent = new Intent(getBaseContext(), CardActivity.class);
        intent.putExtra("CARDLIST", cards);
        intent.putExtra("CATEGORY",category);
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