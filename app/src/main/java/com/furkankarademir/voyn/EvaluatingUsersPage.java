package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;
import com.furkankarademir.voyn.databinding.ActivityEvaluatingUsersPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EvaluatingUsersPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ActivityEvaluatingUsersPageBinding binding;
    private EvaluationPageUserListAdapter evaluationPageUserListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEvaluatingUsersPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        ArrayList<String> usersId = (ArrayList<String>) intent.getSerializableExtra("participants");
        System.out.println(usersId + "    evaluation cart curt");

        binding.evRv.setLayoutManager(new LinearLayoutManager(EvaluatingUsersPage.this));
        evaluationPageUserListAdapter = new EvaluationPageUserListAdapter(usersId);
        binding.evRv.setAdapter(evaluationPageUserListAdapter);
    }

    public void askMeLaterClicked(View view)
    {
        Intent intent = new Intent(EvaluatingUsersPage.this, HomeFragment.class);
        startActivity(intent);
    }

    public void doNotAskAgainClicked(View view)
    {
        Intent intent = new Intent(EvaluatingUsersPage.this, HomeFragment.class);
        startActivity(intent);
    }

    public void okClicked(View view)
    {
        handleGivingStar();
        Toast.makeText(EvaluatingUsersPage.this, "oldu", Toast.LENGTH_LONG).show();
        
        Intent intent = new Intent(EvaluatingUsersPage.this, HomeFragment.class);
        startActivity(intent);
    }

    public void handleGivingStar()
    {
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    User user = snapshot.toObject(User.class);

                    double expectedStar = user.getExpectedStar();

                    user.setStar(expectedStar);

                    //snapshot.getReference().set(user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}