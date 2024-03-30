package com.furkankarademir.voyn.ParentClassesForActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public abstract class Activity implements Serializable {
    private String  date;

    private String time;

    private String name;
    private String surname;
    private String mail;
    private int creatorUserID;
    private String extraNote;

    public Activity(String name,String surname, String mail,String date,String time,String extraNote, String creatorUserID) {
        this.date = date;
        this.time = time;
        this.extraNote = extraNote;
        this.name = name;
        this.mail = mail;
        this.surname = surname;
    }

    public abstract void addActivityToDatabase();

    public String getExtraNote() {
        return extraNote;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getMail() {
        return mail;
    }
    public int getCreatorUserID() {
        return creatorUserID;
    }

}
