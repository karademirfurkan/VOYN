package com.furkankarademir.voyn.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityChatInBetweenPageBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatInBetweenPage extends AppCompatActivity {
    private ActivityChatInBetweenPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatInBetweenPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
}