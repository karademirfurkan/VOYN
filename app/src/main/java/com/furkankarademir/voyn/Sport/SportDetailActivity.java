package com.furkankarademir.voyn.Sport;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.furkankarademir.voyn.Chat.Chat;
import com.furkankarademir.voyn.Chat.ChatInBetweenPage;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.ParentClassesForActivity.Activity;
import com.furkankarademir.voyn.Sport.SportDetailActivity;
import com.furkankarademir.voyn.databinding.ActivitySportDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SportDetailActivity extends AppCompatActivity {
    private ActivitySportDetailBinding binding;

    private HashMap<String, Object> sport;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        HashMap<String, Object> sportMap = (HashMap<String, Object>) intent.getSerializableExtra("sport");
        sport = sportMap;

        System.out.println(sport.get("creatorUserID"));

        String name = (String) sportMap.get("name");
        binding.nameInfo.setText(name);
        binding.surnameInfo.setText((String) sportMap.get("surname"));
        binding.placeInfo.setText((String) sportMap.get("place"));
        binding.dateInfo.setText((String) sportMap.get("date"));
        binding.timeInfo.setText((String) sportMap.get("time"));
        binding.numberOfPlayersInfo.setText(sportMap.get("seats").toString());
        ArrayList<String> invited = (ArrayList<String>) sportMap.get("invited");
        if (invited != null) {
           // binding.deneme.setText(invited.toString());
        }
    }
    public void sendMessageButtonClicked(View view)
    {
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", sport.get("creatorUserID").toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        if (queryDocumentSnapshots.isEmpty()) {
                            // Chat doesn't exist, create a new chat
                            ArrayList<Message> messagesInBetween = new ArrayList<>();
                            Chat newChat = new Chat(auth.getUid().toString(),sport.get("creatorUserID").toString(),messagesInBetween);
                            db.collection("Chat").add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(SportDetailActivity.this, "new chat added", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SportDetailActivity.this, "same chat", Toast.LENGTH_LONG).show();
                        }

                        Intent intent = new Intent(SportDetailActivity.this, ChatInBetweenPage.class);
                        intent.putExtra("sport", sport);
                        startActivity(intent);
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
