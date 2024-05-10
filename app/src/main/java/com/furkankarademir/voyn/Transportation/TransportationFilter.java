package com.furkankarademir.voyn.Transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityTransportationFilterBinding;

import java.util.Calendar;

public class TransportationFilter extends AppCompatActivity {

    private ActivityTransportationFilterBinding binding;

    private Long selectedDateInMillis = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationFilterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


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
                resultIntent.putExtra("departure", binding.departure.getText().toString());
                resultIntent.putExtra("destination", binding.destination.getText().toString());
                resultIntent.putExtra("time", binding.timeFilter.getText().toString());
                resultIntent.putExtra("availablity", binding.switchAvailable.isChecked());
                resultIntent.putExtra("locked", binding.switchLocked.isChecked());
                resultIntent.putExtra("calendar", selectedDateInMillis);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}