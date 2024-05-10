package com.furkankarademir.voyn.Sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.databinding.ActivitySportsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    private User user;
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

        binding.filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SportsActivity.this, SportsFilter.class);
                startActivityForResult(intent, 1);
            }
        });

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String time = data.getStringExtra("time");
                String place = data.getStringExtra("place");
                String type = data.getStringExtra("type");
                boolean availability = data.getBooleanExtra("availability", false);
                boolean locked = data.getBooleanExtra("locked", false);
                long calendar = data.getLongExtra("calendar", 0);

                DocumentReference documentReference = db.collection("Users").document(auth.getCurrentUser().getUid());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            user = documentSnapshot.toObject(User.class);
                        }
                    }
                });

                sportActivities.removeIf(sport -> {
                    if (time != null && !time.equals("") && !time.equals(sport.get("time")))
                        return true;
                    if (place != null && !place.equals("") &&!place.equals(sport.get("place")))
                        return true;
                    if (type != null && !type.equals("") && !type.equals(sport.get("type")))
                        return true;
                    if(calendar != 0) {
                        // Convert the calendar long value to Date
                        Date calendarDate = new Date(calendar);


                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                        String calendarDateString = format.format(calendarDate);


                        String activityDateString = (String) sport.get("date");
                        System.out.println("calendarDateString: " + calendarDateString);
                        System.out.println("activityDateString: " + activityDateString);
                        if (!calendarDateString.equals(activityDateString)) {
                            return true;
                        }
                    }
                    if (availability) {
                        ArrayList<String> participantsList = (ArrayList<String>) sport.get("participantsId");
                        if (participantsList.size() == Integer.parseInt(sport.get("seats").toString()))
                            return true;
                        if (participantsList.contains(auth.getUid()))
                            return true;
                    }
                    if (locked)
                        if (user.getStar() < Double.parseDouble(sport.get("minStar").toString()))
                            return true;
                    return false;
                });
                sportAdapter.notifyDataSetChanged();
            }
        }
    }
    public void addSportButton(View view) {
        Intent intent = new Intent(this, AddSportsActivity.class);
        startActivity(intent);
    }

}
