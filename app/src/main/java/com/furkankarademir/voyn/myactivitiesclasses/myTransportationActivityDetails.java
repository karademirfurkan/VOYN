package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;

public class myTransportationActivityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transportation_details);
    }

    public void incomingInvitationButtonClicked(View view) {
        Intent intent = new Intent(this, WaitingIncomingInvitations.class);
        startActivity(intent);
    }
}