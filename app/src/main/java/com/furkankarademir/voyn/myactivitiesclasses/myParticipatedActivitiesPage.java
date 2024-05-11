package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.furkankarademir.voyn.Accomodation.AccomodationAdapter;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Sport.SportAdapter;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityMyActivitiesBinding;
import com.furkankarademir.voyn.databinding.ActivityMyParticipatedActivitiesPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class myParticipatedActivitiesPage extends AppCompatActivity {

    ActivityMyParticipatedActivitiesPageBinding binding;

    private ArrayList<String> myActivities;

    private TransportationAdapter transportationAdapter;

    private SportAdapter sportAdapter;

    private AccomodationAdapter accommodationAdapter;

    private ArrayList<HashMap<String, Object>> myParticipatedTransportationActivities;

    private ArrayList<HashMap<String, Object>> myParticipatedAccommodationActivities;

    private ArrayList<HashMap<String, Object>> myParticipatedSportActivities;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyParticipatedActivitiesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser().getUid();

        myParticipatedTransportationActivities = new ArrayList<>();
        myParticipatedAccommodationActivities = new ArrayList<>();
        myParticipatedSportActivities = new ArrayList<>();

        binding.transportation.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        transportationAdapter = new TransportationAdapter(myParticipatedTransportationActivities, 2);
        binding.transportation.setAdapter(transportationAdapter);

        binding.sport.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        accommodationAdapter = new AccomodationAdapter(myParticipatedAccommodationActivities, 2);
        binding.sport.setAdapter(accommodationAdapter);

        binding.accomodation.setLayoutManager(new LinearLayoutManager(myParticipatedActivitiesPage.this));
        sportAdapter = new SportAdapter(myParticipatedSportActivities, 2);
        binding.accomodation.setAdapter(sportAdapter);
        System.out.println("buraya girdi");
        makeMyTransportationArrayList();
        makeMyAccommodationArrayList();
        makeMySportArrayList();
    }

    /*public void makeMyTransportationArrayList()
    {
        System.out.println("buraya girdi2");
        if (currentUser != null) {
            myActivities = new ArrayList<>();
            db.collection("transportations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> participantsId = (ArrayList<String>) document.get("participantsId");
                                    if (participantsId != null && participantsId.contains(currentUser)) {

                                        myActivities.add(document.getId());
                                    }
                                    if (!myActivities.isEmpty() && myActivities != null) {
                                        for (String activityId : myActivities) {
                                            db.collection("transportations").document(activityId)
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                            myParticipatedTransportationActivities.add(data);
                                                            transportationAdapter.notifyDataSetChanged();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            System.out.println("transportation alamıyor");
                                                        }
                                                    });
                                        }
                                    } else {
                                        System.out.println("No such document my activity yok");
                                    }
                                }
                                }
                            } else {
                                System.out.println("Error getting documents: transportationlara tek tek bakamıyor" );
                            }
                        }
                    });


        else {
            System.out.println("get failed with user yok");
        }
    }*/
    /*public void makeMyTransportationArrayList() {
        System.out.println("buraya girdi2");
        if (currentUser != null) {
            myActivities = new ArrayList<>();
            db.collection("transportations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            myActivities.clear();
                            myParticipatedTransportationActivities.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> participantsId = (ArrayList<String>) document.get("participantsId");
                                    if (participantsId != null && participantsId.contains(currentUser)) {
                                        if (!myActivities.contains(document.getId())) {
                                            myActivities.add(document.getId());
                                        }
                                    }
                                    if (!myActivities.isEmpty() && myActivities != null) {
                                        for (String activityId : myActivities) {
                                            db.collection("transportations").document(activityId)
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                            myParticipatedTransportationActivities.add(data);
                                                            transportationAdapter.notifyDataSetChanged();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            System.out.println("transportation alamıyor");
                                                        }
                                                    });
                                        }
                                    } else {
                                        System.out.println("No such document my activity yok");
                                    }
                                }
                            } else {
                                System.out.println("Error getting documents: transportationlara tek tek bakamıyor");
                            }
                        }
                    });
        } else {
            System.out.println("get failed with user yok");
        }
    }*/
    public void makeMyTransportationArrayList() {
        System.out.println("buraya girdi2");
        if (currentUser != null) {
            myActivities = new ArrayList<>();
            db.collection("transportations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            myActivities.clear();
                            myParticipatedTransportationActivities.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> participantsId = (ArrayList<String>) document.get("participantsId");
                                    if (participantsId != null && participantsId.contains(currentUser)) {
                                        if (!myActivities.contains(document.getId())) {
                                            myActivities.add(document.getId());
                                        }
                                    }
                                }
                                if (!myActivities.isEmpty() && myActivities != null) {
                                    for (String activityId : myActivities) {
                                        db.collection("transportations").document(activityId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                        myParticipatedTransportationActivities.add(data);
                                                        transportationAdapter.notifyDataSetChanged();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("transportation alamıyor");
                                                    }
                                                });
                                    }
                                } else {
                                    System.out.println("No such document my activity yok");
                                }
                            } else {
                                System.out.println("Error getting documents: transportationlara tek tek bakamıyor");
                            }
                        }
                    });
        } else {
            System.out.println("get failed with user yok");
        }
    }



    /*public void makeMyAccommodationArrayList()
    {
        myActivities = new ArrayList<>();
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser);

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ( myActivities != null) {
                                for (String activityId : myActivities) {
                                    db.collection("attendedActivities").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myParticipatedAccommodationActivities.add(data);
                                                    accommodationAdapter.notifyDataSetChanged();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error getting document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }
            });
        }
    }*/
    public void makeMyAccommodationArrayList() {
        System.out.println("buraya girdi2");
        if (currentUser != null) {
            myActivities = new ArrayList<>();
            db.collection("accommodations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            myActivities.clear();
                            myParticipatedAccommodationActivities.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> participantsId = (ArrayList<String>) document.get("participantsId");
                                    if (participantsId != null && participantsId.contains(currentUser)) {
                                        if (!myActivities.contains(document.getId())) {
                                            myActivities.add(document.getId());
                                        }
                                    }
                                }
                                if (!myActivities.isEmpty() && myActivities != null) {
                                    for (String activityId : myActivities) {
                                        db.collection("accommodations").document(activityId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                        myParticipatedAccommodationActivities.add(data);
                                                        accommodationAdapter.notifyDataSetChanged();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("accommodation alamıyor");
                                                    }
                                                });
                                    }
                                } else {
                                    System.out.println("No such document my activity yok");
                                }
                            } else {
                                System.out.println("Error getting documents: accommodationslara tek tek bakamıyor");
                            }
                        }
                    });
        } else {
            System.out.println("get failed with user yok");
        }
    }
    public void makeMySportArrayList() {
        System.out.println("buraya girdi2");
        if (currentUser != null) {
            myActivities = new ArrayList<>();
            db.collection("sports")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            myActivities.clear();
                            myParticipatedSportActivities.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> participantsId = (ArrayList<String>) document.get("participantsId");
                                    if (participantsId != null && participantsId.contains(currentUser)) {
                                        if (!myActivities.contains(document.getId())) {
                                            myActivities.add(document.getId());
                                        }
                                    }
                                }
                                if (!myActivities.isEmpty() && myActivities != null) {
                                    for (String activityId : myActivities) {
                                        db.collection("sports").document(activityId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                        myParticipatedSportActivities.add(data);
                                                        sportAdapter.notifyDataSetChanged();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("sport alamıyor");
                                                    }
                                                });
                                    }
                                } else {
                                    System.out.println("No such document my activity yok");
                                }
                            } else {
                                System.out.println("Error getting documents: sportlara tek tek bakamıyor");
                            }
                        }
                    });
        } else {
            System.out.println("get failed with user yok");
        }
    }
    /*public void makeMySportArrayList()
    {
        myActivities = new ArrayList<>();
        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("Users").document(currentUser);

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            myActivities = (ArrayList<String>) document.get("myActivities");
                            if ( myActivities != null) {
                                for (String activityId : myActivities) {
                                    db.collection("attendedActivities").document(activityId)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                                                    myParticipatedSportActivities.add(data);
                                                    sportAdapter.notifyDataSetChanged();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error getting document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }
            });
        }
    }*/
}