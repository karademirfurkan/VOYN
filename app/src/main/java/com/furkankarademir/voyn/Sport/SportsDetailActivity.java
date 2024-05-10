package com.furkankarademir.voyn.Sport;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.furkankarademir.voyn.Accomodation.AccomodationDetailActivity;
import com.furkankarademir.voyn.Chat.Chat;
import com.furkankarademir.voyn.Chat.ChatInBetweenPage;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivitySportsDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SportsDetailActivity extends AppCompatActivity implements Serializable {
    private ActivitySportsDetailBinding binding;

    private HashMap<String, Object> sport;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportsDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        HashMap<String, Object> sportMap = (HashMap<String, Object>) intent.getSerializableExtra("sport");
        sport = sportMap;

        DocumentReference docRef = db.collection("Users").document(sport.get("creatorUserID").toString());
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
                    ImageView profilePicture = findViewById(R.id.profile);

                    User user = documentSnapshot.toObject(User.class);
                    if (user.getProfilePhotoUrl() != null && !user.getProfilePhotoUrl().isEmpty()) {
                        Glide.with(SportsDetailActivity.this)
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

        //binding.surnameInfo.setText((String) sportMap.get("surname"));
        binding.placeInfo.setText((String) sportMap.get("place"));
        binding.dateInfo.setText((String) sportMap.get("date"));
        binding.timeInfo.setText((String) sportMap.get("time"));
        binding.numberOfPlayersInfo.setText(sportMap.get("numberOfPlayers").toString());
        binding.noteInfo.setText(sportMap.get("extraNote").toString());
        binding.starInfo.setText(sportMap.get("minStar").toString());
        binding.starInfo.setText(sportMap.get("minStar").toString());

        ArrayList<String> invited = (ArrayList<String>) sportMap.get("invited");
        if (invited != null) {
            // binding.deneme.setText(invited.toString());
        }

        boolean isRed = getIntent().getBooleanExtra("isRed", false);
        if (isRed) {
            binding.sendInvitation.setVisibility(View.GONE);
            binding.sendMessage.setVisibility(View.GONE);
        }
    }
    public void sendMessageButtonClicked(View view)
    {
        System.out.println("sportSend1");
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", sport.get("creatorUserID").toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        System.out.println("sportSend2");
                        if (queryDocumentSnapshots.isEmpty()) {
                            // Chat doesn't exist, create a new chat
                            System.out.println("sportSend3");
                            ArrayList<Message> messagesInBetween = new ArrayList<>();
                            Chat newChat = new Chat(auth.getUid().toString(),sport.get("creatorUserID").toString(),messagesInBetween);

                            System.out.println("sportSend4");
                            db.collection("Chat").add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    System.out.println("sportSend5");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("sportSend6");
                                }
                            });
                        }
                        else
                        {
                            System.out.println("sportSend7");
                            Toast.makeText(SportsDetailActivity.this, "same chat", Toast.LENGTH_LONG).show();
                        }

                        System.out.println("sportSend8");
                        Intent intent = new Intent(SportsDetailActivity.this, ChatInBetweenPage.class);

                        intent.putExtra("sport", sport);
                        System.out.println("sportSend9");
                        startActivity(intent);
                        System.out.println("sportSend10");
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
        ArrayList<String> invited = (ArrayList<String>) sport.get("invited");
        if (invited == null) {
            invited = new ArrayList<>();
        }

        // Add the current user's ID to the invited ArrayList
        invited.add(auth.getUid());

        sport.put("invited", invited);

        db.collection("sports").document(sport.get("documentId").toString())
                .set(sport)
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