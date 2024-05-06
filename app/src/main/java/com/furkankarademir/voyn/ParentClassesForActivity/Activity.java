package com.furkankarademir.voyn.ParentClassesForActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public abstract class Activity implements Serializable {
    private String  date;

    private String time;

    private String name;
    private String surname;
    private String mail;
    private String creatorUserID;
    private String extraNote;

    private ArrayList<String> participantsId;

    private ArrayList<String> invitedId;

    public Activity(String name,String surname, String mail,String date,String time,String extraNote, String creatorUserID) {
        this.date = date;
        this.time = time;
        this.extraNote = extraNote;
        this.name = name;
        this.mail = mail;
        this.surname = surname;
        this.participantsId = new ArrayList<>();
        this.invitedId = new ArrayList<>();
        this.creatorUserID = creatorUserID;
    }

    public void setCreatorUserID(String creatorUserID) {
        this.creatorUserID = creatorUserID;
    }

    public abstract void addActivityToDatabase(FireStoreCallback callback);

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
    public String getCreatorUserID() {
        return creatorUserID;
    }


    public void addParticipant(String participantId)
    {
        participantsId.add(participantId);
    }

    public void addInvited(String invitedId)
    {
        this.invitedId.add(invitedId);
    }

    public ArrayList<String> getParticipantsId() {
        return participantsId;
    }

    public ArrayList<String> getInvitedId() {
        return invitedId;
    }

    public void setParticipantsId(ArrayList<String> participantsId) {
        this.participantsId = participantsId;
    }

    public void setInvitedId(ArrayList<String> invitedId) {
        this.invitedId = invitedId;
    }
}
