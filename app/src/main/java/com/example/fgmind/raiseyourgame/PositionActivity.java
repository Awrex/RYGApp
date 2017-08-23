package com.example.fgmind.raiseyourgame;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PositionActivity extends Activity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.mainListView );

        // Create and populate a List of position names.
        String[] positions = new String[] { "Batting", "Fielding", "Wicket Keeping", "Bowling",
                "Captaincy", "Character / Team", "Well Being", "Physical", "Mental"};
        ArrayList<String> positionList = new ArrayList<String>();
        positionList.addAll( Arrays.asList(positions) );

        // Create ArrayAdapter using the position list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.row_position, positionList);

        // Add more positions. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
//        listAdapter.add( "Ceres" );
//        listAdapter.add( "Pluto" );
//        listAdapter.add( "Haumea" );
//        listAdapter.add( "Makemake" );
//        listAdapter.add( "Eris" );

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );
    }

}