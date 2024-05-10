package com.furkankarademir.voyn.myactivitiesclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileFragment;
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

    private String age;
    private String department;

    private FirebaseFirestore db;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageForOtherUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent.hasExtra("userId")) {
            userID = intent.getStringExtra("userId");
        }

        Intent intent2 = getIntent();
        if(intent2.hasExtra("idComing"))
        {
            userID = intent.getStringExtra("idComing");
        }




        db = FirebaseFirestore.getInstance();
        getUserFromFirebase();

        ImageView profilePhoto = binding.imageView11;
        profilePhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        DocumentReference docRef = db.collection("Users").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    user = documentSnapshot.toObject(User.class);
                    if (user.getProfilePhotoUrl() != null && !user.getProfilePhotoUrl().isEmpty()) {
                        Glide.with(profilePageForOtherUsers.this)
                                .load(user.getProfilePhotoUrl())
                                .into(profilePhoto);
                    } else {
                        profilePhoto.setImageResource(R.drawable.profile_photo);
                    }
                    binding.starTextInProfile.setText(String.format("%.2f", user.getStar()));
                    binding.biography.setText(user.getBio());
                    binding.genderInProfile.setText("Male");
                }
            }
        });


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
                    age = documentSnapshot.getString("age");
                    department = documentSnapshot.getString("department");
                    binding.nameInProfile.setText(name + " " + surname);
                    binding.ageInProfile.setText(age);
                    binding.departmentInProfile.setText(department);
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