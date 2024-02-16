package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.databinding.ActivityMainBinding;
import com.furkankarademir.voyn.databinding.ActivitySignUpPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpPage extends AppCompatActivity {
    private ActivitySignUpPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }

    public void signUpButtonClicked(View view)
    {
        String name = binding.name.getText().toString();
        String surname = binding.surname.getText().toString();
        String eMail = binding.mail.getText().toString();
        String password = binding.password.getText().toString();
        String approvePassword = binding.approvePassword.getText().toString();

        if (name.equals("") || surname.equals("") || eMail.equals("") || password.equals("") || approvePassword.equals(""))
        {
            Toast.makeText(SignUpPage.this, "Please enter all the blanks", Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    User newUser = new User(name, surname);
                    fireStore.collection("Users").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(SignUpPage.this, MenuPage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpPage.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpPage.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}