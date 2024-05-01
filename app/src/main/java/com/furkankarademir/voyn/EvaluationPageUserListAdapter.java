package com.furkankarademir.voyn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Chat.ChatInBetweenAdapter;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.Transportation.AddTransportationActivity;
import com.furkankarademir.voyn.databinding.ChatInBetweenRowBinding;
import com.furkankarademir.voyn.databinding.EvaluatingUsersRowBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EvaluationPageUserListAdapter extends RecyclerView.Adapter<EvaluationPageUserListAdapter.EvaluationPageUserListHolder>
{
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ArrayList<String> users;
    public EvaluationPageUserListAdapter(ArrayList<String> users)
    {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.users = users;
    }
    @NonNull
    @Override
    public EvaluationPageUserListAdapter.EvaluationPageUserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EvaluatingUsersRowBinding binding = EvaluatingUsersRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EvaluationPageUserListAdapter.EvaluationPageUserListHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationPageUserListAdapter.EvaluationPageUserListHolder holder, int position)
    {
        DocumentReference docRef = db.collection("Users").document(users.get(position));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    User thisUser = documentSnapshot.toObject(User.class);

                    String name = thisUser.getName();
                    String surname = thisUser.getSurname();
                    holder.binding.nameSurname.setText(name + " " + surname);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        holder.binding.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
                holder.binding.star4.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
                holder.binding.star4.setImageResource(R.drawable.stars);
                holder.binding.star5.setImageResource(R.drawable.stars);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class EvaluationPageUserListHolder extends RecyclerView.ViewHolder
    {
        private EvaluatingUsersRowBinding binding;
        public EvaluationPageUserListHolder(EvaluatingUsersRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}