package com.furkankarademir.voyn.Transportation;

import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.ProfileClasses.Profile;

import java.sql.Time;
import java.util.Date;

public class Transportation extends Activity {

    private String departure;
    private String destination;
    private int seats;


    public Transportation(Date date, Profile profile, Time time, String departure, String destination, int seats, String extraNote) {
        super(date, profile, time, extraNote);
        this.departure = departure;
        this.destination = destination;
        this.seats = seats;
    }
}
