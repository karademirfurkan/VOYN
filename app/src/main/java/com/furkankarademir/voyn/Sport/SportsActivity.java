package com.furkankarademir.voyn.Sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.furkankarademir.voyn.databinding.ActivitySportsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SportsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ArrayList<HashMap<String, Object>> sportActivities;
    private SportAdapter sportAdapter;
    private ActivitySportsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sportActivities = new ArrayList<>();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(SportsActivity.this));
        sportAdapter= new SportAdapter(sportActivities, 0);
        binding.recyclerView.setAdapter(sportAdapter);

        makeArrayList();
    }
    public void makeArrayList()
    {
        db.collection("sports").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    HashMap<String, Object> sport = (HashMap<String, Object>) documentSnapshot.getData();

                    if(!auth.getUid().equals(sport.get("creatorUserID")))
                        sportActivities.add(sport);
                }
                System.out.println(sportActivities);

                sportActivities.removeIf(sport -> sport.get("date") == null ||
                        sport.get("time") == null || sport.get("place") == null);


                sportActivities.sort((o1, o2) -> {
                    if(o1.get("date") == null || o2.get("date") == null)
                        return 0;
                    String date1 = (String) o1.get("date");
                    String date2 = (String) o2.get("date");

                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

                    try {
                        Date dateObj1 = format.parse(date1);
                        Date dateObj2 = format.parse(date2);
                        return dateObj1.compareTo(dateObj2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                });
                sportAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Olmadı beeee");
            }
        });
    }
    public void addSportButton(View view) {
        Intent intent = new Intent(this, AddSportsActivity.class);
        startActivity(intent);
    }

}
