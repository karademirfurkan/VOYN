package com.furkankarademir.voyn.Sport;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.ParentClassesForActivity.FireStoreCallback;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.Sport.AddSportsActivity;
import com.furkankarademir.voyn.Sport.Sport;
import com.furkankarademir.voyn.databinding.ActivityAddSportsBinding;
import com.furkankarademir.voyn.databinding.ActivitySportsDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddSportsActivity extends AppCompatActivity {

    private ActivityAddSportsBinding binding;
    private User thisUser;
    private String userID;

    private FirebaseFirestore db;
    private Profile creatorProfile;

    private FirebaseAuth auth;

    private String name;
    private String surname;
    private String mail;

    public AddSportsActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSportsBinding.inflate(getLayoutInflater());
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
                    Toast.makeText(AddSportsActivity.this, "oldu", Toast.LENGTH_LONG).show();
                    name = documentSnapshot.getString("name");
                    System.out.println("bunun ismi" + name);
                    surname = documentSnapshot.getString("surname");
                    mail = documentSnapshot.getString("mail");

                    thisUser = documentSnapshot.toObject(User.class);
                }
                else
                {
                    System.out.println("olmadı");
                    Toast.makeText(AddSportsActivity.this, "olmadı", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSportsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void addSportActivityButtonClicked(View view) {
        if(binding.notesEdit.getText().toString().equals("")) {
            Toast.makeText(AddSportsActivity.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        } else {
            Sport sport = new Sport(name, surname, mail, binding.dateEdit.getText().toString(),
                    binding.timeEdit.getText().toString(), binding.placeEdit.getText().toString(),
                    Integer.parseInt(binding.numberOfPlayersEdit.getText().toString()), binding.notesEdit.getText().toString(), userID);
            DocumentReference docRef = db.collection("Users").document(userID);

            sport.addActivityToDatabase(new FireStoreCallback() {
                @Override
                public void onCallback(String id) {
                    if(thisUser != null) {
                        thisUser.addActivity(id);
                        docRef.set(thisUser);
                    }
                }
            });
            /*docRef.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        Toast.makeText(AddSportActivity.this, "user bulundu", Toast.LENGTH_LONG).show();
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            user.addActivity(thisActivityId);
                            docRef.set(user); // Update the user document in Firebase
                        }
                    } else {
                        Toast.makeText(AddSportActivity.this, "olmadı", Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddSportActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });*/

            finish();
        }
    }

    public void cancelSportActivityButtonClicked(View view) {
        finish();
    }
}