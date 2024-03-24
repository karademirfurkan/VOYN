package com.furkankarademir.voyn.ParentClassesForActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.ProfileClasses.Profile;

import java.sql.Time;
import java.util.Date;

public class Activity  {
    private Date date;
    private Profile profile;

    private Time time;

    private String extraNote;

    public Activity(Date date, Profile profile, Time time, String extraNote) {
        this.date = date;
        this.profile = profile;
        this.time = time;
        this.extraNote = extraNote;
    }

}
