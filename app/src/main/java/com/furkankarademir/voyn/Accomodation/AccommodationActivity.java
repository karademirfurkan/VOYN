package com.furkankarademir.voyn.Accomodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.databinding.ActivityAccomodationBinding;
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

public class AccommodationActivity extends AppCompatActivity {
    private ActivityAccomodationBinding binding;

    private FirebaseAuth auth;

    private FirebaseFirestore db;
    private ArrayList<HashMap<String, Object>> accommodationActivities;

    private AccomodationAdapter accomodationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccomodationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        accommodationActivities = new ArrayList<>();

        binding.recyclerView5.setLayoutManager(new LinearLayoutManager(AccommodationActivity.this));
        accomodationAdapter = new AccomodationAdapter(accommodationActivities, 0);
        binding.recyclerView5.setAdapter(accomodationAdapter);

        binding.filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccommodationActivity.this, AccommodationFilter.class);
                startActivityForResult(intent, 1);
            }
        });

        makeArrayList();

    }

    public void makeArrayList()
    {
        db.collection("accommodations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    HashMap<String, Object> accomodation = (HashMap<String, Object>) documentSnapshot.getData();
                    if(!auth.getUid().equals(accomodation.get("creatorUserID"))) {
                        accommodationActivities.add(accomodation);
                    }
                }


                accommodationActivities.sort((o1, o2) -> {
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccommodationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String place = data.getStringExtra("place");
                String calendar = data.getStringExtra("calendar");
                boolean availablity = data.getBooleanExtra("availablity", false);
                boolean locked = data.getBooleanExtra("locked", false);
                accommodationActivities.removeIf(accomodation -> {
                    if(place != null && !place.equals("") && !place.equals(accomodation.get("place")))
                        return true;
                    //if(calendar != null && !calendar.equals(accomodation.get("calendar")))
                      //  return true;
                    //if(availablity && (boolean) accomodation.get("availablity"))
                        //return true;
                    //if(locked && (boolean) accomodation.get("locked"))
                      //  return true;
                    return false;
                        });

                accomodationAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addAccomodationButton(View view) {
        Intent intent = new Intent(this, AddAccomodationActivity.class);
        startActivity(intent);
    }
}