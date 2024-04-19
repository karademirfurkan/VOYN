package com.furkankarademir.voyn.myactivitiesclasses;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class IncomingInvitationsAdapter extends RecyclerView.Adapter<IncomingInvitationsAdapter.IncomingInvitationsHolder> {

    private ArrayList<String> incomingInvitations;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String transportationId;

    public IncomingInvitationsAdapter(ArrayList<String> incomingInvitations, String transportationId)
    {
        this.incomingInvitations = incomingInvitations;
        this.transportationId = transportationId;
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
        if (incomingInvitations != null && !incomingInvitations.isEmpty()) {
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

            holder.binding.acceptButton.setOnClickListener(v ->
            {
                // Add the user to the attendId list
                db.collection("transportations")
                        .whereEqualTo("documentId", transportationId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // The document was found
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    Map<String, Object> transportation = documentSnapshot.getData();
                                    // Now you can use the transportation map

                                    ArrayList<String> participantsId = (ArrayList<String>) transportation.get("participantsId");
                                    if (participantsId == null) {
                                        participantsId = new ArrayList<>();
                                    }
                                    participantsId.add(userId);
                                    transportation.put("participantsId", participantsId);
                                    ArrayList<String> invited = (ArrayList<String>) transportation.get("invited");
                                    invited.remove(userId);
                                    transportation.put("invited", invited);

                                    db.collection("transportations").document(transportation.get("documentId").toString())
                                            .set(transportation)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(holder.context, "User accepted", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(holder.context, "Error updating document", Toast.LENGTH_SHORT).show();
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });
                                } else {
                                    Toast.makeText(holder.context, "No such document", Toast.LENGTH_SHORT).show();
                                    // The document was not found
                                    System.out.println("No such document");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the error
                                System.out.println("Error getting document: " + e);
                            }
                        });
            });

            holder.binding.declineButton.setOnClickListener(v ->
            {
                // Remove the user from the invited list
                db.collection("transportations")
                        .whereEqualTo("documentId", transportationId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // The document was found
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    Map<String, Object> transportation = documentSnapshot.getData();
                                    // Now you can use the transportation map

                                    ArrayList<String> invited = (ArrayList<String>) transportation.get("invited");
                                    invited.remove(userId);
                                    transportation.put("invited", invited);

                                    db.collection("transportations").document(transportation.get("documentId").toString())
                                            .set(transportation)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(holder.context, "User declined", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(holder.context, "Error updating document", Toast.LENGTH_SHORT).show();
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });
                                } else {
                                    Toast.makeText(holder.context, "No such document", Toast.LENGTH_SHORT).show();
                                    // The document was not found
                                    System.out.println("No such document");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the error
                                System.out.println("Error getting document: " + e);
                            }
                        });
            });

            holder.itemView.setOnClickListener(v ->
            {
                // Go to the user's profile
                Intent intent = new Intent(holder.context, profilePageForOtherUsers.class);
                intent.putExtra("userId", incomingInvitations.get(position));
                holder.context.startActivity(intent);
            });
        }
        else {
            System.out.println("No incoming invitations");
        }
    }

    @Override
    public int getItemCount() {
        if (incomingInvitations != null && !incomingInvitations.isEmpty()) {
            return incomingInvitations.size();
        } else {
            return 0;
        }
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
