package com.furkankarademir.voyn.Transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityTransportationDetailBinding;

import java.util.HashMap;

public class TransportationDetailActivity extends AppCompatActivity {

    private ActivityTransportationDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");

        String name = (String) transportationMap.get("name");
        binding.nameInfo.setText(name);
        binding.surnameInfo.setText((String) transportationMap.get("surname"));
        binding.mailInfo.setText((String) transportationMap.get("mail"));
        binding.departureInfo.setText((String) transportationMap.get("departure"));
        binding.destinationInfo.setText((String) transportationMap.get("destination"));
        binding.dateInfo.setText((String) transportationMap.get("date"));
        binding.timeInfo.setText((String) transportationMap.get("time"));
        binding.seatsInfo.setText(transportationMap.get("seats").toString());
    }

    public void sendInvitationButtonClicked(View view)
    {

    }
}