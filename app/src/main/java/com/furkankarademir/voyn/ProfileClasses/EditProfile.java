package com.furkankarademir.voyn.ProfileClasses;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.MainActivity;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private DocumentReference documentReference = db.collection("Users").document(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ChangeProfilPicture.setOnClickListener(v ->
        {
            Intent intent = new Intent(EditProfile.this, ChangeProfilePhoto.class);
            startActivity(intent);
        });

        binding.ChangeMailandPassword.setOnClickListener(v ->
        {
            Intent intent = new Intent(EditProfile.this, ChangeEmailandPassword.class);
            startActivity(intent);
        });

        /*binding.Confirm.setOnClickListener(v ->
        {

            if(!binding.editBioInProfile.getText().toString().isEmpty())
            {
                String newBio = binding.editBioInProfile.getText().toString();
                String userId = auth.getCurrentUser().getUid();

                DocumentReference docRef = db.collection("Users").document(userId);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            User user = documentSnapshot.toObject(User.class);
                            user.setBio(newBio);

                            docRef.set(user)
                                    .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                    .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                        }
                    }
                });
            }
            if (!binding.ageEditInProfile.getText().toString().isEmpty()) {
                String newAge = binding.ageEditInProfile.getText().toString();
                String userId = auth.getCurrentUser().getUid();

                DocumentReference docRef = db.collection("Users").document(userId);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            User user = documentSnapshot.toObject(User.class);
                            user.setAge(newAge);

                            docRef.set(user)
                                    .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                    .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                        }
                    }
                });
            }

            finish();
        });*/
        binding.Confirm.setOnClickListener(v -> {
            String userId = auth.getCurrentUser().getUid();
            DocumentReference docRef = db.collection("Users").document(userId);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        User user = documentSnapshot.toObject(User.class);

                        if(!binding.editBioInProfile.getText().toString().isEmpty()) {
                            String newBio = binding.editBioInProfile.getText().toString();
                            user.setBio(newBio);
                        }

                        if (!binding.ageEditInProfile.getText().toString().isEmpty()) {
                            String newAge = binding.ageEditInProfile.getText().toString();
                            user.setAge(newAge);
                        }

                        docRef.set(user)
                                .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                    }
                }
            });
            finish();
        });

        binding.DeleteProfile.setOnClickListener(v ->
        {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            // Get the current user's ID
            String userId = auth.getCurrentUser().getUid();

            // Define the collections
            String[] collections = {"transportations", "sports", "accommodations"};

            for (String collection : collections) {
                // Fetch and delete all activities where the user is the creator
                db.collection(collection)
                        .whereEqualTo("creatorId", userId)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    // No activities created by the user
                                } else {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Delete the activity
                                        document.getReference().delete();
                                    }
                                }
                        }
                        });
            }



            // Fetch all activities where the user is an attendee
            db.collection("Activities")
                    .whereArrayContains("attendeeIds", userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Get the list of attendee IDs
                                ArrayList<String> attendeeIds = (ArrayList<String>) document.get("attendeeIds");

                                // Remove the user's ID from the list
                                attendeeIds.remove(userId);

                                // Update the attendeeIds of the activity
                                document.getReference().update("attendeeIds", attendeeIds);
                            }
                        }
                    });

            // Delete the user from the Users collection
            db.collection("Users").document(userId)
                    .delete();

            // Delete the user from the authentication system
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Redirect to MainActivity after user deletion
                            Intent intent = new Intent(EditProfile.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

            FirebaseAuth.getInstance().signOut();
        });

    }
}