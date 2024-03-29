package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;

import com.furkankarademir.voyn.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;


public class MenuPage extends AppCompatActivity {

    private static final String TAG = "MenuPage";

    private User thisUser;
    private int userID;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        userID = getIntent().getIntExtra("userID", 0);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.altbar);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraint_layout, new MessagesFragment());
        fragmentTransaction.commit();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();

                if (itemid == R.id.home) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (homeFragment != null) {
                        fragmentTransaction.replace(R.id.constraint_layout, homeFragment);
                    }
                    else {
                        fragmentTransaction.replace(R.id.constraint_layout, new HomeFragment());
                    }
                    fragmentTransaction.commit();
                }
                else if (itemid == R.id.messages) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, new MessagesFragment());
                    fragmentTransaction.commit();
                }
                else if (itemid == R.id.profile) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, new ProfileFragment());
                    fragmentTransaction.commit();
                }
                return  true;
            }
        });

        db.collection("Users").whereEqualTo("userId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        thisUser = document.toObject(User.class);
                        // Now you have the user object, you can get the profile

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userID", userID);
                        homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundle);
                    }
                }
                else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}