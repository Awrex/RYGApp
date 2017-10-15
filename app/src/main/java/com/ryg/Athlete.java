package com.ryg;

import java.util.Calendar;

/**
 * Created by Alex on 15/08/2017.
 */

public class Athlete {
    private String name;
    private String email;
    private String dob;
    private String gender;
    private int skillLevel;
    private String teams;
    private String location;
    private String zipCode;
    private boolean terms1;
    private boolean terms2;
    private boolean terms3;
    private String dateOf;
    private int weeks;

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public Athlete(String name, String email, String dob, String gender, int skillLevel, String teams, String loc, String zip, int terms1, int terms2, int terms3, String dateOf, int weeks) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.skillLevel = skillLevel;
        this.teams = teams;
        this.location = loc;
        this.zipCode = zip;
        if (terms1 == 1)
            this.terms1 = Boolean.TRUE;
        else
            this.terms1 = Boolean.FALSE;
        if (terms2 == 1)
            this.terms2 = Boolean.TRUE;
        else
            this.terms2 = Boolean.FALSE;
        if (terms3 == 1)
            this.terms3 = Boolean.TRUE;
        else
            this.terms3 = Boolean.FALSE;
        this.dateOf = dateOf;
        this.weeks = weeks;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int isTerms1() {
        if(terms1)
        return 1;
        else
            return 0;
    }
    public int isTerms2() {
        if(terms2)
            return 1;
        else
            return 0;
    }
    public int isTerms3() {
        if(terms3)
            return 1;
        else
            return 0;
    }
    public Boolean getTerms1(){
        return terms1;
    }
    public Boolean getTerms2(){
        return terms2;
    }
    public Boolean getTerms3(){
        return terms3;
    }

    public void setTerms1(Boolean terms) {
        terms1 = terms;
    }
    public void setTerms2(Boolean terms) {
        terms2 = terms;
    }
    public void setTerms3(Boolean terms) {
        terms3 = terms;
    }

    public Athlete(){}
    public String getEmail() {
        return email;
    }
    public int getAge(Calendar dob) {
        int age = 0;
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        age = year - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if(month2 > month1){
            age--;
        } else if(month1 == month2){
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
