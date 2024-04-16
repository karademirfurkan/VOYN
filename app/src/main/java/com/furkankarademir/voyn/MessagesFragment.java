package com.furkankarademir.voyn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.furkankarademir.voyn.Chat.ChatInBetweenAdapter;
import com.furkankarademir.voyn.Chat.ChatInBetweenPage;
import com.furkankarademir.voyn.Chat.ChatUserListAdapter;
import com.furkankarademir.voyn.Classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MessagesFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private ChatUserListAdapter chatUserListAdapter;

    private ArrayList<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        makeArrayList();

        System.out.println("jksndkjas   " + users);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rcv = view.findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        chatUserListAdapter= new ChatUserListAdapter(users);
        rcv.setAdapter(chatUserListAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    public void makeArrayList()
    {
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    String name = snapshot.getString("name");
                    String surname = snapshot.getString("surname");
                    String id = snapshot.getString("id");

                    User user = new User(name, surname, id);

                    db.collection("Chat")
                            .whereIn ("firstUserId", Arrays.asList(auth.getUid().toString(), user.getId()))
                            .whereIn ("secondUserId", Arrays.asList(auth.getUid().toString(), user.getId())).get().addOnSuccessListener(queryDocumentSnapshots2 -> {
                                if (!queryDocumentSnapshots2.isEmpty()) {
                                    users.add(user);
                                    chatUserListAdapter.updateData(users);
                                    System.out.println(users);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
                System.out.println("jksndkjasdvdfvv   " + users);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("message fragment olmadı");
            }
        });
    }



}