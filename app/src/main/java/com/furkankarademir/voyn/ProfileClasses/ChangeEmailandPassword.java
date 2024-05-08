package com.furkankarademir.voyn.ProfileClasses;

import static androidx.fragment.app.FragmentManager.TAG;

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
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeEmailandPassword extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = auth.getCurrentUser();

    FirebaseAuthSettings firebaseAuthSettings = auth.getFirebaseAuthSettings();

    ActivityChangeEmailandPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_emailand_password);

        binding = ActivityChangeEmailandPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuthSettings.setAppVerificationDisabledForTesting(true);
        /*binding.Confirmbutton.setOnClickListener(v -> {
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
                            firebaseUser.updateEmail(newMail);
                        }

                        if (!binding.changePassword.getText().toString().isEmpty()) {
                            String newPassword = binding.changePassword.getText().toString();
                            user.setPassword(newPassword);
                            firebaseUser.updatePassword(newPassword);
                        }

                        docRef.set(user)
                                .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                    }
                }
            });
            finish();
        });*/

        /*binding.Confirmbutton.setOnClickListener(v -> {
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

                            // Re-authenticate user before updating email
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getMail(), user.getPassword());
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    firebaseUser.updateEmail(newMail).addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {
                                            // Handle error
                                            System.out.println("Error updating email");
                                        }
                                    });
                                } else {
                                    // Handle error
                                    System.out.println("Error re-authenticating");
                                }
                            });
                        }

                        if (!binding.changePassword.getText().toString().isEmpty()) {
                            String newPassword = binding.changePassword.getText().toString();
                            user.setPassword(newPassword);

                            // Re-authenticate user before updating password
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getMail(), user.getPassword());
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {
                                            // Handle error
                                            System.out.println("Error updating password");
                                        }
                                    });
                                } else {
                                    // Handle error
                                    System.out.println("Error re-authenticating");
                                }
                            });
                        }

                        docRef.set(user)
                                .addOnSuccessListener(aVoid -> System.out.println("DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                    }
                }
            });
            finish();
        });*/

        binding.Confirmbutton.setOnClickListener(v -> {
            String userId = auth.getCurrentUser().getUid();
            DocumentReference docRef = db.collection("Users").document(userId);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);

                        if (!binding.changeMail.getText().toString().isEmpty() && binding.changePassword.getText().toString().isEmpty()) {
                            String newMail = binding.changeMail.getText().toString();


                            // Re-authenticate user before updating email
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getMail(), user.getPassword());
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    firebaseUser.updateEmail(newMail).addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {
                                            // Handle error
                                            System.out.println("Error updating email");
                                        } else {
                                            user.setMail(newMail);
                                            docRef.set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            System.out.println("DocumentSnapshot successfully updated!");
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(aVoid -> System.out.println("Error updating document"));

                                        }
                                    });
                                } else {
                                    System.out.println("Error re-authenticating");
                                }
                            });

                        }

                        if (!binding.changePassword.getText().toString().isEmpty() && binding.changeMail.getText().toString().isEmpty()) {
                            String newPassword = binding.changePassword.getText().toString();

                            AuthCredential credential = EmailAuthProvider.getCredential(user.getMail(), user.getPassword());
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {

                                            System.out.println("Error updating password");
                                        } else {
                                            user.setPassword(newPassword);
                                            docRef.set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            System.out.println("DocumentSnapshot successfully updated!");
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(aVoid -> System.out.println("Error updating document"));

                                        }
                                    });
                                } else {
                                    // Handle error
                                    System.out.println("Error re-authenticating");
                                }
                            });

                        }


                        if (!binding.changeMail.getText().toString().isEmpty() && !binding.changePassword.getText().toString().isEmpty()) {
                            String newMail = binding.changeMail.getText().toString();
                            String newPassword = binding.changePassword.getText().toString();

                            // Re-authenticate user before updating email
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getMail(), user.getPassword());
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    firebaseUser.verifyBeforeUpdateEmail(newMail).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            user.setMail(newMail);

                                            // Re-authenticate user before updating password
                                            firebaseUser.reauthenticate(credential).addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(task3 -> {
                                                        if (task3.isSuccessful()) {
                                                            user.setPassword(newPassword);
                                                            docRef.set(user)
                                                                    .addOnSuccessListener(aVoid -> {
                                                                        System.out.println("DocumentSnapshot successfully updated!");
                                                                        finish(); // Call finish() only after both updates are successful
                                                                    })
                                                                    .addOnFailureListener(aVoid -> System.out.println("Error updating document"));
                                                        } else {
                                                            System.out.println("Error updating password");
                                                        }
                                                    });
                                                } else {
                                                    System.out.println("Error re-authenticating");
                                                }
                                            });
                                        } else {
                                            System.out.println("Error updating email");
                                        }
                                    });
                                } else {
                                    System.out.println("Error re-authenticating");
                                }
                            });
                        }




                    }
                }
            });
        });


    }
}