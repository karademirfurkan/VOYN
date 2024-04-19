package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.AddTransportationActivity;
import com.furkankarademir.voyn.databinding.ActivityProfilePageForOtherUsersBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profilePageForOtherUsers extends AppCompatActivity
{
    private ActivityProfilePageForOtherUsersBinding binding;

    private User thisUser;
    private String name;
    private String surname;
    private String mail;
    private String userID;
    private String profilePhoto;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageForOtherUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userID = intent.getStringExtra("userId");


        db = FirebaseFirestore.getInstance();
        getUserFromFirebase();



    }

    private void getUserFromFirebase() {
        DocumentReference docRef = db.collection("Users").document(userID);
        docRef.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    name = documentSnapshot.getString("name");
                    System.out.println("bunun ismi" + name);
                    surname = documentSnapshot.getString("surname");
                    mail = documentSnapshot.getString("mail");

                    thisUser = documentSnapshot.toObject(User.class);
                    binding.nameInProfile.setText(name + " " + surname);
                }
                else
                {
                    System.out.println("olmadı");

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profilePageForOtherUsers.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}