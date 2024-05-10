package com.furkankarademir.voyn.Accomodation;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.ParentClassesForActivity.FireStoreCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Accomodation extends Activity
{
    private String type;
    private String place;
    private String gender;
    private int numberOfInhabitants;

    private double minStar;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public Accomodation(String name, String surname, String mail, String date, String time,
                        String extraNote, String creatorUserID, String type, String place,
                        String gender, int numberOfInhabitants, double minStar)
    {
        super(name,surname, mail, date, time, extraNote, creatorUserID);
        this.type = type;
        this.place = place;
        this.gender = gender;
        this.numberOfInhabitants = numberOfInhabitants;
        this.minStar = minStar;
    }
    
    @Override
    public void addActivityToDatabase(FireStoreCallback callback)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> accomodation = new HashMap<>();
        accomodation.put("name", getName());
        accomodation.put("surname", getSurname());
        accomodation.put("mail", getMail());
        accomodation.put("date", getDate());
        accomodation.put("time", getTime());
        accomodation.put("extraNote", getExtraNote());
        accomodation.put("creatorUserID", auth.getCurrentUser().getUid());
        accomodation.put("participantsId", getParticipantsId());
        accomodation.put("invitedId", getInvitedId());

        accomodation.put("type", type);
        accomodation.put("place", place);
        accomodation.put("gender", gender);
        accomodation.put("numberOfInhabitants", numberOfInhabitants);
        accomodation.put("minStar", minStar);


        db.collection("accommodations")
                .add(accomodation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String documentId = documentReference.getId();
                        accomodation.put("documentId", documentId);
                        db.collection("accommodations").document(documentId).set(accomodation);
                        System.out.println("her sey eklendi");
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        callback.onCallback(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
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

    public double getMinStar() {
        return minStar;
    }

    public void setMinStar(double minStar) {
        this.minStar = minStar;
    }
}
