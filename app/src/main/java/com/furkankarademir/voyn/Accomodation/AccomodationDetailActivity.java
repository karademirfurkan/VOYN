package com.furkankarademir.voyn.Accomodation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.furkankarademir.voyn.Chat.Chat;
import com.furkankarademir.voyn.Chat.ChatInBetweenPage;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Sport.SportsDetailActivity;
import com.furkankarademir.voyn.Transportation.TransportationDetailActivity;
import com.furkankarademir.voyn.databinding.ActivityAccomodationDetailBinding;
import com.furkankarademir.voyn.databinding.ActivitySportsDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AccomodationDetailActivity extends AppCompatActivity {

    private ActivityAccomodationDetailBinding binding;

    private HashMap<String, Object> accommodation;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccomodationDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        HashMap<String, Object> accommodationMap = (HashMap<String, Object>) intent.getSerializableExtra("accommodation");
        accommodation = accommodationMap;

        DocumentReference docRef = db.collection("Users").document(accommodation.get("creatorUserID").toString());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String name = documentSnapshot.getString("name");
                    String surname = documentSnapshot.getString("surname");
                    String nameSurname = name + " " + surname;
                    binding.nameInfo.setText(nameSurname);
                    binding.bioInfo.setText(documentSnapshot.getString("bio"));
                    binding.ageInfo.setText(documentSnapshot.getString("age"));
                    binding.noteInfo.setText(documentSnapshot.getString("extraNote"));
                    binding.starInfo.setText(documentSnapshot.getString("minStar"));
                    ImageView profilePicture = findViewById(R.id.profile);

                    User user = documentSnapshot.toObject(User.class);
                    if (user.getProfilePhotoUrl() != null && !user.getProfilePhotoUrl().isEmpty()) {
                        Glide.with(AccomodationDetailActivity.this)
                                .load(user.getProfilePhotoUrl())
                                .into(profilePicture);
                    } else {
                        profilePicture.setImageResource(R.drawable.profile_photo);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error getting user document", e);
            }
        });

        binding.genderInfo.setText((String) accommodationMap.get("gender"));//it has written wrong in the xml part
        binding.timeInfo.setText((String) accommodationMap.get("place")); //it has written wrong in the xml part
        binding.dateInfo.setText((String) accommodationMap.get("date"));
        binding.timeInfo.setText((String) accommodationMap.get("time"));
        binding.inhabitantslimitInfo.setText(accommodationMap.get("numberOfInhabitants").toString());
        ArrayList<String> invited = (ArrayList<String>) accommodationMap.get("invited");

        boolean isRed = getIntent().getBooleanExtra("isRed", false);
        if (isRed) {
            binding.sendInvitation.setVisibility(View.GONE);
            binding.sendMessage.setVisibility(View.GONE);
        }
    }
    public void sendMessageButtonClicked(View view)
    {
        System.out.println("send1");
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", accommodation.get("creatorUserID").toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        System.out.println("send2");
                        if (queryDocumentSnapshots.isEmpty()) {
                            System.out.println("send3");
                            ArrayList<Message> messagesInBetween = new ArrayList<>();
                            Chat newChat = new Chat(auth.getUid().toString(),accommodation.get("creatorUserID").toString(),messagesInBetween);
                            System.out.println("send4");
                            db.collection("Chat").add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    System.out.println("send5");
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(AccomodationDetailActivity.this, "new chat added", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("send6");
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                        }
                        else
                        {
                            System.out.println("send7");
                            Toast.makeText(AccomodationDetailActivity.this, "same chat", Toast.LENGTH_LONG).show();
                        }

                        System.out.println("send8");
                        Intent intent = new Intent(AccomodationDetailActivity.this, ChatInBetweenPage.class);
                        intent.putExtra("accommodation", accommodation);
                        startActivity(intent);
                        System.out.println("send9");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error checking chat existence", e);
                    }
                });


    }
    public void sendInvitationButtonClicked(View view)
    {
        binding.sendInvitation.setVisibility(View.INVISIBLE);
        ArrayList<String> invited = (ArrayList<String>) accommodation.get("invited");
        if (invited == null) {
            invited = new ArrayList<>();
        }

        // Add the current user's ID to the invited ArrayList
        invited.add(auth.getUid());

        accommodation.put("invited", invited);

        db.collection("accommodations").document(accommodation.get("documentId").toString())
                .set(accommodation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

}