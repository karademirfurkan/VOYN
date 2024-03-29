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
                    name = documentSnapshot.getString("name");
                    System.out.println(name);
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

    public void addTransportationActivityButtonClicked(View view) {
        if("a".equals("b"))
        {
            // Show a toast message to user
            Toast.makeText(AddTransportationActivity.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Add transportation activity to database

            System.out.println("tammam");

            System.out.println("okeyy");



            Transportation transportation = new Transportation(binding.notesEdit.getText().toString());
            System.out.println("transportation zıkkımı");
            transportation.addActivityToDatabase();
            finish();


        }
    }

    public void cancelTransportationActivityButtonClicked(View view) {
        finish();
    }
}