package com.furkankarademir.voyn.Transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SplittableRandom;

public class AddTransportationActivity extends AppCompatActivity {

    private ActivityAddTransportationBinding binding;

    private User thisUser;

    private String userID;

    private FirebaseFirestore db;

    private  Profile creatorProfile;

    private FirebaseAuth auth;

    private String name;
    private String surname;
    private String mail;

    public  AddTransportationActivity() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransportationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserFromFirebase();
    }
    private void getUserFromFirebase() {
        DocumentReference docRef = db.collection("Users").document(userID);
        docRef.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    Toast.makeText(AddTransportationActivity.this, "oldu", Toast.LENGTH_LONG).show();
                    name = documentSnapshot.getString("name");
                    System.out.println("bunun ismi" + name);
                    surname = documentSnapshot.getString("surname");
                    mail = documentSnapshot.getString("mail");
                }
                else
                {
                    Toast.makeText(AddTransportationActivity.this, "olmadı", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTransportationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /*private void getUserFromFirebase() {
        FirebaseFirestore.getInstance().collection("Users").document(userID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                name = document.getString("name");
                                surname = document.getString("surname");
                                mail = document.getString("mail");
                                Toast.makeText(AddTransportationActivity.this, "User retrieved successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddTransportationActivity.this, "No such document", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddTransportationActivity.this, "Error getting document: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/

    public void addTransportationActivityButtonClicked(View view) {
        if(binding.notesEdit.getText().toString().equals(""))
        {
            // Show a toast message to user
            Toast.makeText(AddTransportationActivity.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Add transportation activity to database

            Transportation transportation = new Transportation(name, surname, mail, binding.dateEdit.getText().toString(),
                    binding.timeEdit.getText().toString(), binding.departureEdit.getText().toString(), binding.destinationEdit.getText().toString(),
                    Integer.parseInt(binding.seatsNumberEdit.getText().toString()), binding.notesEdit.getText().toString(), userID);
                  //  ,binding.notesEdit.getText().toString());

            transportation.addActivityToDatabase();
            finish();


        }
    }

    public void cancelTransportationActivityButtonClicked(View view) {
        finish();
    }
}