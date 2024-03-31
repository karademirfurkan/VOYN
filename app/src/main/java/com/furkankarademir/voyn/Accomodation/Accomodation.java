package com.furkankarademir.voyn.Accomodation;

import com.furkankarademir.voyn.ParentClassesForActivity.Activity;

public class Accomodation extends Activity
{
    private String type;
    private String place;
    private String gender;
    private int numberOfInhabitants;

    public Accomodation(String name, String surname, String mail, String date, String time,
                        String extraNote, String creatorUserID, String type, String place,
                        String gender, int numberOfInhabitants)
    {
        super(name,surname, mail, date, time, extraNote, creatorUserID);
        this.type = type;
        this.place = place;
        this.gender = gender;
        this.numberOfInhabitants = numberOfInhabitants;
    }
    
    @Override
    public void addActivityToDatabase() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumberOfInhabitants() {
        return numberOfInhabitants;
    }

    public void setNumberOfInhabitants(int numberOfInhabitants) {
        this.numberOfInhabitants = numberOfInhabitants;
    }
}
