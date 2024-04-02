package com.furkankarademir.voyn.Transportation;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transportation extends Activity{
    private String departure;
    private String destination;

    private int seats;


    public Transportation(String name, String surname, String mail, String date, String time,String departure, String destination,
                          int seats, String extraNote, String creatorUserID)
    {
        super(name,surname, mail, date, time, extraNote, creatorUserID);
        this.departure = departure;
        this.destination = destination;
        this.seats = seats;
    }



    public String addActivityToDatabase() {
        // Add transportation to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String[] thisActivityID = new String[1];
        Map<String, Object> transportation = new HashMap<>();
        transportation.put("date", this.getDate());
        transportation.put("name", this.getName());

        System.out.println(transportation.get("name"));

        transportation.put("surname", getSurname());
        transportation.put("mail", getMail());
        transportation.put("creatorUserID", getCreatorUserID());
        transportation.put("time", getTime());
        transportation.put("departure", departure);
        transportation.put("destination", destination);
        transportation.put("seats", seats);
        transportation.put("extraNote", getExtraNote());
        transportation.put("participantsId", getParticipantsId());
        transportation.put("invitedId", getInvitedId());


        db.collection("transportations")
                .add(transportation)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = task.getResult();
                            System.out.println("her şey ejkendi");
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            thisActivityID[0] = documentReference.getId();
                            System.out.println("bu da id" + thisActivityID[0]);
                        } else {
                            Exception e = task.getException();
                            System.out.println("olmadı beee");
                            Log.w(TAG, "Error adding document", e);
                        }
                    }
                });
        System.out.println("bu da id 2.deneme" + thisActivityID[0]);
        return thisActivityID[0];
    }


    //getter and setters
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
