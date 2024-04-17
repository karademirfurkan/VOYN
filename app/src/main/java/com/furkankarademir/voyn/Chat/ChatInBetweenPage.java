package com.furkankarademir.voyn.Chat;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.TransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ActivityChatInBetweenPageBinding;
import com.furkankarademir.voyn.databinding.ActivityTransportationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

public class ChatInBetweenPage extends AppCompatActivity {
    private ActivityChatInBetweenPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private ArrayList<Message> messages;
    private HashMap<String, Object> transportationMap;

    private ChatInBetweenAdapter chatInBetweenAdapter;
    private DocumentReference chatDocumentRef;

    private String otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatInBetweenPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        User otherUser = (User) intent.getSerializableExtra("selectedUser");
        if (otherUser != null) {
            System.out.println("chatInbetw1");
            otherUserId = otherUser.getId();
            System.out.println(otherUserId);
            System.out.println("chatInbetw2");
        } else {
            Intent intent2 = getIntent();
            transportationMap = (HashMap<String, Object>) intent2.getSerializableExtra("transportation");
            otherUserId = (String) transportationMap.get("creatorUserID");
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        getChatId();
        System.out.println("getChatId");
    }


    public void getChatId()
    {
        db.collection("Chat")
                .whereIn ("firstUserId", Arrays.asList(auth.getUid().toString(), otherUserId))
                .whereIn ("secondUserId", Arrays.asList(auth.getUid().toString(), otherUserId)).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        System.out.println("getChatdshkds");
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String chatId = document.getId();

                        chatDocumentRef = db.collection("Chat").document(chatId);
                        startChatListener();

                        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatInBetweenPage.this);
                        binding.chatRv.setLayoutManager(layoutManager);

                        if (chatInBetweenAdapter == null) {
                            messages = new ArrayList<>();
                            chatInBetweenAdapter = new ChatInBetweenAdapter(messages);
                            binding.chatRv.setAdapter(chatInBetweenAdapter);
                        }
                    }
                    else
                    {
                        System.out.println(otherUserId);
                        System.out.println("123456789");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void startChatListener() {
        if (chatDocumentRef != null) {
            chatDocumentRef.addSnapshotListener((snapshot, e) -> {
                if (e != null) {
                    //Log.e(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Chat chat = snapshot.toObject(Chat.class);
                    if (chat != null) {
                        messages = chat.getMessagesInBetween();
                        chatInBetweenAdapter.setMessages(messages);
                        chatInBetweenAdapter.notifyDataSetChanged();
                        binding.chatRv.scrollToPosition(messages.size() - 1);
                    }
                }
            });
        }
    }




    public void sendMessageButtonClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(calendar.getTime());

        String messageText = binding.messageText.getText().toString().trim();

        if (!TextUtils.isEmpty(messageText)) {
            Message newMessage = new Message(messageText, auth.getUid(), otherUserId);
            newMessage.setTime(currentTime);

            chatDocumentRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Chat chat = documentSnapshot.toObject(Chat.class);
                    if (chat != null) {
                        chat.addMessageToArrayList(newMessage);
                        chatDocumentRef.set(chat);
                    }
                } else {
                    messages.add(newMessage);
                    Chat newChat = new Chat(auth.getUid(), otherUserId, messages);
                    chatDocumentRef.set(newChat);
                }
                binding.messageText.setText("");
            }).addOnFailureListener(e -> {

            });
        }
    }

}