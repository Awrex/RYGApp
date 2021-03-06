package com.ryg;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * SelectPositionsActivity
 * Created By Alex Stewart
 *
 * Brings up a ListView for the user to select what positions they play,
 * If they select an item it will get a green background to signify it's selected.
 * The positions selected are saved into the database and then the user is sent to the SelectCategoryActivity class.
 */

public class SelectPositionsActivity extends AppCompatActivity {
    private ArrayList<Position> posList;
    private MainController mc = new MainController(this);
    private ArrayList<Card> cardList = new ArrayList<>();
    ArrayList<String> oldPositions = new ArrayList<>();
    private ArrayList<String> selectedPositions = new ArrayList<>();
    private ArrayList<String> deletedPositions = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView selectionList;
    private Button reviewButton, secondButton, outputButton;
    private boolean loaded= Boolean.FALSE, review= Boolean.FALSE, second= Boolean.FALSE, output = Boolean.FALSE;
    private DBController db;
    private Athlete a;
    String fileName = "userImage.png";
    File f;
    TextView userText;
    ImageView profile;
    @Override
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

    /**
     * Is run when the profile picture icon is clicked.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBController(this);
        setContentView(R.layout.activity_position);
        posList = new ArrayList<>();
        posList = db.getPositions();
        a = new Athlete();
        a = db.getAthlete();
        reviewButton = (Button)findViewById(R.id.reviewMenu);
        secondButton = (Button)findViewById(R.id.secondMenu);
        outputButton = (Button)findViewById(R.id.outputMenu);
        //The buttons start out invisible.
        reviewButton.setEnabled(false);
        reviewButton.setVisibility(View.INVISIBLE);
        secondButton.setEnabled(false);
        secondButton.setVisibility(View.INVISIBLE);
        outputButton.setEnabled(false);
        outputButton.setVisibility(View.INVISIBLE);

        //Setting up the adapter to change colours if the item has been or has already been selected.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String s = adapter.getItem(position);
                for(int i = 0; i < posList.size(); i++)
                {
                    if(s.equals(posList.get(i).getName()))
                    {
                        if(posList.get(i).getPicked() == 1){
                            if(!selectedPositions.contains(posList.get(i).getName()))
                                selectedPositions.add(posList.get(i).getName());
                            loaded = Boolean.TRUE;
                            view.setBackgroundColor(Color.GREEN);
                        }
                    }
                }
                return view;
            }
        };
        selectionList = (ListView) findViewById(R.id.posListView);
        selectionList.setBackgroundColor(Color.LTGRAY);
        selectionList.setAlpha((float)0.5);
        //Checks if cards already exist and if they have a rating or priority, if they do, the second sort, output and review sort button is made visible.
        try{
            ArrayList<Card> cardList = db.getCards();
            for(int i = 0; i < cardList.size(); i++)
            {
                if(cardList.get(i).getRating()!=0) {
                    reviewButton.setEnabled(true);
                    reviewButton.setVisibility(View.VISIBLE);
                }
                if(cardList.get(i).getPriority()!=0) {
                    secondButton.setEnabled(true);
                    secondButton.setVisibility(View.VISIBLE);
                    outputButton.setEnabled(true);
                    outputButton.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < posList.size(); i++) {
            adapter.add(posList.get(i).getName());
        }
        oldPositions = selectedPositions;
        selectionList.setAdapter(adapter);
        //Setting up the on click listener to select or deselect the item and change its colour.
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                String text = "";
                if (!selectedPositions.contains(item)) {
                    selectedPositions.add(item);
                    for(int i = 0; i<posList.size(); i++)
                    {
                        if(posList.get(i).getName().equals(item)){
                            posList.get(i).setPicked(1);
                            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                            db.updatePosition(posList.get(i));
                        }
                    }
                }
                else if (selectedPositions.contains(item)) {
                    selectedPositions.remove(item);
                    for(int i = 0; i<posList.size(); i++)
                    {
                        if(posList.get(i).getName().equals(item)){
                            posList.get(i).setPicked(0);
                            parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                            db.updatePosition(posList.get(i));
                        }
                    }

                }
            }

         });
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        Boolean isOverDue = Boolean.FALSE;

        //Also displays the date for the next sort, will instead display the default message for the activity.
        try {
            if (a.getDateOf() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date d = sdf.parse(a.getDateOf());
                cal.setTime(d);
                if(c.get(Calendar.YEAR) >= cal.get(Calendar.YEAR)) {
                    if (c.get(Calendar.MONTH) > cal.get(Calendar.MONTH))
                        isOverDue = Boolean.TRUE;
                    if(c.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) >= cal.get(Calendar.DAY_OF_MONTH))
                        isOverDue = Boolean.TRUE;

                }
                if(isOverDue){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You're due for another sort! Please finish a new sort asap.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    menuToolbar.setSubtitle("Sort date: " + a.getDateOf());
                }
                else
                    menuToolbar.setSubtitle("Next Sort: " + a.getDateOf());
                }   else
                menuToolbar.setSubtitle("Select cards to sort");

        } catch(Exception e) {
            menuToolbar.setSubtitle("Select cards to sort");
        }
        setSupportActionBar(menuToolbar);
        Menu m = menuToolbar.getMenu();
        }

    /** goMain
     * When the user has selected their positions they press on the button that sends them to the SelectCategoryActivity class.
     * It then gets all the positions selected and what used to be selected, and the database to deselect it if it is no longer selected.
     * It also gets all the newly selected positions and selects it.
     * This allows the user to remove and add positions at a whim and the app will accommodate for that.
     * @param view - Button
     */
    public void goMain(View view)
        {
            String text = "";
            for (int i = 0; i < selectedPositions.size(); i++)
                text += selectedPositions.get(i) + "\n";
            new AlertDialog.Builder(this)
                    .setTitle("Positions Selected")
                    .setMessage("Your current positions selected are:\n" + text + "\nWould you like to continue?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialog, int button) {
              ArrayList<String> newPositions = new ArrayList<>();
              ArrayList<Position> positionList = new ArrayList<>();
              positionList = db.getPositions();
              for(int i = 0; i<positionList.size(); i++) {
                  if (positionList.get(i).getPicked() == 1) {
                      if (!oldPositions.contains(positionList.get(i).getName()))
                          newPositions.add(positionList.get(i).getName());
                  }
              }
              for(int i = 0; i<positionList.size(); i++) {
                  if (positionList.get(i).getPicked() == 0) {
                      if (oldPositions.contains(positionList.get(i).getName()))
                          deletedPositions.add(positionList.get(i).getName());
                  }
              }
              //Will be false if positions have not yet been selected once before, other wise it will always be loaded.
              if(!loaded) {
                  db.deleteSkills();
                  mc.createSkills(getBaseContext());
                  db.updatePositions(selectedPositions);
                  db.createCards();
              }
              else
                  db.updatePositions(selectedPositions);

              Intent intent = new Intent(getBaseContext(),SelectCategoryActivity.class);
              startActivity(intent);
              finish();
          }
        }).setNegativeButton(android.R.string.no, null).show();

        }
    /** onOptionsItemSelected
     * Checks what item is pressed and acts on it
     * @param item (MenuItem) - An item in the top toolbar is pressed, this is what item it is.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Tap on the positions you play, then press the circle select button to sort for those positions.\n\n Otherwise you may tap on any of the other icons available to go to the other screens.")
                    .setCancelable(false)
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
        return super.onOptionsItemSelected(item);
    }
    /** onBackPressed
     * When back is pressed, goes to the SelectCategoryActivity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TitleScreenActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Goes to the corresponding activity for that button.
     * @param v (View) - button
     */
    public void toReview(View v){
        Intent intent = new Intent(getBaseContext(), ReviewSortActivity.class);
        startActivity(intent);
        finish();
    }
    public void toSecond(View v){
        Intent intent = new Intent(getBaseContext(), SecondSortActivity.class);
        startActivity(intent);
        finish();
    }
    public void toOutput(View v){
        Intent intent = new Intent(getBaseContext(), OutputActivity.class);
        startActivity(intent);
        finish();
    }
}
