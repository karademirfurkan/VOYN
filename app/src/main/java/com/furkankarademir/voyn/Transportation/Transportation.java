package com.furkankarademir.voyn.Transportation;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transportation extends Activity {
    private String departure;
    private String destination;


    private int seats;


    public Transportation(String extraNote) {
        super(extraNote);
    }

    public void addActivityToDatabase() {
        // Add transportation to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> transportation = new HashMap<>();
        transportation.put("date", getDate());
        transportation.put("profile", getProfile());
        transportation.put("time", getTime());
        transportation.put("departure", departure);
        transportation.put("destination", destination);
        transportation.put("seats", seats);
        transportation.put("extraNote", getExtraNote());

        db.collection("transportations")
                .add(transportation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("noldu");
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("nolmadı");
                        Log.w(TAG, "Error adding document", e);
                    }
                });
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
