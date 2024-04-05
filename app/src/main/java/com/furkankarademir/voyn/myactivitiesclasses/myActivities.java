package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.furkankarademir.voyn.R;
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

    private TransportationAdapter transportationAdapter;

    private ArrayList<HashMap<String, Object>> myTransportationActivities;

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

        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(myActivities.this));
        transportationAdapter = new TransportationAdapter(myTransportationActivities, 1);
        binding.recyclerView2.setAdapter(transportationAdapter);

        makeMyTransportationArrayList();


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
            });
        }
        System.out.println(myTransportationActivities);

    }
}