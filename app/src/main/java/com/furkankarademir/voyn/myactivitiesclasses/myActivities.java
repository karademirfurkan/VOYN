package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.furkankarademir.voyn.Accomodation.AccomodationAdapter;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Sport.SportAdapter;
import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityMyActivitiesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class myActivities extends AppCompatActivity {

    private ActivityMyActivitiesBinding binding;

    private ArrayList<String> myActivities;

    private ArrayList<String> mySportActivities2;

    private ArrayList<String> myAccommodationActivities2;



    private TransportationAdapter transportationAdapter;

    private SportAdapter sportAdapter;

    private AccomodationAdapter accommodationAdapter;

    private ArrayList<HashMap<String, Object>> myTransportationActivities;

    private ArrayList<HashMap<String, Object>> myAccommodationActivities;

    private ArrayList<HashMap<String, Object>> mySportActivities;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        myTransportationActivities = new ArrayList<>();
        myAccommodationActivities = new ArrayList<>();
        mySportActivities = new ArrayList<>();


        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(myActivities.this));
        transportationAdapter = new TransportationAdapter(myTransportationActivities, 1);
        binding.recyclerView2.setAdapter(transportationAdapter);

        binding.recyclerView6.setLayoutManager(new LinearLayoutManager(myActivities.this));
        accommodationAdapter = new AccomodationAdapter(myAccommodationActivities, 1);
        binding.recyclerView6.setAdapter(accommodationAdapter);

        binding.recyclerView7.setLayoutManager(new LinearLayoutManager(myActivities.this));
        sportAdapter = new SportAdapter(mySportActivities, 1);
        binding.recyclerView7.setAdapter(sportAdapter);


        makeMyTransportationArrayList();
        makeMyAccommodationArrayList();
        makeMySportArrayList();



    }

    public void makeMyTransportationArrayList() {
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
                                    db.collection("transportations").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myTransportationActivities.add(data);
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
        System.out.println(myTransportationActivities);
    }

    public void makeMySportArrayList() {
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            mySportActivities2 = (ArrayList<String>) document.get("mySportActivities");
                            if (mySportActivities2 != null) {
                                for (String activityId : mySportActivities2) {
                                    db.collection("sports").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    mySportActivities.add(data);
                                                    System.out.println(mySportActivities);
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
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        System.out.println(mySportActivities2);
    }

    public void makeMyAccommodationArrayList() {
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myAccommodationActivities2 = (ArrayList<String>) document.get("myAccommodationActivities");
                            if (myAccommodationActivities2 != null) {
                                for (String activityId : myAccommodationActivities2) {
                                    db.collection("accommodations").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myAccommodationActivities.add(data);
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
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
        System.out.println(myAccommodationActivities2);
    }

    public void participatedActivitiesButtonClicked(View view)
    {
        Intent intent = new Intent(myActivities.this, myParticipatedActivitiesPage.class);
        startActivity(intent);
    }
}