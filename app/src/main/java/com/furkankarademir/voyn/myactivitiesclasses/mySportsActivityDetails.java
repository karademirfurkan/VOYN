package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityMySportsDetailsBinding;
import com.furkankarademir.voyn.databinding.ActivityMyTransportationDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class mySportsActivityDetails extends AppCompatActivity {

    private HashMap<String, Object> sports;

    private ActivityMySportsDetailsBinding binding;


    private ArrayList<String> acceptedUserList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    private HashMap<String, Object> sportsMap;

    private AcceptedIncomingInvitationsAdapter acceptedIncomingInvitationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMySportsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        sportsMap = (HashMap<String, Object>) intent.getSerializableExtra("sport");
        sports = sportsMap;

        binding.typeForMySportActivities.setText(sports.get("type").toString());
        binding.dateForMySportsActivities.setText(sports.get("date").toString());
        binding.timeForMySportsActivities.setText(sports.get("time").toString());
        binding.placeForMySportsActivities.setText(sports.get("place").toString());
        binding.personLimitForMySportsActivities.setText(sports.get("numberOfPlayers").toString());

        acceptedUserList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        acceptedUserList = (ArrayList<String>) sportsMap.get("participantsId");

        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(mySportsActivityDetails.this));
        acceptedIncomingInvitationsAdapter = new AcceptedIncomingInvitationsAdapter(acceptedUserList);
        binding.recyclerView3.setAdapter(acceptedIncomingInvitationsAdapter);
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        intent.putExtra("sports", sports);
        startActivity(intent);
    }
}