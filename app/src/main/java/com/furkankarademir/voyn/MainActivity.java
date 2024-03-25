package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.furkankarademir.voyn.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /*
        if (user != null)
        {
            Intent intent = new Intent(MainActivity.this, MenuPage.class);
            startActivity(intent);
            finish();
            Toast.makeText(MainActivity.this, "No problem, you are already signed in :)", Toast.LENGTH_LONG).show();
        }
         */
    }

    public void signInButtonClicked(View view)
    {
        String eMail = binding.mailM.getText().toString();
        String password = binding.passwordM.getText().toString();

        if (eMail.equals("") || password.equals(""))
        {
            Toast.makeText(MainActivity.this, "Please enter your eMail and password", Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid();
                        Intent intent = new Intent(MainActivity.this, MenuPage.class);
                        intent.putExtra("userID", uid);
                        startActivity(intent);
                        finish();
                    } else {
                        // Handle the case where the user is null
                        System.out.println("User is null");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void signUpButtonClicked(View view)
    {
        Intent intent = new Intent(MainActivity.this, SignUpPage.class);
        startActivity(intent);
    }

    public void forgetPasswordButtonClicked(View view)
    {
        Intent intent = new Intent(MainActivity.this, ForgetPasswordPage.class);
        startActivity(intent);
        finish();
    }

}