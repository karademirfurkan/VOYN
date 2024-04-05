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


    private ArrayList<User> acceptedUserList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    private AcceptedIncomingInvitationsAdapter acceptedIncomingInvitationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTransportationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");
        transportation = transportationMap;

        acceptedUserList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(myTransportationActivityDetails.this));
        acceptedIncomingInvitationsAdapter = new AcceptedIncomingInvitationsAdapter(acceptedUserList);
        binding.recyclerView3.setAdapter(acceptedIncomingInvitationsAdapter);

        makeAcceptedUserList();
    }

    public void makeAcceptedUserList()
    {
        //db.collection("Users")
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        intent.putExtra("transportation", transportation);
        startActivity(intent);
    }
}