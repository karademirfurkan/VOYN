package com.furkankarademir.voyn.Sport;

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

public class Sport extends Activity {

    private String place;
    private String type;


    private int numberOfPlayers;

    private double minStar;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
     public Sport(String name, String surname, String mail, String date, String time, String place,
                  int numberOfPlayers, String extraNote, String creatorUserID, String type, double minStar){
         super(name,surname, mail, date, time, extraNote, creatorUserID);
         this.place = place;
         this.numberOfPlayers = numberOfPlayers;
         this.type = type;
         this.minStar = minStar;
     }
    public void addActivityToDatabase(FireStoreCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> sport = new HashMap<>();
        sport.put("date", this.getDate());
        sport.put("name", this.getName());

        System.out.println(sport.get("name"));

        sport.put("type",getType());
        sport.put("surname", getSurname());
        sport.put("mail", getMail());
        sport.put("creatorUserID", auth.getCurrentUser().getUid());
        sport.put("time", getTime());
        sport.put("place", getPlace());
        sport.put("numberOfPlayers", getNumberOfPlayers());
        sport.put("extraNote", getExtraNote());
        sport.put("participantsId", getParticipantsId());
        sport.put("invitedId", getInvitedId());
        sport.put("minStar", minStar);


        db.collection("sports")
                .add(sport)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String documentId = documentReference.getId();
                        sport.put("documentId", documentId);
                        db.collection("sports").document(documentId).set(sport);
                        System.out.println("her sey eklendi");
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        callback.onCallback(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("olmadı beee");
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMinStar(double minStar) {
        this.minStar = minStar;
    }

    public double getMinStar() {
        return minStar;
    }
}
