package com.example.alex.raiseyourgameapp;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

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

public class CreateUser extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateUser";
    private static final int RESULT_LOAD_IMAGE = 1;
    Button imageToUpload, dateButton, inputButton, nextButton, cancelButton;
    ImageView profileImage;
    TextView dateOfBirth, imageText;
    CheckBox perm1, perm2, perm3;
    ListView teamList;
    EditText name,teams,email, zipCode;
    Boolean firstTime = Boolean.FALSE;
    Spinner spin, countrySpin;
    Bitmap bmp = null;
    ToggleButton gender;
    private ArrayAdapter<String> listAdapter, countryAdapter;
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
        zipCode = (EditText)findViewById(R.id.zipEdit);
        countrySpin = (Spinner)findViewById(R.id.countrySpinner);
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
        dateButton = (Button) findViewById(R.id.dateButton);
        profileImage = (ImageView) findViewById(R.id.profilePicture);
        nextButton = (Button) findViewById(R.id.nextButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        dateOfBirth = (TextView) findViewById(R.id.dob);
        inputButton = (Button) findViewById(R.id.inputTeam);
        teamList = (ListView) findViewById(R.id.teamList);
        name = (EditText) findViewById(R.id.fName);
        email = (EditText) findViewById(R.id.email);
        spin = (Spinner) findViewById(R.id.levelSpinner);
        teams = (EditText) findViewById(R.id.teams);

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

        imageText = (TextView) findViewById(R.id.profileText);
        if (bmp != null)
        {
            imageText.setText("");
        }
        gender = (ToggleButton) findViewById(R.id.genderSelect);
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
                    gender.setChecked(false);
                }
                else
                    gender.setChecked(true);
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
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
                if(name.getText().toString().equals("Insert Name Here") || name.getText().toString().equals("")) {
                    checked = false;
                    missed += "You have not entered a name.\n";
                }
                if(dateOfBirth.getText().toString().equals("Date Of Birth:") || dateOfBirth.getText().toString().equals("")) {
                    checked = false;
                    missed += "You have not entered a DOB.\n";
                }
                if(email.getText().toString().equals("Insert Email Here") || email.getText().toString().equals(""))
                {
                    checked = false;
                    missed += "You have not entered an Email.\n";
                }
                    if(checked) {
                        athlete.setName(name.getText().toString());
                        athlete.setEmail(email.getText().toString());
                        String gen;
                        if (gender.getText().toString().equals("Male")) {
                            athlete.setGender("Male");
                            gen = "Male";
                        } else {
                            athlete.setGender("Female");
                            gen = "Female";
                        }
                        if(perm1.isChecked())
                            athlete.setTerms1(Boolean.TRUE);
                        if(perm2.isChecked())
                            athlete.setTerms2(Boolean.TRUE);
                        if(perm3.isChecked())
                            athlete.setTerms3(Boolean.TRUE);
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
                            Intent intent = new Intent(this,PositionActivity.class);
                            MainController mc = new MainController(this);
                            mc.createPositions();
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
                DatePickerDialog dialog = new DatePickerDialog(CreateUser.this,android.R.style.Theme_Holo_Dialog,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
                break;
        }
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }  finally{
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
