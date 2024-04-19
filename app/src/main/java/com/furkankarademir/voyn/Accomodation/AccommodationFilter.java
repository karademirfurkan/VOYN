package com.furkankarademir.voyn.Accomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityAccommodationFilterBinding;

public class AccommodationFilter extends AppCompatActivity {

    private ActivityAccommodationFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityAccommodationFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("place", binding.placeFilter.getText().toString());
                resultIntent.putExtra("calendar", binding.calendar.getDate());
                resultIntent.putExtra("availablity", binding.switchAvailable.isChecked());
                resultIntent.putExtra("locked", binding.switchLocked.isChecked());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}