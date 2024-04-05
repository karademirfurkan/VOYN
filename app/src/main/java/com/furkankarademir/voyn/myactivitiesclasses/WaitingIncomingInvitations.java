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

    private ActivityWaitingIncomingInvitationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaitingIncomingInvitationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent intent = getIntent();
        HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");
        transportation = transportationMap;

        binding.recyclerView4.setLayoutManager(new LinearLayoutManager(WaitingIncomingInvitations.this));
        ArrayList<String> incomingInvitations = (ArrayList<String>) transportation.get("invited");
        IncomingInvitationsAdapter incomingInvitationsAdapter = new IncomingInvitationsAdapter(incomingInvitations);
        binding.recyclerView4.setAdapter(incomingInvitationsAdapter);

    }
}