package com.furkankarademir.voyn.myactivitiesclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.Transportation.AddTransportationActivity;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.InvitationSendersRowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class IncomingInvitationsAdapter extends RecyclerView.Adapter<IncomingInvitationsAdapter.IncomingInvitationsHolder> {

    private ArrayList<String> incomingInvitations;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public IncomingInvitationsAdapter(ArrayList<String> incomingInvitations)
    {
        this.incomingInvitations = incomingInvitations;
    }
    @NonNull
    @Override
    public IncomingInvitationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvitationSendersRowBinding binding = InvitationSendersRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IncomingInvitationsHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingInvitationsHolder holder, int position)
    {
        String userId = incomingInvitations.get(position);

        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String name = documentSnapshot.getString("name");
                    System.out.println("bunun ismi" + name);
                    String surname = documentSnapshot.getString("surname");
                    String mail = documentSnapshot.getString("mail");

                    User thisUser = documentSnapshot.toObject(User.class);

                    // Set the user's name to the TextView
                    holder.binding.nameForInvitations.setText(name);
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
    }

    @Override
    public int getItemCount() {
        return incomingInvitations.size();
    }

    public class IncomingInvitationsHolder extends RecyclerView.ViewHolder
    {
        private InvitationSendersRowBinding binding;
        private Context context;

        public IncomingInvitationsHolder(InvitationSendersRowBinding binding, Context context)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
    }
}
