package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityWaitingIncomingInvitationsBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class WaitingIncomingInvitations extends AppCompatActivity {

    private HashMap<String, Object> transportation;

    private HashMap<String, Object> accommodation;

    private HashMap<String, Object> sport;

    private ActivityWaitingIncomingInvitationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaitingIncomingInvitationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent intent = getIntent();
        if(intent.getSerializableExtra("transportation") != null)
        {
            HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");
            transportation = transportationMap;

            ArrayList<String> participants = (ArrayList<String>) transportation.get("participantsId");
            boolean isFull = false;
            if (participants.size() == Integer.parseInt(transportation.get("seats").toString())) {
                isFull = true;
            }

            binding.recyclerView4.setLayoutManager(new LinearLayoutManager(WaitingIncomingInvitations.this));
            ArrayList<String> incomingInvitations = (ArrayList<String>) transportation.get("invited");
            IncomingInvitationsAdapter incomingInvitationsAdapter = new IncomingInvitationsAdapter(incomingInvitations, transportation.get("documentId").toString(), isFull);
            binding.recyclerView4.setAdapter(incomingInvitationsAdapter);
        }
        else if (intent.getSerializableExtra("accommodation") != null)
        {
            HashMap<String, Object> accommodationMap = (HashMap<String, Object>) intent.getSerializableExtra("accommodation");
            accommodation = accommodationMap;

            ArrayList<String> participants = (ArrayList<String>) accommodation.get("participantsId");
            boolean isFull = false;
            if (participants.size() == Integer.parseInt(accommodation.get("numberOfInhabitants").toString())) {
                isFull = true;
            }

            binding.recyclerView4.setLayoutManager(new LinearLayoutManager(WaitingIncomingInvitations.this));
            ArrayList<String> incomingInvitations = (ArrayList<String>) accommodation.get("invited");
            IncomingInvitationsAdapter incomingInvitationsAdapter = new IncomingInvitationsAdapter(incomingInvitations, accommodation.get("documentId").toString(), isFull);
            binding.recyclerView4.setAdapter(incomingInvitationsAdapter);
        }
        else if (intent.getSerializableExtra("sports") != null)
        {
            HashMap<String, Object> sportsMap = (HashMap<String, Object>) intent.getSerializableExtra("sports");
            sport = sportsMap;

            ArrayList<String> participants = (ArrayList<String>) sport.get("participantsId");
            boolean isFull = false;
            if (participants.size() == Integer.parseInt(sport.get("numberOfPlayers").toString())) {
                isFull = true;
            }

            binding.recyclerView4.setLayoutManager(new LinearLayoutManager(WaitingIncomingInvitations.this));
            ArrayList<String> incomingInvitations = (ArrayList<String>) sport.get("invited");
            IncomingInvitationsAdapter incomingInvitationsAdapter = new IncomingInvitationsAdapter(incomingInvitations, sport.get("documentId").toString(), isFull);
            binding.recyclerView4.setAdapter(incomingInvitationsAdapter);
        }

    }
}