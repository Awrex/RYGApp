package com.ryg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 15/09/2017.
 */

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "CreateUser";
    private static final int RESULT_LOAD_IMAGE = 1;
    Button imageToUpload, inputButton, nextButton, cancelButton;
    ImageView profileImage, dateButton, downArrow;
    LinearLayout userLayout;
    TextView dateOfBirth, imageText, termsTitle;
    Boolean terms = false;
    CheckBox perm1, perm2, perm3;
    ListView teamList;
    ScrollView termsScroll;
    EditText name,teams,email, zipCode;
    TextView permText1, permText2, permText3;
    Boolean firstTime = Boolean.FALSE;
    Spinner spin, countrySpin;
    Bitmap bmp = null;
    Spinner gender;
    private ArrayAdapter<String> listAdapter;
    ArrayList<String> teamNames;
    int skillLevel = 1;
    String s = "Emerging Talent/New Team Member";
    private Athlete athlete;
    private Calendar DOB;
    private DBController db;
    private int year,month,day;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private File f;
    FileOutputStream out = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamNames = new ArrayList<>();
        setContentView(R.layout.activity_createuser);
        athlete = new Athlete();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        DOB = Calendar.getInstance();
        userLayout = (LinearLayout)findViewById(R.id.userCreationLayout);
        zipCode = (EditText)findViewById(R.id.zipEdit);
        zipCode.setOnFocusChangeListener(this);
        countrySpin = (Spinner)findViewById(R.id.countrySpinner);
        downArrow = (ImageView)findViewById(R.id.downArrow);
        termsTitle = (TextView)findViewById(R.id.termsTitle);
        permText1 = (TextView)findViewById(R.id.perm1Text);
        permText2 = (TextView)findViewById(R.id.perm1Text);
        permText3 = (TextView)findViewById(R.id.perm1Text);
        termsScroll = (ScrollView)findViewById(R.id.termsScroll);
        ArrayAdapter<CharSequence>  countryAdapter = ArrayAdapter.createFromResource(this,R.array.countries, android.R.layout.simple_spinner_item);
        countrySpin.setAdapter(countryAdapter);
        imageToUpload = (Button) findViewById(R.id.addImageButton);
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            String dir = directory.getAbsolutePath();
            File f = new File(dir, "Profile.png");
            bmp = BitmapFactory.decodeStream(new FileInputStream(f));
            imageToUpload.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        perm1 = (CheckBox) findViewById(R.id.perm1);
        perm2 = (CheckBox) findViewById(R.id.perm2);
        perm3 = (CheckBox) findViewById(R.id.perm3);
        dateButton = (ImageView) findViewById(R.id.dateButton);
        profileImage = (ImageView) findViewById(R.id.profilePicture);
        nextButton = (Button) findViewById(R.id.nextButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        dateOfBirth = (TextView) findViewById(R.id.dob);
        dateOfBirth.setFocusable(Boolean.FALSE);
        inputButton = (Button) findViewById(R.id.inputTeam);
        teamList = (ListView) findViewById(R.id.teamList);
        name = (EditText) findViewById(R.id.fName);
        name.setOnFocusChangeListener(this);
        email = (EditText) findViewById(R.id.email);
        email.setOnFocusChangeListener(this);
        spin = (Spinner) findViewById(R.id.levelSpinner);
        teams = (EditText) findViewById(R.id.teams);
        teams.setOnFocusChangeListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getItemAtPosition(position).toString();
                if (s.equals("Emerging Talent / New Team Member"))
                    skillLevel = 1;
                else if(s.equals("Team Member / Gaining Experience"))
                        skillLevel = 2;
                else if(s.equals("Senior Player / Leader"))
                        skillLevel = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                skillLevel = 1;
            }

        });
        spin.setFocusable(Boolean.FALSE);
        imageText = (TextView) findViewById(R.id.profileText);
        if (bmp != null)
        {
            imageText.setText("");
        }
        gender = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence>  genderAdapter = ArrayAdapter.createFromResource(this,R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);
        gender.setFocusable(Boolean.FALSE);
        imageToUpload.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        teamList.setAdapter(listAdapter);
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = listAdapter.getItem(position);

                listAdapter.remove(selected);
                listAdapter.notifyDataSetChanged();
                for(int i = 0; i < teamNames.size();i++){
                    if(teamNames.get(i).equals(selected))
                        teamNames.remove(i);
                }

            }
        });
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.add(teams.getText().toString());
                teamNames.add(teams.getText().toString());
                teams.setText("");
                listAdapter.notifyDataSetChanged();
            }
        });
        db = new DBController(this);
        f = new File(this.getFilesDir(), "Profile.png");
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                year = y;
                month = m;
                day = d;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);
                String date = day + "/" + (month+1) + "/" + year;
                dateOfBirth.setText(date);
                athlete.setDob(date);
                DOB.set(y,m,d);
            }
        };
        if (imageToUpload.getBackground() != null)
        {
            try{
                athlete = db.getAthlete();
                name.setText(athlete.getName());
                email.setText(athlete.getEmail());
                zipCode.setText(athlete.getZipCode());
                spin.setSelection(athlete.getSkillLevel()-1);
                if(athlete.getGender().equals("Male")) {
                    gender.setSelection(0);
                }
                else
                    gender.setSelection(1);
                dateOfBirth.setText(athlete.getDob());
                char[] n = athlete.getTeams().toCharArray();
                String newName = "";
                newName += n[0];
                if(newName.length() != 0) {
                    for (int j = 1; j < n.length; j++) {
                        if (n[j] == ',') {
                            listAdapter.add(newName);
                            teamNames.add(newName);
                            newName = "";
                        }
                        else
                            newName += n[j];
                    }
                    listAdapter.notifyDataSetChanged();
                }
                for(int i = 0; i < countrySpin.getCount(); i++)
                {
                    if(countrySpin.getItemAtPosition(i).toString().equals(athlete.getLocation()))
                    {
                        countrySpin.setSelection(i);
                    }
                }
                if(athlete.getTerms1())
                    perm1.setChecked(true);
                if(athlete.getTerms2())
                    perm2.setChecked(true);
                if(athlete.getTerms3())
                    perm3.setChecked(true);
            }
            catch(RuntimeException e)
            {
                firstTime = Boolean.TRUE;
            }

        }
        permText1.setOnClickListener(this);
        permText2.setOnClickListener(this);
        permText3.setOnClickListener(this);
        downArrow.setOnClickListener(this);
        termsTitle.setOnClickListener(this);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                saveFile(bmp);
                finish();
                startActivity(getIntent());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        setResult(RESULT_CANCELED, null);
    }

    public void saveFile(Bitmap bmp)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(directory,"Profile.png");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally{
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!v.hasFocus())
            switch (v.getId()){
                case R.id.zipEdit:
                    hideKeyboard(v);
                    break;
                case R.id.fName:
                    hideKeyboard(v);
                    break;
                case R.id.email:
                    hideKeyboard(v);
                    break;
                case R.id.teams:
                    hideKeyboard(v);
                    break;
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.userCreationLayout:
                hideKeyboard(v);
                break;
            case R.id.termsTitle:
                if(terms)
                {
                    terms = false;
                    downArrow.setImageDrawable(getResources().getDrawable(R.drawable.downarrow));
                    termsScroll.setVisibility(View.GONE);
                }
                else {
                    terms = true;
                    downArrow.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                    termsScroll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.downArrow:
                if(terms)
                {
                    terms = false;
                    downArrow.setImageDrawable(getResources().getDrawable(R.drawable.downarrow));
                    termsScroll.setVisibility(View.GONE);
                }
                else {
                    terms = true;
                    downArrow.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                    termsScroll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.perm1Text:
                if(!perm1.isChecked())
                    perm1.setChecked(true);
                else
                    perm1.setChecked(false);
                break;
            case R.id.perm2Text:
                if(!perm2.isChecked())
                    perm2.setChecked(true);
                else
                    perm2.setChecked(false);
                break;
            case R.id.perm3Text:
                if(!perm3.isChecked())
                    perm3.setChecked(true);
                else
                    perm3.setChecked(false);
                break;
            case R.id.addImageButton:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                imageText.setText("");
                break;
            case R.id.nextButton:
                Boolean checked = true;
                String missed = "";
                if(!perm3.isChecked()) {
                    checked = false;
                    missed += "Terms and conditions have not been ticked.\n";
                }
                if(name.getText().toString().equals("Name:") || name.getText().toString().equals("")) {
                    checked = false;
                    missed += "You have not entered a name.\n";
                }
                if(dateOfBirth.getText().toString().equals("Date Of Birth:") || dateOfBirth.getText().toString().equals("")) {
                    checked = false;
                    missed += "You have not entered a DOB.\n";
                }
                if(email.getText().toString().equals("Email:") || email.getText().toString().equals(""))
                {
                    checked = false;
                    missed += "You have not entered an Email.\n";
                }
                if(checked) {
                    athlete.setName(name.getText().toString());
                    athlete.setEmail(email.getText().toString());
                    String gen;
                    if (gender.getSelectedItem().toString().equals("Male")) {
                        athlete.setGender("Male");
                        gen = "Male";
                    } else {
                        athlete.setGender("Female");
                        gen = "Female";
                    }
                    if(perm1.isChecked())
                        athlete.setTerms1(Boolean.TRUE);
                    else
                        athlete.setTerms1(Boolean.FALSE);
                    if(perm2.isChecked())
                        athlete.setTerms2(Boolean.TRUE);
                    else
                        athlete.setTerms2(Boolean.FALSE);
                    if(perm3.isChecked())
                        athlete.setTerms3(Boolean.TRUE);
                    else
                        athlete.setTerms3(Boolean.FALSE);
                    athlete.setSkillLevel(skillLevel);
                    String s = "";
                    for (int i = 0; i < teamNames.size(); i++) {
                        if (i == teamNames.size())
                            s += teamNames.get(i);
                        else
                            s += teamNames.get(i) + ",";
                    }
                    athlete.setTeams(s);
                    athlete.setLocation(countrySpin.getSelectedItem().toString());
                    athlete.setZipCode(zipCode.getText().toString());
                    db.addAthlete(athlete);
                    saveFile(bmp);
                    setResult(RESULT_OK, null);
                    if(firstTime)
                    {
                        Intent intent = new Intent(this,SelectPositionsActivity.class);
                        MainController mc = new MainController(this);
                        mc.createPositions(this);
                        startActivity(intent);
                    }
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You have not filled in everything. Please read and check the following:\n" + missed)
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                break;
            case R.id.cancelButton:
                finish();
                setResult(RESULT_CANCELED, null);
                break;
            case R.id.dateButton:
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if(!dateOfBirth.getText().toString().equals("DOB")) {
                    try {
                        Date d = sdf.parse(dateOfBirth.getText().toString());
                        cal.setTime(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(CreateUserActivity.this,android.R.style.Theme_Holo_Dialog,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
                break;
        }
    }
}
