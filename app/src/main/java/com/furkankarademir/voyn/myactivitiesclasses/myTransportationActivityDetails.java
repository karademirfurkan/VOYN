package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;

import java.util.HashMap;

public class myTransportationActivityDetails extends AppCompatActivity {

    private HashMap<String, Object> transportation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transportation_details);

        Intent intent = getIntent();
        HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");
        transportation = transportationMap;
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        intent.putExtra("transportation", transportation);
        startActivity(intent);
    }
}