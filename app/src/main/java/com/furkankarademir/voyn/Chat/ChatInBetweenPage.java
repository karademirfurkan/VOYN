package com.furkankarademir.voyn.Chat;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityChatInBetweenPageBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

public class ChatInBetweenPage extends AppCompatActivity {
    private ActivityChatInBetweenPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private ArrayList<Message> messages;
    private HashMap<String, Object> transportationMap;

    private ChatInBetweenAdapter chatInBetweenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatInBetweenPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        transportationMap = (HashMap<String, Object>) intent.getSerializableExtra("transportation");



        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        messages = new ArrayList<>();
        binding.chatRv.setLayoutManager(new LinearLayoutManager(ChatInBetweenPage.this));
        chatInBetweenAdapter = new ChatInBetweenAdapter(messages);
        binding.chatRv.setAdapter(chatInBetweenAdapter);

        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", transportationMap.get("creatorUserID").toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String chatId = document.getId();
                                setUpMessageListener(chatId);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("chat message arraylisti yapılamadı");
                    }
                });
        makeArrayList();

    }


    public void makeArrayList()
    {
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", transportationMap.get("creatorUserID").toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                            {
                                Chat chat = document.toObject(Chat.class);
                                messages = chat.getMessagesInBetween();

                                binding.chatRv.setLayoutManager(new LinearLayoutManager(ChatInBetweenPage.this));
                                chatInBetweenAdapter = new ChatInBetweenAdapter(messages);
                                binding.chatRv.setAdapter(chatInBetweenAdapter);


                                String chatId = document.getId();

                                // Set up Firestore listener for real-time updates using this chatId
                                setUpMessageListener(chatId);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("chat message arraylisti yapılamadı");
                    }
                });
    }

    /*public void setUpMessageListener(String chatId) {
        db.collection("Chat")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Log.e("ChatInBetweenPage", "Error listening for messages", error);
                        return;
                    }

                    for (DocumentSnapshot document : querySnapshot) {
                        Message message = document.toObject(Message.class);
                        messages.add(message);
                    }

                    // Update the RecyclerView adapter with the new messages
                    chatInBetweenAdapter.setMessages(messages);
                    chatInBetweenAdapter.notifyDataSetChanged();
                });
    }*/
    public void setUpMessageListener(String chatId) {
        db.collection("Chat")
                .document(chatId)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        System.out.println("Listen failed.");
                        Log.w("Listen failed.", e.getMessage());
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Chat chat = documentSnapshot.toObject(Chat.class);
                        messages = chat.getMessagesInBetween();

                        chatInBetweenAdapter.setMessages(messages);
                        chatInBetweenAdapter.notifyDataSetChanged();
                        binding.chatRv.scrollToPosition(messages.size() - 1);
                    } else {
                        System.out.println("No documents found in messagesInBetween field.");
                    }
                });
    }


    public void sendMessageButtonClicked(View view) {
        db.collection("Chat")
                .whereEqualTo("firstUserId", auth.getUid())
                .whereEqualTo("secondUserId", transportationMap.get("creatorUserID").toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String chatId = document.getId();

                                Chat chat = document.toObject(Chat.class);
                                Message newMessage = new Message(binding.messageText.getText().toString(), auth.getUid(), transportationMap.get("creatorUserID").toString());

                                chat.addMessageToArrayList(newMessage);

                                db.collection("Chat").document(chatId).set(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document updated successfully
                                        // Update the adapter after the new message has been added to Firestore
                                        chatInBetweenAdapter.setMessages(chat.getMessagesInBetween());
                                        chatInBetweenAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure
                                    }
                                });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }



}