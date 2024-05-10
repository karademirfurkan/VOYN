package com.furkankarademir.voyn.Transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.HomeFragment;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivitySignUpPageBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TransportationActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ArrayList<HashMap<String, Object>> transportationActivities;
    private TransportationAdapter transportationAdapter;
    private ActivityTransportationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        transportationActivities = new ArrayList<>();

        binding.filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, TransportationFilter.class);
                startActivityForResult(intent, 1);
            }
        });



        binding.recyclerView.setLayoutManager(new LinearLayoutManager(TransportationActivity.this));
        transportationAdapter= new TransportationAdapter(transportationActivities, 0);
        binding.recyclerView.setAdapter(transportationAdapter);

        makeArrayList();
    }

    public void makeArrayList()
    {
        db.collection("transportations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    HashMap<String, Object> transportation = (HashMap<String, Object>) documentSnapshot.getData();

                    if(!auth.getUid().equals(transportation.get("creatorUserID")))
                        transportationActivities.add(transportation);
                }
                System.out.println(transportationActivities);

                transportationActivities.removeIf(transportation -> transportation.get("date") == null ||
                        transportation.get("time") == null || transportation.get("departure") == null ||
                        transportation.get("destination") == null);


                transportationActivities.sort((o1, o2) -> {
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
                transportationAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Olmadı beeee");
            }
        });
    }

    public void addTransportationButton(View view) {
        Intent intent = new Intent(this, AddTransportationActivity.class);
        startActivity(intent);
    }

    private User user;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String departure = data.getStringExtra("departure");
                String destination = data.getStringExtra("destination");
                String time = data.getStringExtra("time");
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

                transportationActivities.removeIf(transportation -> {
                    if(departure != null && !departure.equals("") && !departure.equals(transportation.get("departure")))
                        return true;
                    if(destination != null && !destination.equals("") && !destination.equals(transportation.get("destination")))
                        return true;
                    if(time != null && !time.equals("") && !time.equals(transportation.get("time")))
                        return true;
                    if(availability)
                    {
                        ArrayList<String> participantsList = (ArrayList<String>) transportation.get("participantsId");
                        if(participantsList.size() == Integer.parseInt(transportation.get("seats").toString()))
                            return true;
                        if (participantsList.contains(auth.getUid()))
                            return true;
                    }
                    if(locked)
                        if (user.getStar() < Double.parseDouble(transportation.get("minStar").toString()))
                            return true;
                    if(calendar != 0) {
                        // Convert the calendar long value to Date
                        Date calendarDate = new Date(calendar);


                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                        String calendarDateString = format.format(calendarDate);


                        String activityDateString = (String) transportation.get("date");
                        System.out.println("calendarDateString: " + calendarDateString);
                        System.out.println("activityDateString: " + activityDateString);
                        if (!calendarDateString.equals(activityDateString)) {
                            return true;
                        }
                    }
                    return false;
                });
                transportationAdapter.notifyDataSetChanged();
            }
        }
    }




}