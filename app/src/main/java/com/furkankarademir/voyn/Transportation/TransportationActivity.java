package com.furkankarademir.voyn.Transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.HomeFragment;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivitySignUpPageBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TransportationActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<HashMap<String, Object>> transportationActivities;
    private TransportationAdapter transportationAdapter;
    private ActivityTransportationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = FirebaseFirestore.getInstance();
        transportationActivities = new ArrayList<>();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(TransportationActivity.this));
        transportationAdapter= new TransportationAdapter(transportationActivities);
        binding.recyclerView.setAdapter(transportationAdapter);

        makeArrayList();
    }

    public void makeArrayList()
    {
        db.collection("transportations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    HashMap<String, Object> transportation = (HashMap<String, Object>) documentSnapshot.getData();
                    transportationActivities.add(transportation);
                }
                System.out.println(transportationActivities);
                transportationAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void addTransportationButton(View view) {
        Intent intent = new Intent(this, AddTransportationActivity.class);
        startActivity(intent);
    }
}