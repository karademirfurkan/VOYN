package com.furkankarademir.voyn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluatingUsersPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluating_users_page);

        /*
        Intent intent = getIntent();
        ArrayList<String> usersId = (ArrayList<String>) intent.getSerializableExtra("participants");
        System.out.println(usersId + "    evaluation cart curt");

         */
    }

    public void askMeLaterClicked(View view)
    {
        Intent intent = new Intent(EvaluatingUsersPage.this, HomeFragment.class);
        startActivity(intent);
    }

    public void doNotAskAgainClicked(View view)
    {

    }

    public void okClicked(View view)
    {

    }
}