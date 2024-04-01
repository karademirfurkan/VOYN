package com.furkankarademir.voyn.Accomodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.AddTransportationActivity;
import com.furkankarademir.voyn.Transportation.Transportation;
import com.furkankarademir.voyn.databinding.ActivityAddAccomodationBinding;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddAccomodationActivity extends AppCompatActivity {
    private ActivityAddAccomodationBinding binding;

    private String name;
    private String surname;
    private String mail;

    private Profile creatorProfile;
    private User thisUser;


    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAccomodationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getUserFromFirebase();
    }

    public void getUserFromFirebase()
    {
        db.collection("Users").document(auth.getUid())
            .get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    name = documentSnapshot.getString("name");
                    surname = documentSnapshot.getString("surname");
                    mail = documentSnapshot.getString("mail");

                    Toast.makeText(AddAccomodationActivity.this, "oldu", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(AddAccomodationActivity.this, "olmadı", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddAccomodationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openActivityButtonClicked(View view)
    {
        if(!binding.type.getText().toString().equals(""))
        {
            if(!binding.place.getText().toString().equals(""))
            {
                if(!binding.date.getText().toString().equals(""))
                {
                    if(!binding.gender.getText().toString().equals(""))
                    {
                        if(!binding.numberOfInhabitants.getText().toString().equals(""))
                        {
                            if(!binding.extraNotes.getText().toString().equals(""))
                            {
                                System.out.println("oldu");
                                Accomodation accomodation = new Accomodation(name, surname, mail, binding.date.getText().toString(),
                                        "boş", binding.extraNotes.getText().toString(), auth.getUid().toString(),
                                        binding.type.getText().toString(), binding.place.getText().toString(), binding.gender.getText().toString(),
                                        Integer.parseInt(binding.numberOfInhabitants.getText().toString()));

                                System.out.println("oldu2");
                                accomodation.addActivityToDatabase();
                                System.out.println("oldu3");
                                finish();
                            }
                            else
                            {
                                Toast.makeText(AddAccomodationActivity.this, "Please enter extra note", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(AddAccomodationActivity.this, "Please enter number of inhabitants", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddAccomodationActivity.this, "Please enter gender", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(AddAccomodationActivity.this, "Please enter date", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(AddAccomodationActivity.this, "Please enter place", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(AddAccomodationActivity.this, "Please enter type", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelButtonClicked(View view)
    {
        Intent intent = new Intent(AddAccomodationActivity.this, AccomodationActivity.class);
        startActivity(intent);
        finish();
    }
}