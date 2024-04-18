package com.furkankarademir.voyn.Transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityTransportationFilterBinding;

public class TransportationFilter extends AppCompatActivity {

    private ActivityTransportationFilterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationFilterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("departure", binding.departure.getText().toString());
                resultIntent.putExtra("destination", binding.destination.getText().toString());
                resultIntent.putExtra("time", binding.timeFilter.getText().toString());
                resultIntent.putExtra("availablity", binding.switchAvailable.isChecked());
                resultIntent.putExtra("locked", binding.switchLocked.isChecked());
                resultIntent.putExtra("calendar", binding.calendar.getDate());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}