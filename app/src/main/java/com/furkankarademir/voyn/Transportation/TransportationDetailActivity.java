package com.furkankarademir.voyn.Transportation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.furkankarademir.voyn.Chat.Chat;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.ActivityTransportationDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;

public class TransportationDetailActivity extends AppCompatActivity {

    private ActivityTransportationDetailBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportationDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        HashMap<String, Object> transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");

        String name = (String) transportationMap.get("name");
        binding.nameInfo.setText(name);
        binding.surnameInfo.setText((String) transportationMap.get("surname"));
        binding.mailInfo.setText((String) transportationMap.get("mail"));
        binding.departureInfo.setText((String) transportationMap.get("departure"));
        binding.destinationInfo.setText((String) transportationMap.get("destination"));
        binding.dateInfo.setText((String) transportationMap.get("date"));
        binding.timeInfo.setText((String) transportationMap.get("time"));
        binding.seatsInfo.setText(transportationMap.get("seats").toString());
    }

    public void sendMessageButtonClicked(View view)
    {
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", "abc")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Chat already exists

                        } else {
                            // Chat doesn't exist, create a new chat
                            ArrayList<Message> messagesInBetween = new ArrayList<>();
                            Chat newChat = new Chat(auth.getUid().toString(),"abc",messagesInBetween);
                            db.collection("Chat").add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                        }
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

    }
}