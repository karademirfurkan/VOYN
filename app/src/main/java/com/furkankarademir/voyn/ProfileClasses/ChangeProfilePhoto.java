package com.furkankarademir.voyn.ProfileClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityChangeProfilePhotoBinding;

public class ChangeProfilePhoto extends AppCompatActivity {

    private ActivityChangeProfilePhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeProfilePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}