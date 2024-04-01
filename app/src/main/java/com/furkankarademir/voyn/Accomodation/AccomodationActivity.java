package com.furkankarademir.voyn.Accomodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.AddTransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityAccomodationBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AccomodationActivity extends AppCompatActivity {
    private ActivityAccomodationBinding binding;

    private FirebaseFirestore db;
    private ArrayList<HashMap<String, Object>> accomodationActivities;
    private AccomodationAdapter accomodationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccomodationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = FirebaseFirestore.getInstance();
        accomodationActivities = new ArrayList<>();



        makeArrayList();
    }

    public void makeArrayList()
    {
        db.collection("accomodation").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    HashMap<String, Object> accomodation = (HashMap<String, Object>) snapshot.getData();
                    accomodationActivities.add(accomodation);
                }

                accomodationActivities.sort((o1, o2) -> {
                    if(o1.get("date") == null || o2.get("date") == null)
                        return 0;
                    String date1 = (String) o1.get("date");
                    String date2 = (String) o2.get("date");

                    if (Integer.parseInt(date1.substring(6, 10)) < Integer.parseInt(date2.substring(6, 10)))
                    {
                        return -1;
                    }
                    else if (Integer.parseInt(date1.substring(6, 10)) > Integer.parseInt(date2.substring(6, 10)))
                    {
                        return 1;
                    }
                    else
                    {
                        if (Integer.parseInt(date1.substring(3, 5)) < Integer.parseInt(date2.substring(3, 5)))
                        {
                            return -1;
                        }
                        else if ((Integer.parseInt(date1.substring(3, 5)) > Integer.parseInt(date2.substring(3, 5))))
                        {
                            return 1;
                        }
                        else
                        {
                            if (Integer.parseInt(date1.substring(0, 2)) < Integer.parseInt(date2.substring(0, 2)))
                            {
                                return -1;
                            }
                            else
                            {
                                return 1;
                            }
                        }
                    }
                });


                for (int i = 0; i < accomodationActivities.size(); i++)
                {
                    System.out.println(accomodationActivities.get(i).get("date"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccomodationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addAccomodationButtonClicked(View view)
    {
        Intent intent = new Intent(AccomodationActivity.this, AddAccomodationActivity.class);
        startActivity(intent);
    }

}