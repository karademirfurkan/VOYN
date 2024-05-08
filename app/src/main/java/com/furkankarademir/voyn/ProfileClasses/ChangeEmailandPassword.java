package com.furkankarademir.voyn.ProfileClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityChangeEmailandPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeEmailandPassword extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = auth.getCurrentUser();
    ActivityChangeEmailandPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_emailand_password);

        binding = ActivityChangeEmailandPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.Confirmbutton.setOnClickListener(v -> {
            String userId = auth.getCurrentUser().getUid();
            DocumentReference docRef = db.collection("Users").document(userId);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);

                        if (!binding.changeMail.getText().toString().isEmpty()) {
                            String newMail = binding.changeMail.getText().toString();
                            user.setMail(newMail);
                            firebaseUser.verifyBeforeUpdateEmail(newMail);
                            auth.getCurrentUser().verifyBeforeUpdateEmail(newMail);
                            changeemail(user.getMail(), user.getPassword());
                        }

                        if (!binding.changePassword.getText().toString().isEmpty()) {
                            String newPassword = binding.changePassword.getText().toString();
                            user.setPassword(newPassword);
                            firebaseUser.updatePassword(newPassword);
                            auth.getCurrentUser().updatePassword(newPassword);
                        }

                        docRef.set(user)
                                .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                    }
                }
            });
            finish();
        });

    }

    private void changeemail(String email, final String password) {


         View mail = findViewById(R.id.changeMail);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        // Get auth credentials from the user for re-authentication

        AuthCredential credential = EmailAuthProvider.getCredential(email, password); // Current Login Credentials


        // Prompt the user to re-provide their sign-in credentials

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {



            @Override

            public void onComplete(@NonNull Task<Void> task) {


                Log.d("value", "User re-authenticated.");


                // Now change your email address \\

                //----------------Code for Changing Email Address----------\\

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.verifyBeforeUpdateEmail(binding.changeMail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(ChangeEmailandPassword.this, "Email Changed" + " Current Email is " + binding.changeMail.getText().toString(), Toast.LENGTH_LONG).show();

                        }

                    }

                });

            }

        });


    }
}