package com.furkankarademir.voyn.Sport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivitySportsFilterBinding;

public class SportsFilter extends AppCompatActivity {
    private ActivitySportsFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportsFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("time", binding.timeFilter.getText().toString());
                resultIntent.putExtra("place", binding.sportPlace.getText().toString());
                resultIntent.putExtra("type", binding.sportType.getText().toString());
                resultIntent.putExtra("availablity", binding.switchAvailable.isChecked());
                resultIntent.putExtra("locked", binding.switchLocked.isChecked());
                resultIntent.putExtra("calendar", binding.calendar.getDate());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}