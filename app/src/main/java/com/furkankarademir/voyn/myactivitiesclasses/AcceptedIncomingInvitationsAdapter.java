package com.furkankarademir.voyn.myactivitiesclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.databinding.MyActivitiesRowBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AcceptedIncomingInvitationsAdapter extends RecyclerView.Adapter<AcceptedIncomingInvitationsAdapter.AcceptedIncomingInvitationsHolder> {
    private ArrayList<String> acceptedInvitations;
    private FirebaseFirestore db;
    private FirebaseAuth auth;


    public AcceptedIncomingInvitationsAdapter(ArrayList<String> acceptedInvitations)
    {
        this.acceptedInvitations = acceptedInvitations;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public AcceptedIncomingInvitationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyActivitiesRowBinding binding = MyActivitiesRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AcceptedIncomingInvitationsAdapter.AcceptedIncomingInvitationsHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedIncomingInvitationsHolder holder, int position) {
        String userId = acceptedInvitations.get(position);

        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String name = documentSnapshot.getString("name");
                    String surname = documentSnapshot.getString("surname");
                    String mail = documentSnapshot.getString("mail");

                    User thisUser = documentSnapshot.toObject(User.class);

                    holder.binding.name.setText(name);
                    holder.binding.surname.setText(surname);
                }
                else
                {
                    System.out.println("olmadı");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("olmadı");
            }
        });
        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(holder.context, profilePageForOtherUsers.class);
            intent.putExtra("userId", acceptedInvitations.get(position));
            holder.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return acceptedInvitations.size();
    }

    public class AcceptedIncomingInvitationsHolder extends RecyclerView.ViewHolder
    {
        private MyActivitiesRowBinding binding;
        private Context context;

        public AcceptedIncomingInvitationsHolder(MyActivitiesRowBinding binding, Context context)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
    }
}
