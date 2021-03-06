package com.ryg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * OutputActivity
 * Created by Alex Stewart
 *
 * The activity combined with the OutputAdapter for a ListView.
 * It shows the cards the user has set priorities as High and Medium, it also allows for Low functionality.
 */
public class OutputActivity extends AppCompatActivity {
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<Card> sortedList = new ArrayList<>();
    private DBController db;
    private ListView highList, medList, lowList;
    private OutputAdapter highAdapter;
    private OutputAdapter medAdapter;
    private OutputAdapter lowAdapter;

    /**
     * Creates the menu toolbar.
     * Also checks if the user has a profile picture and displays it as an icon.
     * @param menu
     */
    @Override
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
        setContentView(R.layout.activity_output2);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        menuToolbar.setSubtitle("My competencies");
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        db = new DBController(this);
        highList = (ListView) findViewById(R.id.highList);
        medList = (ListView) findViewById(R.id.medList);
        cardList = db.getCards();
        lowAdapter = new OutputAdapter(this);
        medAdapter = new OutputAdapter(this);
        highAdapter = new OutputAdapter(this);
        //Adding the items to each adapter.
        for(int i = 0; i < cardList.size(); i++)
        {
            if(cardList.get(i).getPriority() != 0&& cardList.get(i).getSelected())
            {
                switch (cardList.get(i).getPriority()){
                    case 1:
                        lowAdapter.add(cardList.get(i));
                        break;
                    case 2:
                        medAdapter.add(cardList.get(i));
                        break;
                    case 3:
                        highAdapter.add(cardList.get(i));
                        break;
                }
                sortedList.add(cardList.get(i));
            }
        }
        highList.setAdapter(highAdapter);
        medList.setAdapter(medAdapter);
    }

    /** onOptionsItemSelected
     * Checks what item is pressed and acts on it
     * @param item (MenuItem) - An item in the top toolbar is pressed, this is what item it is.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //gives information on the activity(what to do)
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("These are the competencies you have chosen as high or medium priority for your next training block.  \nGreen = Mental, \nYellow = Life balance/Wellbeing, \nRed = team/character, \nBlack = Technical skills, \nOrange = Physical, \nBlue = Captaincy. ")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        //sends the user to the user creation/update activity.
        if(id==R.id.userIcon)
        {
            Intent intent = new Intent(getBaseContext(), CreateUserActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Goes to its designated activity after the button is pressed
     * @param v (View)
     */
    public void toSelection(View v)
    {
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        startActivity(intent);
        finish();
    }
    public void toSort(View v)
    {
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Goes to its designated activity after the back button on the phone is pressed
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), SecondSortActivity.class);
        startActivity(intent);
        finish();
    }

    /** toPlan
     * Currently not included, as the action plan app has not been completed/has implementation for the app.
     * Will be included
     * @param v
     */
    public void toPlan(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("In this current version of the app, action planning has yet to be implemented.")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /** toEmail
     * Gets the user to specify their email client of choice, then sends an email to whoever they want(their own email as default).
     * @param v
     */
    public void toEmail(View v)
    {
        Athlete a = new Athlete();
        a = db.getAthlete();
        String fullthing = "";
        String highs = "";
        String mediums = "";
        String lows = "";
        for(int i = 0; i < sortedList.size(); i++)
        {
            switch (sortedList.get(i).getPriority()) {
                case 3:
                    highs += sortedList.get(i).getName() + ", You rated your proficiency in this skill as - ";
                    switch (sortedList.get(i).getRating()) {
                        case 3:
                            highs += "Strength.\n\n";
                            break;
                        case 2:
                            highs += "Medium.\n\n";
                            break;
                        case 1:
                            highs += "Work on.\n\n";
                            break;
                    }
                    break;
                case 2:
                    mediums += sortedList.get(i).getName() + ", You rated your proficiency in this skill as - ";
                    switch (sortedList.get(i).getRating()) {
                        case 3:
                            mediums += "Strength.\n\n";
                            break;
                        case 2:
                            mediums += "Medium.\n\n";
                            break;
                        case 1:
                            mediums += "Work on.\n\n";
                            break;
                    }
                    break;
                case 1:
                    lows += sortedList.get(i).getName() + ", You rated your proficiency in this skill as - ";
                    switch (sortedList.get(i).getRating()) {
                        case 3:
                            lows += "Strength.\n\n";
                            break;
                        case 2:
                            lows += "Medium.\n\n";
                            break;
                        case 1:
                            lows += "Work on.\n\n";
                            break;
                    }
            }
        }
        fullthing += "YOUR CARD SORT \n\n Hello " + a.getName()+ ".\n\n\n This is your card sort: \n\n\nHighs\n\n" + highs + "\nMediums:\n\n" + mediums +"\nLows:\n\n" + lows +"\nYour next sort is on:" + a.getDateOf();
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{a.getEmail()});
        email.putExtra(Intent.EXTRA_SUBJECT, "My card sort:");
        email.putExtra(Intent.EXTRA_TEXT, fullthing);

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

}
