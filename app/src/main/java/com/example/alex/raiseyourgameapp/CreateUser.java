package com.example.alex.raiseyourgameapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static com.example.alex.raiseyourgameapp.R.id.dob;

/**
 * Created by Alex on 15/09/2017.
 */

public class CreateUser extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateUser";
    private static final int RESULT_LOAD_IMAGE = 1;
    Button imageToUpload, dateButton;
    ImageView profileImage, nextButton;
    TextView dateOfBirth, imageText;
    EditText fName,lName,email;
    Spinner spin;
    ToggleButton gender;
    int skillLevel;
    private Athlete athlete;
    private Calendar DOB;
    private DBController db;
    private int year,month,day;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String fileName = "userImage.png";
    private File f;
    FileOutputStream out = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        athlete = new Athlete();
         DOB = Calendar.getInstance();
        imageToUpload = (Button) findViewById(R.id.addImageButton);
        dateButton = (Button) findViewById(R.id.dateButton);
        profileImage = (ImageView) findViewById(R.id.profilePicture);
        nextButton = (ImageView) findViewById(R.id.nextButton);
        dateOfBirth = (TextView) findViewById(dob);
        fName = (EditText) findViewById(R.id.fName);
        lName = (EditText) findViewById(R.id.lName);
        email = (EditText) findViewById(R.id.email);
        spin = (Spinner) findViewById(R.id.levelSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                if (s.equals("Emerging talent/New Team member"))
                {
                    skillLevel = 1;
                    if(s.equals("Team member/Gaining experience")){
                        skillLevel = 2;
                    }
                    else
                        skillLevel = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                skillLevel = 1;
            }

        });

        imageText = (TextView) findViewById(R.id.profileText);
        gender = (ToggleButton) findViewById(R.id.genderSelect);
        imageToUpload.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        db = new DBController(this);
        f = new File(this.getFilesDir(), fileName);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                year = y;
                month = m;
                day = d;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);
                String date = day + "/" + (month+1) + "/" + year;
                dateOfBirth.setText(date);
                DOB.set(y,m,d);
            }
        };
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
                athlete.setFirstName(fName.getText().toString());
                athlete.setLastName(lName.getText().toString());
                athlete.setEmail(email.getText().toString());
                if(gender.getText().toString().equals("I'm a Male"))
                    athlete.setGender("Male");
                else
                    athlete.setGender("Female");
                athlete.setDob(DOB);
                db.addAthlete(athlete);
                Intent intent = new Intent(this, PositionActivity.class);
                intent.putExtra("DIR",saveFile(profileImage));
                intent.putExtra("LEVEL",skillLevel);
                startActivity(intent);
                finish();
                break;
            case R.id.dateButton:
                Calendar cal = Calendar.getInstance();;
                if(dateOfBirth.getText().toString().equals("DOB")) {
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                }
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
            profileImage.setImageURI(selectedImage);
        }
    }
    public String saveFile(ImageView image)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(directory,"Profile.jpg");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path);
            Bitmap bmp = ((BitmapDrawable)profileImage.getDrawable()).getBitmap();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
