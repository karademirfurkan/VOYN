package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.furkankarademir.voyn.Accomodation.AccomodationAdapter;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Sport.SportAdapter;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityMyActivitiesBinding;
import com.furkankarademir.voyn.databinding.ActivityMyParticipatedActivitiesPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class myParticipatedActivitiesPage extends AppCompatActivity {

    ActivityMyParticipatedActivitiesPageBinding binding;

    private ArrayList<String> myActivities;

    private TransportationAdapter transportationAdapter;

    private SportAdapter sportAdapter;

    private AccomodationAdapter accommodationAdapter;

    private ArrayList<HashMap<String, Object>> myParticipatedTransportationActivities;

    private ArrayList<HashMap<String, Object>> myParticipatedAccommodationActivities;

    private ArrayList<HashMap<String, Object>> myParticipatedSportActivities;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyParticipatedActivitiesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        myParticipatedTransportationActivities = new ArrayList<>();
        myParticipatedAccommodationActivities = new ArrayList<>();
        myParticipatedSportActivities = new ArrayList<>();

        binding.transportation.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        transportationAdapter = new TransportationAdapter(myParticipatedTransportationActivities, 2);
        binding.transportation.setAdapter(transportationAdapter);

        binding.sport.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        accommodationAdapter = new AccomodationAdapter(myParticipatedAccommodationActivities, 2);
        binding.sport.setAdapter(accommodationAdapter);

        binding.accomodation.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        sportAdapter = new SportAdapter(myParticipatedSportActivities, 2);
        binding.accomodation.setAdapter(sportAdapter);

        makeMyTransportationArrayList();
        makeMyAccommodationArrayList();
        makeMySportArrayList();
    }

    public void makeMyTransportationArrayList()
    {
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myActivities = (ArrayList<String>) document.get("myActivities");
                            if ( myActivities != null) {
                                for (String activityId : myActivities) {
                                    db.collection("attendedActivities").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myParticipatedTransportationActivities.add(data);
                                                    transportationAdapter.notifyDataSetChanged();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error getting document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }
            });
        }
    }

    public void makeMyAccommodationArrayList()
    {
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myActivities = (ArrayList<String>) document.get("myActivities");
                            if ( myActivities != null) {
                                for (String activityId : myActivities) {
                                    db.collection("attendedActivities").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myParticipatedAccommodationActivities.add(data);
                                                    accommodationAdapter.notifyDataSetChanged();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error getting document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }
            });
        }
    }

    public void makeMySportArrayList()
    {
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myActivities = (ArrayList<String>) document.get("myActivities");
                            if ( myActivities != null) {
                                for (String activityId : myActivities) {
                                    db.collection("attendedActivities").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myParticipatedSportActivities.add(data);
                                                    sportAdapter.notifyDataSetChanged();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error getting document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }
            });
        }
    }
}