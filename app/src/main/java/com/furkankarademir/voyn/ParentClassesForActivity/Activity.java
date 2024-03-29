package com.furkankarademir.voyn.ParentClassesForActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.ProfileClasses.Profile;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public abstract class Activity implements Serializable {
    private Date date;
    private Profile profile;

    private Time time;

    private String extraNote;

    public Activity(String extraNote) {
        this.extraNote = extraNote;
    }

    public abstract void addActivityToDatabase();

    public String getExtraNote() {
        return extraNote;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Profile getProfile() {
        return profile;
    }

}
