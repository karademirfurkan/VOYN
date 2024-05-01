package com.furkankarademir.voyn.Transportation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.RecyclerTransportationRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.myTransportationActivityDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.Color;

import java.util.ArrayList;
import java.util.HashMap;





public class TransportationAdapter extends RecyclerView.Adapter<TransportationAdapter.TransportationHolder> {
    private ArrayList<HashMap<String, Object>> transportationActivities;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private User user;
    private int transportationAdapterOption;
    public  TransportationAdapter(ArrayList<HashMap<String, Object>> transportationActivities, int i)
    {
        this.transportationActivities = transportationActivities;
        transportationAdapterOption = i;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    public class TransportationHolder extends RecyclerView.ViewHolder
    {
        private RecyclerTransportationRowBinding binding;
        public TransportationHolder(RecyclerTransportationRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public TransportationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerTransportationRowBinding transportationRowBinding = RecyclerTransportationRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransportationHolder(transportationRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportationHolder holder, @SuppressLint("RecyclerView") int position)
    {
        HashMap<String, Object> currentActivity = transportationActivities.get(position);
        if(currentActivity == null)
        {
            return;
        }
        String time = (String) transportationActivities.get(position).get("time");
        String departure = (String) transportationActivities.get(position).get("departure");
        String date = (String) transportationActivities.get(position).get("date");
        String destination = (String) transportationActivities.get(position).get("destination");
        ArrayList<String> participantsList = (ArrayList<String>) transportationActivities.get(position).get("participantsId");
        String personLimit = participantsList.size() + "/" + transportationActivities.get(position).get("seats").toString();
        double minStar = Double.parseDouble(transportationActivities.get(position).get("minStar").toString());

        holder.binding.time.setText(time);
        holder.binding.date.setText(date);
        holder.binding.whereFrom.setText(departure);
        holder.binding.whereTo.setText(destination);
        holder.binding.availableSeats.setText(personLimit);

        //----------------------
        DocumentReference docRef = db.collection("Users").document(auth.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    user = documentSnapshot.toObject(User.class);
                    if(transportationAdapterOption == 0)
                    {
                        if (user.getStar() < minStar || participantsList.contains(auth.getUid()) || auth.getUid().equals(transportationActivities.get(position).get("creatorUserID").toString()))
                        {
                            holder.binding.bigLinearLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.red_row_view));

                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), TransportationDetailActivity.class);
                                intent.putExtra("transportation", transportationActivities.get(position));
                                boolean isRed = user.getStar() < minStar || participantsList.contains(auth.getUid()) || auth.getUid().equals(transportationActivities.get(position).get("creatorUserID").toString());
                                intent.putExtra("isRed", isRed);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }
                    else
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), myTransportationActivityDetails.class);
                                intent.putExtra("transportation", transportationActivities.get(position));
                                holder.itemView.getContext().startActivity(intent);
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
        //-------------------------------

    }

    @Override
    public int getItemCount() {
        return transportationActivities.size();
    }
}
