package com.furkankarademir.voyn.Accomodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityAccommodationFilterBinding;

import java.util.Calendar;

public class AccommodationFilter extends AppCompatActivity {

    private ActivityAccommodationFilterBinding binding;

    private Long selectedDateInMillis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityAccommodationFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Note: month is 0-based.
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Save the selected date in milliseconds since epoch
                selectedDateInMillis = selectedDate.getTimeInMillis();
            }
        });

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("place", binding.placeFilter.getText().toString());
                resultIntent.putExtra("calendar", selectedDateInMillis);
                resultIntent.putExtra("availablity", binding.switchAvailable.isChecked());
                resultIntent.putExtra("locked", binding.switchLocked.isChecked());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}