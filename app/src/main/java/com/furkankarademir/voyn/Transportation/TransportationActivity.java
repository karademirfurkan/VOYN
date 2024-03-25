package com.furkankarademir.voyn.Transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;

import java.io.Serializable;

public class TransportationActivity extends AppCompatActivity {

    private User thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        thisUser = (User) getIntent().getSerializableExtra("thisUser");
    }

    public void addTransportationButton(View view) {
        Intent intent = new Intent(this, AddTransportationActivity.class);
        intent.putExtra("thisUser", (Serializable) thisUser);
        startActivity(intent);
    }
}