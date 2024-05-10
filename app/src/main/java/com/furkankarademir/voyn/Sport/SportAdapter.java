package com.furkankarademir.voyn.Sport;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.RecyclerSportRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.mySportsActivityDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportHolder> {

    private ArrayList<HashMap<String, Object>> sportActivities;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private User user;

    private int sportAdapterOption;
    public  SportAdapter(ArrayList<HashMap<String, Object>> sportActivities, int i)
    {
        this.sportActivities = sportActivities;
        sportAdapterOption = i;
    }
    public class SportHolder extends RecyclerView.ViewHolder
    {
        private RecyclerSportRowBinding binding;
        public SportHolder(RecyclerSportRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public SportAdapter.SportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerSportRowBinding sportRowBinding = RecyclerSportRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SportAdapter.SportHolder(sportRowBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull SportAdapter.SportHolder holder, int position)
    {
        HashMap<String, Object> currentActivity = sportActivities.get(position);
        if(currentActivity == null)
        {
            return;
        }
        String time = (String) sportActivities.get(position).get("time");
        String place = (String) sportActivities.get(position).get("place");
        String date = (String) sportActivities.get(position).get("date");
        ArrayList<String> participants = (ArrayList<String>) sportActivities.get(position).get("participantsId");
        String personLimit = participants.size() + "/" + sportActivities.get(position).get("numberOfPlayers").toString();
        ArrayList<String> invitedList = (ArrayList<String>) sportActivities.get(position).get("invitedId");
        String typeInfo = (String) sportActivities.get(position).get("type");

        holder.binding.time.setText(time);
        holder.binding.date.setText(date);
        holder.binding.place.setText(place);
        holder.binding.personLimit.setText(personLimit);
        holder.binding.typeInfo.setText(typeInfo);

        double minStar;
        if (sportActivities.get(position).get("minStar") == null)
        {
            minStar = 0;
        }
        else
        {
            minStar = Double.parseDouble(sportActivities.get(position).get("minStar").toString());
        }


        DocumentReference docRef = db.collection("Users").document(auth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    user = documentSnapshot.toObject(User.class);

                    if (sportAdapterOption == 0) {
                        if (user.getStar() < minStar || participants.contains(auth.getUid()) || participants.size() == Integer.parseInt(sportActivities.get(position).get("numberOfPlayers").toString()))
                        {
                            holder.binding.bigLinearLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.red_row_view));
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), SportsDetailActivity.class);
                                intent.putExtra("sport", sportActivities.get(position));
                                boolean isRed = user.getStar() < minStar || participants.contains(auth.getUid()) || participants.size() == Integer.parseInt(sportActivities.get(position).get("numberOfPlayers").toString());
                                intent.putExtra("isRed", isRed);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    } else {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), mySportsActivityDetails.class);
                                intent.putExtra("sport", sportActivities.get(position));
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportActivities.size();
    }
}
