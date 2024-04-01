package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityMyActivitiesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myActivities extends AppCompatActivity {

    private ActivityMyActivitiesBinding binding;

    private ArrayList<String> myActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myActivities = (ArrayList<String>) document.get("myActivities");
                            if (myActivities != null && !myActivities.isEmpty()) {
                                binding.textView42.setText(myActivities.get(0));
                            }
                            else {
                                binding.textView42.setText("No activities found");
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

    }
}