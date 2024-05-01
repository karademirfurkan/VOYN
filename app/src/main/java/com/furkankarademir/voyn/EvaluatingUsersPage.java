package com.furkankarademir.voyn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityAddTransportationBinding;
import com.furkankarademir.voyn.databinding.ActivityEvaluatingUsersPageBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluatingUsersPage extends AppCompatActivity {
    private ActivityEvaluatingUsersPageBinding binding;
    private EvaluationPageUserListAdapter evaluationPageUserListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEvaluatingUsersPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent intent = getIntent();
        ArrayList<String> usersId = (ArrayList<String>) intent.getSerializableExtra("participants");
        System.out.println(usersId + "    evaluation cart curt");

        binding.evRv.setLayoutManager(new LinearLayoutManager(EvaluatingUsersPage.this));
        evaluationPageUserListAdapter = new EvaluationPageUserListAdapter(usersId);
        binding.evRv.setAdapter(evaluationPageUserListAdapter);
    }

    public void askMeLaterClicked(View view)
    {
        Intent intent = new Intent(EvaluatingUsersPage.this, HomeFragment.class);
        startActivity(intent);
    }
}