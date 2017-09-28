package com.example.alex.raiseyourgameapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by fgmind on 28/09/2017.
 */

public class AthleteInfo extends Athlete{

    protected String email;
    protected int age;
    protected String gender;
    protected String skillLevel;
    protected String teamName;
    protected String roles[];
    protected String strengthSkills[];
    protected String mediumSkills[];
    protected String workOnSkills[];


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/")
            .build();
    final HttpConnection httpConnection = retrofit.create(HttpConnection.class);

    email = ;
    age = ;
    gender = ;
    skillLevel = ;
    teamName = ;
    roles = ;
    strengthSkills = ;
    mediumSkills = ;
    workOnSkills = ;




    Call<Void> completeUserInfo = HttpConnection.userInfo(getEmail(), age, getGender(), skillLevel, teamName, roles, strengthSkills, mediumSkills, workOnSkills);
                completeUserInfo.enqueue(callCallback);


    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }

//        @Override
//        public void onResponse(Response<Void> response) {
//            Log.d("XXX", "Submitted. " + response);
//        }
//
//        @Override
//        public void onFailure(Throwable t) {
//            Log.e("XXX", "Failed", t);
//        }
    };

}
