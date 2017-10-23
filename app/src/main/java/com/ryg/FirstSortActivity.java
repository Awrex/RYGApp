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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Alex Stewart
 * FirstSortActivity uses a ViewPager and a StatePagerAdapter to store an array of Fragments
 * These fragments are displayed in the layout and can be scrolled through.
 */
public class FirstSortActivity extends AppCompatActivity{
    private static final String TAG = "CardActivity";
    private StatePagerAdapter cardAdapter;
    private ViewPager viewPager;
    private DBController db = new DBController(this);
    private ArrayList<Card> rawCards = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> sortedCards = new ArrayList<>();
    private ArrayList<Card> undoCards = new ArrayList<>();
    private String category;
    private Boolean firstTime;
    /**
     * Creates the menu toolbar.
     * Also checks if the user has a profile picture and displays it as an icon.
     * @param menu
     */
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
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Log.d(TAG, "onCreate: Started.");
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        menuToolbar.setSubtitle("Rate the competencies below");
        category = getIntent().getStringExtra("CATEGORY");
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        rawCards = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        /**
         * Runs through the list of cards given to the activity from SelectPosition
         * it then creates the ArrayList of cards displayed in the ViewPager.
         */
        for(int i = 0; i<rawCards.size();i++)
        {
            if(category.equals("Skills") && !rawCards.get(i).getCategory().equals("Mental") &&
                    !rawCards.get(i).getCategory().equals("Physical")&&
                    !rawCards.get(i).getCategory().equals("Wellbeing")&&
                    !rawCards.get(i).getCategory().equals("Skills")&&
                    !rawCards.get(i).getCategory().equals("Character/Team"))
                cards.add(rawCards.get(i));
            else if (rawCards.get(i).getCategory().equals(category))
            {
                cards.add(rawCards.get(i));
            }
            else if (category.equals("All"))
            {
                cards.add(rawCards.get(i));
            }
        }
        viewPager = (ViewPager) findViewById(R.id.cardPager);
        setupViewPager(viewPager, cards);
    }

    /** onOptionsItemSelected
     * Checks what item is pressed and acts on it
     * @param item (MenuItem) - An item in the top toolbar is pressed, this is what item it is.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Displays the information on the activity(what to do)
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The cards will appear in front of you in the middle of the screen." +
                    "\nYou can sort them by using the buttons at the top, and can undo with the arrow button on the bottom." +
                    "\nYou can also leave comments and swipe left to go to the next card at any time.")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            firstTime = Boolean.FALSE;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        //sends the user to the user creation/update screen.
        if(id==R.id.userIcon)
        {
            Intent intent = new Intent(getBaseContext(), CreateUserActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When back is pressed, it goes back to the SelectCategoryActivity activity class.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        intent.putExtra("CARDLIST", rawCards);
        intent.putExtra("FIRSTTIME", firstTime);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }

    public void sortLater(View v){ setRating(v,4); }

    public void workOn(View v){setRating(v,1);}
    public void med(View v){setRating(v,2);}
    public void strength(View v){setRating(v,3);}

    /** setRating
     * Checks what the current item(card) is displayed on the screen.
     * It then changes the value of that card to what the value of the button pressed is.
     * @param v (View)
     * @param rate (What the value of the button pressed is)
     */
    public void setRating(View v,int rate){
        int pos = viewPager.getCurrentItem();
        if(cards.size()!=0 && cards != null) {
            if (pos == cards.size()) {
                pos -= 1;
            }
            Card tempCard = cards.get(pos);
            rawCards.remove(tempCard);
            tempCard.setNum(pos);
            tempCard.setRating(rate);
            sortedCards.add(tempCard);
            undoCards.add(tempCard);
            cards.remove(pos);
            setupViewPager(viewPager, cards);
            viewPager.setCurrentItem(pos);
            if (cards.size()==0)
            {
                toSelect();
            }
        }
    }

    /** undoSort
     * An undo button, has an internal list of cards that were sorted, simply places the last card on that list back into the ViewPager.
     * @param v (View)
     */
    public void undoSort(View v)
    {
            int currentPos = 0;
        if(undoCards!=null && undoCards.size() != 0) {
            Card tempCard = undoCards.get(undoCards.size() - 1);
            currentPos = tempCard.getNum();
            cards.add(currentPos, tempCard);
            rawCards.add(currentPos, tempCard);
            undoCards.remove(undoCards.size() - 1);
            setupViewPager(viewPager, cards);
            viewPager.setCurrentItem(currentPos);
        }
    }

    /** setupViewPager
     * Sets up the ViewPager, creates CardFragments based on each card in a category.
     * It then Places each fragment into a StatePagerAdapter, and displays the adapter in the ViewPager.
     * @param viewPager (ViewPager)
     * @param cList (ArrayList<Card>)
     */
    private void setupViewPager(ViewPager viewPager, ArrayList<Card> cList){
        cardAdapter = new StatePagerAdapter(getSupportFragmentManager());
        for (int i = 0; i<cList.size(); i++) {
            CardFragment cf = new CardFragment();
                cf.addCard(cList.get(i), cList.get(i).getName());
                cardAdapter.addItem(cf, cList.get(i).getName());
        }
        viewPager.setAdapter(cardAdapter);
    }

    /** leaveComment
     * Leaves a comment on the currently displayed card.
     * @param v (View)
     */
    public void leaveComment(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setTitle("Enter your comment");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Card c;
                c = cards.get(viewPager.getCurrentItem());
                c.setComment(input.getText().toString());
                db.updateComment(c);
                cardAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Goes to its designated activity after the button is pressed
     * @param v (View)
     */
    public void toSelection(View v)
    {
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }
    public void toSort(View v)
    {
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        sortedCards.clear();
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    public void toSelect()
    {
        Intent intent = new Intent(getBaseContext(), SelectCategoryActivity.class);
        for(int i = 0; i<sortedCards.size(); i++) {
            db.updateRating(sortedCards.get(i)); }
        startActivity(intent);
        finish();
    }
}
