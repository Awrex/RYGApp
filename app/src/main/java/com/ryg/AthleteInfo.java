package com.ryg;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Francois Mindiel
 * Modified by Alex Stewart
 *
 * Somewhat of a data class, is mostly used in conjunction with an Athlete.
 * Is used to create the Httpconnection with google forms in order to send data to the form
 * Where it will be used for analytical purposes
 */

public class AthleteInfo extends Athlete{

    protected String email;
    protected int age;
    protected String gender;
    protected String skillLevel;
    protected String teamName;
    protected String country;
    protected String zipCode;
    protected ArrayList<String> roleList;
    protected ArrayList<Card> cards;
    protected ArrayList<String> strengthSkills, mediumSkills, workOnSkills;
    ArrayList<String> highSkills, middleSkills, lowSkills;
    String roles = "";
    String strengths = "";
    String mediums = "";
    String workOns = "";
    String highs = "", middles = "", lows = "";


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .build();
    final HttpConnection httpConnection = retrofit.create(HttpConnection.class);

    public AthleteInfo(String email, Calendar dob, String gender, String skillLevel, String teamName, ArrayList<String> roles, ArrayList<String> strengthSkills, ArrayList<String> mediumSkills, ArrayList<String> workOnSkills, ArrayList<String> highSkills, ArrayList<String> middleSkills, ArrayList<String> lowSkills, String country, String zip) {
        this.email = email;
        this.age = getAge(dob);
        this.gender = gender;
        this.skillLevel = skillLevel;
        this.teamName = teamName;
        this.roleList = roles;
        this.strengthSkills = strengthSkills;
        this.mediumSkills = mediumSkills;
        this.workOnSkills = workOnSkills;
        this.highSkills = highSkills;
        this.middleSkills = middleSkills;
        this.lowSkills = lowSkills;
        this.country = country;
        this.zipCode = zip;
    }

    /** createInfo
     * Turns the arrayLists of strings into a single string, which is displayed in the spreadsheet
     * Then sends all the info to the HttpConnection class where that is sent to the form.
     */
    public void createInfo() {
        for(int i = 0; i < strengthSkills.size(); i++)
        {
            if(i+1 != strengthSkills.size())
                strengths += strengthSkills.get(i) + ", ";
            else
                strengths += strengthSkills.get(i);
        }

        for(int i = 0; i < roleList.size(); i++)
        {
            if(i+1 != roleList.size())
                roles += roleList.get(i) + ", ";
            else
                roles += roleList.get(i);
        }

        for(int i = 0; i < mediumSkills.size(); i++)
        {
            if(i+1 != mediumSkills.size())
                mediums += mediumSkills.get(i) + ", ";
            else
                mediums += mediumSkills.get(i);
        }

        for(int i = 0; i < workOnSkills.size(); i++)
        {
            if(i+1 != workOnSkills.size())
                workOns += workOnSkills.get(i) + ", ";
            else
                workOns += workOnSkills.get(i);
        }
        for(int i = 0; i < highSkills.size(); i++)
        {
            if(i+1 != highSkills.size())
                highs += highSkills.get(i) + ", ";
            else
                highs += highSkills.get(i);
        }
        for(int i = 0; i < middleSkills.size(); i++)
        {
            if(i+1 != middleSkills.size())
                middles += middleSkills.get(i) + ", ";
            else
                middles += middleSkills.get(i);
        }
        for(int i = 0; i < lowSkills.size(); i++)
        {
            if(i+1 != lowSkills.size())
                lows += lowSkills.get(i) + ", ";
            else
                lows += lowSkills.get(i);
        }


        Call<Void> completeUserInfo = httpConnection.userInfo(email, age, gender, skillLevel, country, zipCode, teamName, roles, strengths, mediums, workOns, highs, middles, lows);
        completeUserInfo.enqueue(callCallback);
    }


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
