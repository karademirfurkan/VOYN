package com.furkankarademir.voyn.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityChatInBetweenPageBinding;
import com.furkankarademir.voyn.databinding.ActivityChattedUsersListPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChattedUsersListPage extends AppCompatActivity {
    private ActivityChattedUsersListPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChattedUsersListPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
}