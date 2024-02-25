package com.furkankarademir.voyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;


public class MenuPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.altbar);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();

                if (itemid == R.id.home)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, new HomeFragment());
                    fragmentTransaction.commit();
                }
                else if (itemid == R.id.messages)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, new MessagesFragment());
                    fragmentTransaction.commit();
                }
                else if (itemid == R.id.profile)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, new ProfileFragment());
                    fragmentTransaction.commit();
                }
                return  true;
            }
        });





        /*private void loadFragment(Fragment fragment, boolean isAppInitialized) // calismiyor :C
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(isAppInitialized)
            {
                fragmentTransaction.add(R.id.constraint_layout, fragment);
            }
            else
            {
                fragmentTransaction.replace(R.id.constraint_layout, fragment);
            }

            fragmentTransaction.commit();

        }*/
    }
}