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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        if (userID == null) {
            thisUser = new User("Emir", "Momoli");
        }

        DocumentReference docRef = db.collection("Users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        thisUser = document.toObject(User.class);
                    } else {
                        Log.d("DocumentSnapshot", "No such document");
                    }
                } else {
                    Log.d("DocumentSnapshot", "get failed with ", task.getException());
                }
            }
        });
    }

    public void addTransportationActivityButtonClicked(View view) {
        if(binding.departureEdit.getText().toString().equals("") || binding.destinationEdit.getText().toString().equals("") || binding.timeEdit.getText().toString().equals("") || binding.dateEdit.getText().toString().equals("") || binding.seatsEdit.getText().toString().equals(""))
        {
            // Show a toast message to user
            Toast.makeText(AddTransportationActivity.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Add transportation activity to database
            Date date = new Date(binding.dateEdit.getText().toString());
            creatorProfile = thisUser.getProfile();
            if(creatorProfile == null)
            {
                creatorProfile = new Profile(thisUser.getName(), thisUser.getSurname(), thisUser.getMail(), "Department");
            }
            String timeString = binding.timeEdit.getText().toString();
            Time time = new Time(Integer.parseInt(timeString.substring(0, 2)), Integer.parseInt(timeString.substring(3, 5)), 0);
            Transportation transportation = new Transportation(date, creatorProfile, time, binding.departureEdit.getText().toString(), binding.destinationEdit.getText().toString(), Integer.parseInt(binding.seatsEdit.getText().toString()), binding.notesEdit.getText().toString());
            transportation.addActivityToDatabase();
            finish();


        }
    }

    public void cancelTransportationActivityButtonClicked(View view) {
        finish();
    }
}