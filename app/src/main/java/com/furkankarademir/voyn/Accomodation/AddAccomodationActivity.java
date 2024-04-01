package com.furkankarademir.voyn.Accomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityAddAccomodationBinding;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;

public class AddAccomodationActivity extends AppCompatActivity {
    private ActivityAddAccomodationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAccomodationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void openActivityButtonClicked()
    {

    }

    public void cancelButtonClicked()
    {
        Intent intent = new Intent(AddAccomodationActivity.this, AccomodationActivity.class);
        startActivity(intent);
        finish();
    }
}