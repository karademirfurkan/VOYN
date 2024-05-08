package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityMyAccommodationDetailsBinding;
import com.furkankarademir.voyn.databinding.ActivityMyTransportationDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class myAccommodationActivityDetails extends AppCompatActivity {

    private HashMap<String, Object> accommodation;

    private ActivityMyAccommodationDetailsBinding binding;


    private ArrayList<String> acceptedUserList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    private HashMap<String, Object> accommodationMap;

    private AcceptedIncomingInvitationsAdapter acceptedIncomingInvitationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAccommodationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        accommodationMap = (HashMap<String, Object>) intent.getSerializableExtra("accommodation");
        accommodation = accommodationMap;

        binding.accomodationDateForMyActivities.setText(accommodation.get("date").toString());
        binding.placeForMyActivities.setText(accommodation.get("place").toString());
        binding.personLimitForMyActivities.setText(accommodation.get("numberOfInhabitants").toString());
        binding.genderForMyActivities.setText(accommodation.get("gender").toString());


        acceptedUserList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        acceptedUserList = (ArrayList<String>) accommodationMap.get("participantsId");

        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(myAccommodationActivityDetails.this));
        acceptedIncomingInvitationsAdapter = new AcceptedIncomingInvitationsAdapter(acceptedUserList);
        binding.recyclerView3.setAdapter(acceptedIncomingInvitationsAdapter);
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        intent.putExtra("accommodation", accommodation);
        startActivity(intent);
    }
}