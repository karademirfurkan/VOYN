package com.furkankarademir.voyn.Transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTransportationActivity extends AppCompatActivity {

    private ActivityAddTransportationBinding binding;

    public  AddTransportationActivity() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransportationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void addTransportationActivityButtonClicked(View view) {
        if(binding.departureEdit.getText().toString().equals("") || binding.destinationEdit.getText().toString().equals("") || binding.timeEdit.getText().toString().equals("") || binding.dateEdit.getText().toString().equals("") || binding.seatsEdit.getText().toString().equals(""))
        {
            // Show a toast message to user
            Toast.makeText(AddTransportationActivity.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Add transportation activity to database
            Date date = new Date(binding.dateEdit.getText().toString());
            Profile creatorProfile = (Profile) getIntent().getSerializableExtra("creatorProfile");
            String timeString = binding.timeEdit.getText().toString();
            Time time = new Time(Integer.parseInt(timeString.substring(0, 2)), Integer.parseInt(timeString.substring(3, 5)), 0);
            Transportation transportation = new Transportation(date, creatorProfile, time, binding.departureEdit.getText().toString(), binding.destinationEdit.getText().toString(), Integer.parseInt(binding.seatsEdit.getText().toString()), binding.notesEdit.getText().toString());
            transportation.addActivityToDatabase();
            finish();


        }
    }

    public void cancelTransportationActivityButtonClicked(View view) {
        finish();
    }
}