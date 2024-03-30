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

        System.out.println(transportationMap);
        String name = (String) transportationMap.get("name");
        System.out.println(name);
        binding.nameInfo.setText(name);


    }
}