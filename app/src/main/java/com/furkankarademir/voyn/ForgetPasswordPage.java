package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.databinding.ActivityForgetPasswordPageBinding;
import com.furkankarademir.voyn.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordPage extends AppCompatActivity {

    private ActivityForgetPasswordPageBinding binding;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
    }

    public void sendMailButtonClicked(View view)
    {
        String eMail = binding.forgetPassword.getText().toString();

        if (eMail.equals(""))
        {
            Toast.makeText(ForgetPasswordPage.this, "Please write your mail", Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.sendPasswordResetEmail(eMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent intent = new Intent(ForgetPasswordPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgetPasswordPage.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}