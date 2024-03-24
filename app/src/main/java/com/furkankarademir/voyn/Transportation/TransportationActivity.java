package com.furkankarademir.voyn.Transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;

public class TransportationActivity extends AppCompatActivity {

    private Profile creatorProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        creatorProfile = (Profile) getIntent().getSerializableExtra("creatorProfile");
    }

    public void addTransportationButton(View view) {
        Intent intent = new Intent(this, AddTransportationActivity.class);
        intent.putExtra("creatorProfile", creatorProfile);
        startActivity(intent);
    }
}