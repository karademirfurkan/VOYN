package com.furkankarademir.voyn.ProfileClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.furkankarademir.voyn.databinding.ChangeProfilePhotoBinding;


import com.furkankarademir.voyn.R;

public class ChangeProfilePhoto extends AppCompatActivity {

    private binding ChangeProfilePhotoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeProfilePhotoBinding = ChangeProfilePhotoBinding.inflate(getLayoutInflater());
        setContentView(ChangeProfilePhotoBinding.getRoot());

    }
}