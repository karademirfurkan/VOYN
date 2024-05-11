package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityMyActivitiesBinding;
import com.furkankarademir.voyn.databinding.ActivityMyTransportationDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class myTransportationActivityDetails extends AppCompatActivity {

    private HashMap<String, Object> transportation;

    private ActivityMyTransportationDetailsBinding binding;


    private ArrayList<String> acceptedUserList;

    private FirebaseFirestore db;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    private HashMap<String, Object> transportationMap;

    private AcceptedIncomingInvitationsAdapter acceptedIncomingInvitationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTransportationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");
        transportation = transportationMap;

        binding.timeForMyTransportationActivities.setText(transportation.get("time").toString());
        binding.dateForMyTransportationActivities.setText(transportation.get("date").toString());
        binding.availableSeatsForMyTransportationActivities.setText(transportation.get("seats").toString());
        binding.whereFromForMyTransportationActivities.setText(transportation.get("departure").toString());
        binding.whereToForMyTransportationActivities.setText(transportation.get("destination").toString());

        if (transportation != null && auth.getCurrentUser() != null) {
            if (!transportation.get("creatorUserID").equals(auth.getCurrentUser().getUid())) {
                binding.button11.setVisibility(View.INVISIBLE);
            }
        } else {
            // Handle the case where transportation or the current user is null
        }
        acceptedUserList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        acceptedUserList = (ArrayList<String>) transportationMap.get("participantsId");

        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(myTransportationActivityDetails.this));
        acceptedIncomingInvitationsAdapter = new AcceptedIncomingInvitationsAdapter(acceptedUserList);
        binding.recyclerView3.setAdapter(acceptedIncomingInvitationsAdapter);
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        intent.putExtra("transportation", transportation);
        startActivity(intent);
    }
}