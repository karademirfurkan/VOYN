package com.furkankarademir.voyn.ParentClassesForActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.ProfileClasses.Profile;

import java.sql.Time;
import java.util.Date;

public class Activity  {
    private Date date;
    private Profile profile;

    private Time time;

    public Activity(Date date, Profile profile, Time time) {
        this.date = date;
        this.profile = profile;
        this.time = time;
    }

}
